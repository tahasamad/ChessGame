/*
 * Description: Class ConnectionBridge.java
 * Author: Dimtri Pankov
 * Date: 23-Feb-2011
 * Version: 1.0
 */
package ChessGameKenai;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;

import GameElements.Piece;
import GameElements.Behaviors.BehaviorResult;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

/**
 * The ConnectionBridge class is the connection between two clients if the game is played online
 * using sockets. If the game is played online this class has responsibility to read data from the socket and
 * to establish a connection between clients in order for them to work as one. This class is an observer so each time
 * any change happens to our data class Chess_Data this class is notified by executing its update method data sends a list of
 * two value through notifyObservers method we extract them and send to the client that is connected when the client is connected
 * when the client receives a list he calls the move method with two arguments of the list that is sent so changes happen that way
 * This class also has an inner class ReadData which reads the data from the socket, analyzes it and calls the appropriate methods with it
 * It is not always move because we also have a chat so it could be a message, attributeSet, color or icon path etc......
 */
public class ConnectionBridge implements Observer {

    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream dis;
    private ChessBoardView view;
    private ReadData readData;
    private ChessData data;
    private Color color = Color.ORANGE;
    private SimpleAttributeSet smpSet = new SimpleAttributeSet();
    private Chat chat;

    /**
     * The overloaded constructor of the class creates server or client as well
     * as creates both stream ObjectInputStream and ObjectOutputStream and starts the inner class thread
     * @param data as Chess_Data
     * @param view as ChessBoardView
     * @param isServer as a boolean
     * @param ipAddress as an InetAddress
     * @param chat as a Chat
     */
    public ConnectionBridge(ChessData data, ChessBoardView view, boolean isServer, InetAddress ipAddress, Chat chat) {
        this.view = view;
        this.data = data;
        this.chat = chat;
        try {
            if (isServer) {
            	setUpAsServerPlayer();
            } else {
            	setUpAsClientPlayer(ipAddress);
            }
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            readData = new ReadData();
            readData.start();
        } catch (ConnectException e) {
            chat.appendStr(new ChatPacket("\nSERVER NOT STARTED\n",  smpSet, color));
            view.reEnableMenuItems(true);
            data.isGameOnLine(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private void setUpAsClientPlayer(InetAddress ipAddress) throws Exception
    {
    	socket = new Socket(ipAddress, 8888);
    	
    	chat.appendStr(new ChatPacket("\nCONNECTED TO SERVER\n",  smpSet, color));
    	chat.appendStr(new ChatPacket("GAME HAS BEEN STARTED!\n",  smpSet, color));
        chat.setButtons(true);
        view.startTimer();
        data.isServer(false);
        view.flipClientBoard();
    }
    private void setUpAsServerPlayer() throws Exception
    {
    	chat.appendStr(new ChatPacket("\nSERVER ON LINE WAITING FOR CONNECTION\n",  smpSet, color));
        serverSocket = new ServerSocket(8888);
        socket = serverSocket.accept();
        chat.appendStr(new ChatPacket("CONNECTION IS ESTABLISHED\n",  smpSet, color));
        chat.appendStr(new ChatPacket("GAME HAS BEEN STARTED!\n",  smpSet, color));
        chat.setButtons(true);
        view.startTimer();
        data.isServer(true);
    	
    }
    
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof BehaviorResult) {
            try {
                oos.writeObject((BehaviorResult) arg);
                oos.flush();
            } catch (Exception e) {
                chat.appendStr(new ChatPacket(e.getMessage(),  smpSet, color));
            }
        }
    }

    /**
     * The ReadData is a Thread object which reads data from the socket
     * then passes the read object to the analyzeAndExecute when the start method
     * is called on this object the run method is executed in our case it is inside the loop
     * so the run method will be executed until the boolean value is changed to false which will force the run
     * method to terminate thus killing the thread
     */
    public class ReadData extends Thread {

        private volatile boolean isAlive = true;

        /**
         * The empty constructor creates an input stream for reading data
         * @throws IOException when working with sockets risks are unavoidable
         */
        public ReadData() throws IOException {
            dis = new ObjectInputStream(socket.getInputStream());
        }

        /**
         * The run method of the class reads data from the socket
         * it is inside the loop so reads data until the run method terminates
         */
        @Override
        public void run() {
            try {
                while (isAlive) {
                    Object object = dis.readObject();
                    this.analyzeAndExecute(object);
                }
            } catch (SocketException e) {
            	String clientPlayerName = ChessGameUtils.getOtherPlayerName();
                chat.appendStr(new ChatPacket("\n" + clientPlayerName + " Has left the game",  smpSet, color));
                chat.setButtons(false);
                data.isGameOnLine(false);
                view.reEnableMenuItems(true);
                try {
                    socket.close();
                } catch (Exception d) {
                    chat.appendStr(new ChatPacket(d.toString(),  smpSet, color));
                }
            } catch (Exception e) {
                chat.appendStr(new ChatPacket(e.toString(),  smpSet, color));
            }
        }

        /**
         * The analyzeAndExecute method receives an object that the ReadData class has just read from
         * the socket this method analyzes it and calls appropriate methods to update the view like change icon,
         * display message, restart game or list of two integers that the move method will be called with
         * @param object as an Object
         */
        public void analyzeAndExecute(Object object) {
            if (object instanceof Packet) {
                Packet packet = (Packet) object;
                if (packet.getMessage() != null) {
                	String clientPlayerName = ChessGameUtils.getOtherPlayerName();
                	chat.appendStr(new ChatPacket("\n" + clientPlayerName + ": " + packet.getMessage(), packet.getSmpSet(), packet.getColor()));
                	chat.setTxtPaneCaretPosition();
                }
                if (packet.getImgPath() != null) {
                    chat.insertTxtPaneIcon(packet);
                    chat.setTxtPaneCaretPosition();
                }
                if (packet.getPlayerIconPath() != null) {
                    ConnectionBridge.this.setPlayerIconPath(object);
                }
                if (packet.getGuestName() != null) {
                    ConnectionBridge.this.setGuestName(object);
                }
                if (packet.getRestartGame() != null) {
                    ConnectionBridge.this.restartGame();
                }
                if (packet.getConfirmRestart() != null) {
                    view.restartClientGame();
                }
            } else if (object instanceof BehaviorResult) {
                BehaviorResult result = (BehaviorResult) object;
                ArrayList<SquareModel> squareModels = result.getSquareModels();
                SquareModel squareModel1 = squareModels.get(0);
                SquareModel squareModel2 = squareModels.get(1);
                ChessGamePoint srcPoint = squareModel1.getPosition();
                Piece piece = ChessData.getChessData().getSquareModel(srcPoint.getX(), srcPoint.getY()).getPiece();
                ChessData.getChessData().deleteObserver(ConnectionBridge.this);
                piece.tryToMove(srcPoint, squareModel2.getPosition());
                ChessData.getChessData().addObserver(ConnectionBridge.this);
                //data.move((Integer) list.get(0), (Integer) list.get(1));TODO:
            }
        }
    }

    /**
     * The method getOutputStream returns the outputStream to the caller
     * @return oos as an ObjectOutputStream
     */
    public ObjectOutputStream getOutputStream() {
        return oos;
    }

    /**
     * The method restartGame simply restarts the client game
     * when it is needed it is calling the method from the view c;lass
     */
    public void restartGame() {
    	String clientPlayerName = ChessGameUtils.getCurrentPlayerName();
        int returnValue = JOptionPane.showConfirmDialog(view, clientPlayerName + "would you like to restart the game", "Confirmation Message", JOptionPane.YES_NO_OPTION);
        if (returnValue == JOptionPane.YES_OPTION) {
            Packet packet = new Packet();
            packet.setConfirmRestart("restart game");
            try {
                this.oos.writeObject(packet);
                this.oos.flush();
            } catch (IOException ex) {
                chat.appendStr(new ChatPacket(ex.toString(),  smpSet, color));
            }
            view.restartClientGame();
        }
    }

    /**
     * The method killThread simply sets the boolean value
     * isAlive to false this forces the run method to exit thus killing the thread
     */
    public void killThread() {
        readData.isAlive = false;
    }

    public void sendPlayerIconInfo(String info)
    {
        try {
        	Packet packet = new Packet();
            packet.setPlayerIconPath(info);
        	ObjectOutputStream outputStream = getOutputStream();
        	if(outputStream != null)
        	{
        		outputStream.writeObject(packet);
        		outputStream.flush();
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    	
    }
    /**
     * The method setPlayericonPath simply sets the iconPath
     * of the player image this method is used to update the view of the client
     * when a client changes his image the path to the image is send to the other client that is connected
     * so he would also see that image changed
     * @param object as an Object
     */
    public void setPlayerIconPath(Object object) {
        Packet packet = (Packet) object;
        if (data.isServer()) {
            data.setPlayerTwoImage(packet);
        } else {
        	data.setPlayerOneImage(packet);
        }
        data.notifyView();
    }
    
    /**
     * The method setGuestName simply sets the guest name
     * of the player this method is used to update the view of the client
     * when a client changes his name the name is send to the other client that is connected
     * so he would also see that name changed
     * @param object as an Object
     */
    public void setGuestName(Object object) {
        Packet packet = (Packet) object;
        if (data.isServer()) {
        	data.setPlayerTwoName(packet);
        } else {
        	data.setPlayerOneName(packet);
        }
        data.notifyView();
    }
}
