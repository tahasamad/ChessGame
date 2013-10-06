/*
 * Description: Class ChessBoardView.java
 * Author: Dimtri Pankov
 * Date: 26-Dec-2010
 * Version: 1.0
 */
package ChessGameKenai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;

import GameElements.ElementColor;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.PieceType;

/**
 * The ChessBoardView Class is main view of the chess game it consists of the
 * chess board, squares and pieces.This view is an observer so each time the change
 * occurs in the model(Chess_Data) it is notified immediately and changes its view accordingly
 * This class is pure GUI of our chess game it interacts with the user by showing him the changes he
 * made like piece move game won and etc.. This view has a lot of options user can change his name
 * or he can change his icon, there is also possible to save the game as well as the player
 * @author Dimitri Pankov
 * @see Observer Interface
 * @see JFrame
 * @version 1.1
 */
public class ChessBoardView extends JFrame implements Observer {

    private JComponent mainPanel, northPanel;
    private CostumPanel mainNorthPanel, mainEastPanel, eastPanel3, eastPanel2;
    private JMenuBar menuBar;
    private JMenu fileMenu, helpMenu, optionsMenu;
    private JScrollPane whiteCapturedPiecesScroll, blackCapturedPiecesScroll;
    private JComponent eastPanel1, northButtonPanel, buttonPanel;
    private CapturedPieces whiteCapturedPiecesPanel, blackCapturedPiecesPanel;
    private JLabel whoseTurnLabel;
    private PlayerInfoPanel player1InfoPanel, player2InfoPanel;
    private JButton restartButton;
    private JTabbedPane tabs;
    private JFileChooser fileChooser = new JFileChooser(".");
    private int returnValue = 0;
    private Board board;
    private static ConnectionBridge bridge;
    private Chat chat;
    private Color color = Color.ORANGE;
    private SimpleAttributeSet smpSet = new SimpleAttributeSet();
    private JMenuItem optionsMenuConnectAsServer, optionsMenuConnectAsClient;
    private JTextArea tArea = new JTextArea();
    private JScrollPane scroll = new JScrollPane(tArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    /**
     * OverLoaded constructor that receives Chess_Data object's address in the system
     * for later communication with the object
     * @param Chess_Data.getChessData() address of the Chess_Data object
     */
     public ChessBoardView() {
        tArea.setText(ChessGameConstants.tAreaTopString);
        tArea.setFont(new Font("Verdana", Font.PLAIN, 16));
        tArea.setOpaque(false);
        tArea.setEditable(false);
        board = new Board(ChessBoardView.this);
        Chess_Data.getChessData().addObserver(board);
        Chess_Data.getChessData().addObserver(this);
        //Chess_Data.getChessData().setSquares(board.getSquares());
        
        chat = new Chat(this);

        //ADD A WINDOW LISTENER TO THE JFRAME
        this.addWindowListener(new WindowAdapter() {

            /**
             * The method windowClosing is executed when the user
             * clicks on the window closing button at the top right corner
             * @param e WindowEvent object that is generated when window is being closed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                ChessBoardView.this.promptUserOnExit();
            }
        });

        //LOADS THE ARRAY LIST WITH TWO PLAYER OBJECTS AND GIVES THEM INITIAL VALUES
        Chess_Data.getChessData().getPlayers().add(new Player("Player1", "Icons/hercules10.gif"));
        Chess_Data.getChessData().getPlayers().add(new Player("Player2", "Icons/hercules12.gif"));

        //CREATE JBUTTON RESTART AND ADD ACTION LISTENER TO IT THROUGH THE ANONYMOUS CLASS
        restartButton = new JButton("Restart", new ImageIcon(getClass().getResource("Icons/start.png")));
        restartButton.addActionListener(new ActionListener() {

            /**
             * The method actionPerformed is the method
             * that is inherited from ActionListener Interface
             * @param e ActionEvent object that is generated when listener detects an action
             */
            public void actionPerformed(ActionEvent e) {
                if (!Chess_Data.getChessData().isGameOnLine()) {
                    ChessBoardView.this.restartLocalGame();
                } else {
                    ChessBoardView.this.restartOnlineGame();
                }
            }
        });

        //SET BUTTON'S PREFERRED SIZE
        restartButton.setPreferredSize(new Dimension(114, 44));

        //CREATE JPANEL
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        //CREATE JPANEL
        northButtonPanel = new JPanel(new GridLayout(1, 2));
        northButtonPanel.setOpaque(false);


        //CREATE AND INITILAIZE MAIN NORTH PANEL
        mainNorthPanel = new CostumPanel("Icons/background.jpg", new BorderLayout());

        //CREATE AND INITILALIZE WHOSE TURN LABEL
        whoseTurnLabel = new JLabel("", SwingConstants.CENTER);
        whoseTurnLabel.setFont(new Font("Times Roman", Font.PLAIN, 30));
        whoseTurnLabel.setForeground(Color.WHITE);
        this.setupTextOfWhoseTurnLabel();

        //CREATE AND INITIALIZE PLAYER INFO PANEL
        
        //CREATE AN ANONYMOUS CLASS THAT IMPLEMENTS ACTION LISTENER

        player1InfoPanel = new PlayerInfoPanel(Chess_Data.getChessData().getPlayers().get(0));
        player1InfoPanel.createTimer();
        player1InfoPanel.startTimer();

        player2InfoPanel = new PlayerInfoPanel(Chess_Data.getChessData().getPlayers().get(1));
        player2InfoPanel.createTimer();
        player2InfoPanel.stopTimer();
        
        //CREATE AND INITIALIZE NORTH PANEL
        northPanel = new JPanel(new GridLayout(1, 2));
        northPanel.setOpaque(false);
        //northPanel.setBackground(Color.WHITE);

        //ADD ALL THE COMPONENTS TO THE NORTH PANEL
        northPanel.add(player1InfoPanel);
        northPanel.add(player2InfoPanel);

        buttonPanel.add(restartButton);

        //ADD ALL THE COMPONENTS TO THE NORTH BUTTON PANEL
        northButtonPanel.add(whoseTurnLabel);
        northButtonPanel.add(buttonPanel,BorderLayout.LINE_END);

        //ADD ALL THE COMPONENTS TO THE MAIN NORTH PANEL
        mainNorthPanel.add(northPanel, BorderLayout.BEFORE_LINE_BEGINS);
        mainNorthPanel.add(northButtonPanel, BorderLayout.SOUTH);

        //CREATE JMENUBAR AND SET IT TO THE JFRAME
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);


        //CREATE JMENU CALLED FILE
        fileMenu = new JMenu("File");

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM NEW GAME
        fileMenu.add(new JMenuItem("New Game")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                if (Chess_Data.getChessData().isGameOnLine()) {
                    int returnValue = JOptionPane.showConfirmDialog(ChessBoardView.this, "The Connection will be lost would you like to proceed?Y/N", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                    if (returnValue == JOptionPane.YES_OPTION) {
                    	Chess_Data.destroy();
                        ChessBoardView.this.dispose();
                        EventQueue.invokeLater(new Runnable() {

                            public void run() {
                                new Start_Game();
                            }
                        });
                    }
                } else {
                	Chess_Data.destroy();
                    ChessBoardView.this.dispose();
                    EventQueue.invokeLater(new Runnable() {

                        public void run() {
                            new Start_Game();
                        }
                    });
                }

            }
        });

        //ADD SEPARATOR
        fileMenu.addSeparator();

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM SAVE PLAYER
        fileMenu.add(new JMenuItem("Save Player")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                Chess_Data.getChessData().savePlayer();
            }
        });

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM LOAD PLAYER
        fileMenu.add(new JMenuItem("Load Player")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                Chess_Data.getChessData().loadPlayer();

            }
        });

        fileMenu.addSeparator();

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM LOAD
        fileMenu.add(new JMenuItem("Save Game")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                Chess_Data.getChessData().save();
            }
        });

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM SAVE
        fileMenu.add(new JMenuItem("Load Game")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                Chess_Data.getChessData().load();
                ChessBoardView.this.board.removeAllPieces();
                ChessBoardView.this.board.populateBoard();
                ChessBoardView.this.loadCapturedPieces();
            }
        });

        fileMenu.addSeparator();

        fileMenu.add(new JMenuItem("Save As...")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                if ((returnValue = fileChooser.showSaveDialog(ChessBoardView.this)) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Chess_Data.getChessData().save(file);
                }
            }
        });

        fileMenu.add(new JMenuItem("Load from file")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                if ((returnValue = fileChooser.showOpenDialog(ChessBoardView.this)) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    Chess_Data.getChessData().load(file);
                    ChessBoardView.this.board.removeAllPieces();
                    //ChessBoardView.this.board.getPieces().clear();
                    ChessBoardView.this.board.populateBoard();
                    ChessBoardView.this.loadCapturedPieces();
                }
            }
        });

        fileMenu.addSeparator();

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM EXIT
        fileMenu.add(new JMenuItem("Exit")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        optionsMenu = new JMenu("Options");

        optionsMenu.add(optionsMenuConnectAsClient = new JMenuItem("Connect As Client")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {

                try {
                    new Thread(new Runnable() {

                        InetAddress ipAddress = InetAddress.getByName(JOptionPane.showInputDialog("Enter Server IP"));

                        public void run() {
                            ChessBoardView.this.playOnLine();
                            ChessBoardView.this.reEnableMenuItems(false);
                            bridge = new ConnectionBridge(Chess_Data.getChessData(), ChessBoardView.this, false, ipAddress, chat);
                            Chess_Data.getChessData().addObserver(bridge);
                        }
                    }).start();
                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(ChessBoardView.this, ex.toString());
                    ChessBoardView.this.reEnableMenuItems(true);
                }
            }
        });
        optionsMenu.add(optionsMenuConnectAsServer = new JMenuItem("Connect As Server")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {

                new Thread(new Runnable() {

                    public void run() {
                        ChessBoardView.this.playOnLine();
                        ChessBoardView.this.reEnableMenuItems(false);
                        bridge = new ConnectionBridge(Chess_Data.getChessData(), ChessBoardView.this, true, null, chat);
                        Chess_Data.getChessData().addObserver(bridge);
                    }
                }).start();
            }
        });

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM FLIP BOARD
        optionsMenu.add(new JMenuItem("Flip Board")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
            	if (board.getCurrentBoard() == BoardFlipMode.Normal)
            	{
            		board.setCurrentBoard(BoardFlipMode.Flipped);
            	}
            	else 
            	{
            		board.setCurrentBoard(BoardFlipMode.Normal);
            	}
            	
            	board.flipBoard();
                /*if (board.getCurrentBoard() == Board.NORMAL_BOARD) {
                    board.setBoard(Board.FLIPPED_BOARD);
                } else {
                    board.setBoard(Board.NORMAL_BOARD);
                }
                board.flipBoard();*/
            }
        });

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM NEW GAME
        optionsMenu.add(new JMenuItem("Choose Icon")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                new Choose_Icon(Chess_Data.getChessData(), bridge);
            }
        });

        //ADD AN ACTIONLISTENER TO THE JMENU ITEM SET PLAYER NAMES
        optionsMenu.add(new JMenuItem("Set Player Names")).addActionListener(new ActionListener() {

            /**
             * The method actionPerformed needs to be overridden in our class
             * @param e ActionEvent object that is generated when listener detects action
             */
            public void actionPerformed(ActionEvent e) {
                if (!Chess_Data.getChessData().isGameOnLine()) {
                    ChessBoardView.this.chooseNameLocal();
                } else {
                    ChessBoardView.this.chooseNameOnLine();
                }
            }
        });

        //CREATE JMENU
        helpMenu = new JMenu("Help");

        //ADD JMENUITEMS TO THE HELP MENU
        helpMenu.add(new JMenuItem("Chess Rules")).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new HelpTopics(ChessBoardView.this);
            }
        });
         helpMenu.add(new JMenuItem("User Guide")).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new UserManual(ChessBoardView.this);
            }
        });

        helpMenu.add(new JMenuItem("About")).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new About(ChessBoardView.this);
            }
        });

        //ADD MENUS TO THE MENUBAR
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);


        whiteCapturedPiecesPanel = new CapturedPieces(ElementColor.White);
        whiteCapturedPiecesScroll = new JScrollPane(whiteCapturedPiecesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        whiteCapturedPiecesScroll.setOpaque(false);

        //CREATE AND SET JPANEL'S LAYOUT
        mainPanel = new JPanel(new GridLayout(1, 1));
        mainPanel.add(board);

        blackCapturedPiecesPanel = new CapturedPieces(ElementColor.Black);

        //CREATE JSCROLLPANE AND ADD JPANEL TO IT
        blackCapturedPiecesScroll = new JScrollPane(blackCapturedPiecesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //ADD TWO OBSERVERS TO THE DATA WHICH ARE CAPTURED PIECES PANELS
        Chess_Data.getChessData().addObserver(whiteCapturedPiecesPanel);
        Chess_Data.getChessData().addObserver(blackCapturedPiecesPanel);

        mainEastPanel = new CostumPanel("Icons/background.jpg", new GridLayout(1, 1));

        //CREATE JTABBED PANE
        tabs = new JTabbedPane();
        eastPanel3 = new CostumPanel("Icons/background.jpg", new BorderLayout());

        //SET BORDER FOR EAST PANEL 3
        TitledBorder t = new TitledBorder("History Tab");
        t.setTitleColor(Color.WHITE);
        t.setTitleFont(new Font("New Times Roman", Font.PLAIN, 18));
        eastPanel3.setBorder(t);
        eastPanel3.add(scroll);
        eastPanel2 = new CostumPanel("Icons/background.jpg", new GridLayout(8, 4));

        //SET BORDER  FOR EAST PANEL 2
        TitledBorder t1 = new TitledBorder("Smilies");
        t1.setTitleColor(Color.WHITE);
        t1.setTitleFont(new Font("New Times Roman", Font.PLAIN, 18));
        eastPanel2.setBorder(t1);

        //ADD SMILIES TO THE PANEL
        for (int i = 0; i < 32; i++) {
            eastPanel2.add(new Emoticons(ChessBoardView.this, "smileys/smiley" + (i + 1) + ".gif", chat));
        }

        //CREATE EAST PANEL
        eastPanel1 = new JPanel(new GridLayout(2, 1));
        eastPanel1.add(whiteCapturedPiecesScroll);
        eastPanel1.add(blackCapturedPiecesScroll);

        //ADD COMPONENTS TO ALL THE TABS
        tabs.addTab("Captured", eastPanel1);
        tabs.addTab("Smilies", eastPanel2);
        tabs.addTab("History", eastPanel3);
        tabs.setBackgroundAt(0, Color.ORANGE);
        tabs.setBackgroundAt(1, Color.ORANGE);
        tabs.setBackgroundAt(2, Color.ORANGE);
        mainEastPanel.add(tabs);

        this.setIconImage(new ImageIcon("ChessPieces/wking46.gif").getImage());

        //ADD EVERYTHING TO THE CONTAINER
        
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(null);        
        JScrollPane mainScrollPane = new JScrollPane(mainContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(mainScrollPane);
        
        mainContainer.add(mainNorthPanel);
        mainContainer.add(board);
        mainContainer.add(mainEastPanel);
        mainContainer.add(chat);
        
        mainNorthPanel.setBounds(0, 0, 757, 170);
        mainNorthPanel.repaint();
        mainEastPanel.setBounds(520, 170, 230, 531);
        mainEastPanel.repaint();
        board.setBounds(0, 170, 583, 592);
        board.repaint();
        chat.setBounds(0, 693, 757, 211);
        chat.repaint();

        //SET LAYOUT PROPERTIES
        this.setTitle("Chess Game");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation(333, 6);
        this.setResizable(false);
        this.setSize(757, 700);
        mainContainer.setSize(757, 633);
        mainContainer.setPreferredSize(new Dimension(757, 870));
        this.setVisible(true);
    }

    /**
     * The method update is inherited from Observer Interface
     * needs to be implemented in our class
     * @param o Observable object
     * @param arg any object
     */
    public void update(Observable o, Object arg) {
        //SET APROPRIATE LABEL TEXT DEPENDING ON WHOSE TURN IT IS
    	this.setupTextOfWhoseTurnLabel();
    	this.adjustHandlerAndTimers();
        player1InfoPanel.resetPanelInformation();
        player2InfoPanel.resetPanelInformation();
    }
    
    private void setupTextOfWhoseTurnLabel()
    {
    	Chess_Data data = Chess_Data.getChessData();
    	this.whoseTurnLabel.setText(Chess_Data.getChessData().isWhiteTurn() ? (data.getPlayers().get(0).getName() + ChessGameConstants.turnToPlay) : (data.getPlayers().get(1).getName() + ChessGameConstants.turnToPlay));
    }
    
    private void adjustHandlerAndTimers()
    {
    	Chess_Data data = Chess_Data.getChessData();
    	if (data.isWinner()) {
	        if (data.getCapturedPieces().get(data.getCapturedPieces().size() - 1).getColor() == ElementColor.Black) {
	            whoseTurnLabel.setText(data.getPlayers().get(0).getName() + ChessGameConstants.hasWon);
	            int wins = data.getPlayers().get(0).getNumberOfWins();
	            data.getPlayers().get(0).setNumberOfWins(++wins);
	        } else {
	            whoseTurnLabel.setText(data.getPlayers().get(1).getName() + ChessGameConstants.hasWon);
	            int wins = data.getPlayers().get(1).getNumberOfWins();
	            data.getPlayers().get(1).setNumberOfWins(++wins);
	        }
	        this.board.removeHandlers();
	        this.stopTimer();
	    }
    	else
    	{
		    if (data.isGameOnLine()) {
			    this.board.distributeOnLineListeners();
		    }
		    if(data.isWhiteTurn())
		    {
		    	this.player1InfoPanel.startTimer();
		    	this.player2InfoPanel.stopTimer();
		    }
		    else
		    {
		    	this.player1InfoPanel.stopTimer();
		    	this.player2InfoPanel.startTimer();
		    }
    	}
    }
	    
    /**
     * The method chooseNameLocal is used if the user
     * wants to change its name and the game is local game
     * this method is used when the user is playing the game locally
     */
    public void chooseNameLocal() {
        if (!Chess_Data.getChessData().isGameOnLine()) {
            String player1Name = "";
            String player2Name = "";

            //SET PLAYER NAMES APROPRIATLY
            player1Name = JOptionPane.showInputDialog("Enter First Player Name");
            player2Name = JOptionPane.showInputDialog("Enter Second Player Name");

            if (player1Name != null && player2Name != null) {

                Chess_Data.getChessData().getPlayers().get(0).setName(player1Name);
                Chess_Data.getChessData().getPlayers().get(1).setName(player2Name);

                //SET APROPRIATE LABEL TEXT DEPENDING ON WHOSE TURN IT IS
                whoseTurnLabel.setText(Chess_Data.getChessData().isWhiteTurn() ? (Chess_Data.getChessData().getPlayers().get(0).getName() + " turn to play") : (Chess_Data.getChessData().getPlayers().get(1).getName() + " turn to play"));
                
                player1InfoPanel.updatePlayerName();
                player2InfoPanel.updatePlayerName();
            }
        }
    }

    /**
     * The method chooseNameOnLine is used when the user
     * is playing online so the name he chose for himself will be shown
     * on the other client view it is done by sending the name through a socket
     * and setting it in the players ArrayList in the data each time the view is notified
     * it extracts the name from the data class and displays it
     */
    public void chooseNameOnLine() {
        String playerName = "";
        if (Chess_Data.getChessData().isServer()) {
            playerName = JOptionPane.showInputDialog("Enter Your Name");
            if (playerName != null) {
            	Chess_Data.getChessData().getPlayers().get(0).setName(playerName);
                player1InfoPanel.updatePlayerName();
            }
        } else {
            playerName = JOptionPane.showInputDialog("Enter Your Name");
            if (playerName != null) {
            	Chess_Data.getChessData().getPlayers().get(1).setName(playerName);
                player2InfoPanel.updatePlayerName();
            }
        }
        try {
            Packet packet = new Packet();
            packet.setGuestName(playerName);
            bridge.getOutputStream().writeObject(packet);
            bridge.getOutputStream().flush();
        } catch (Exception e) {
            chat.appendStr(e.getMessage(), smpSet, color);
        }

        //SET APROPRIATE LABEL TEXT DEPENDING ON WHOSE TURN IT IS
        whoseTurnLabel.setText(Chess_Data.getChessData().isWhiteTurn() ? (Chess_Data.getChessData().getPlayers().get(0).getName() + " turn to play") : (Chess_Data.getChessData().getPlayers().get(1).getName() + " turn to play"));
    }

    /**
     * This method simply starts the timer when it is called
     * by starting the first player timer then the first player timer
     * will start the second player timer an so on.....
     */
    public void startTimer() {
    	Chess_Data.getChessData().isWhiteTurn(true);
        player1InfoPanel.startTimer();
    }

    /**
     * The method loadCapturedPieces is used when the saved game is loaded back and
     * the capturedPieces ArrayList is not empty it takes the captured pieces and adds them to the appropriate
     * captured pieces panel white or black
     */
    public void loadCapturedPieces() {
    	Chess_Data data = Chess_Data.getChessData();
    	ArrayList<Non_Visual_Piece> capturedPiece = data.getCapturedPieces();
        for (int i = 0; i < capturedPiece.size(); i++) {
        	Non_Visual_Piece pieceModel = data.getCapturedPieces().get(i); 
            if (pieceModel.getColor() == ElementColor.White) {
                whiteCapturedPiecesPanel.add(new Piece(pieceModel));
                whiteCapturedPiecesPanel.revalidate();
                whiteCapturedPiecesPanel.repaint();
            } else {
                blackCapturedPiecesPanel.add(new Piece(pieceModel));
                blackCapturedPiecesPanel.revalidate();
                blackCapturedPiecesPanel.repaint();
            }
        }
    }

    /**
     * The method restartLocalGame restarts local game that means
     * that if the user plays locally we use this method if the user plays
     * on the same computer locally
     */
    public void restartLocalGame() {
    	this.board.resetSelectedSquare();
    	Chess_Data data = Chess_Data.getChessData();
        data.discardState();
        this.whiteCapturedPiecesPanel.removeAll();
        this.blackCapturedPiecesPanel.removeAll();
        this.tArea.setText(ChessGameConstants.tAreaTopString);
        this.player1InfoPanel.resetTimerLabel();
        this.player2InfoPanel.resetTimerLabel();
        data.notifyView();
        data.loadActivePiecesFromSavedState();
        this.board.populateBoard();
        this.board.addHandlers();
    }

    /**
     * The method restartOnlineGame is used if the user plays online
     * against an opponent and wants to restart the game
     * The information is sent to the client on the other side and restarts its game as well
     */
    public void restartOnlineGame() {
        if (Chess_Data.getChessData().isGameOnLine()) {
            try {
                Packet packet = new Packet();
                packet.setRestartGame("restart game");
                bridge.getOutputStream().writeObject(packet);
                bridge.getOutputStream().flush();
            } catch (Exception e) {
                chat.appendStr(e.getMessage(), smpSet, color);
            }
        }
    }

    /**
     * The method restartClientGame online game
     * appropriately removes captured pieces creates new visual and non visual pieces
     * adds and removes listeners depending on who's server or a client
     */
    public void restartClientGame() {
    	this.board.resetSelectedSquare();
    	Chess_Data data = Chess_Data.getChessData();
        data.discardState();
        this.whiteCapturedPiecesPanel.removeAll();
        this.blackCapturedPiecesPanel.removeAll();
        this.tArea.setText(ChessGameConstants.tAreaTopString);
        this.player1InfoPanel.resetTimerLabel();
        this.player2InfoPanel.resetTimerLabel();
        data.notifyView();
        data.loadActivePiecesFromSavedState();
        this.board.populateBoard();
        this.adjustHandlerAndTimers();
    }

    /**
     * The method getConnectionInstance is a static method here
     * we use a Singleton pattern to get the instance of the ConnectionBrodge class
     * @return bridge as a ConnectionBridge
     */
    public static ConnectionBridge getConnectionInstance() {
        return bridge;
    }

    /**
     * getPanel simply returns a JPanel to the caller
     * @return eastPanel2 as a JPanel
     */
    public JPanel getPanel() {
        return eastPanel2;
    }

    /**
     * The method reEnableMenuItems simply set enable false or true
     * to the options menu item connect as server and connect as client
     * @param enable as a boolean
     */
    public void reEnableMenuItems(boolean enable) {
        optionsMenuConnectAsServer.setEnabled(enable);
        optionsMenuConnectAsClient.setEnabled(enable);
    }

    /**
     * The method stopTimer stops both timers
     * whenever we need we could call this method and both timers would
     * In our case we would use this method if the game was won by either party
     */
    public void stopTimer() {
        player1InfoPanel.stopTimer();
        player2InfoPanel.stopTimer();
    }

    /**
     * The method promptUserOnExit simply asks the user if he wants to save the game or not
     * if yes the JFileChoose pops up to save the game if no or yes was pressed the program will exit
     * if the user wants to cancel the exit there is a third option cancel which disposes of the dialog
     * but the game is still there
     */
    public void promptUserOnExit() {
        returnValue = JOptionPane.showConfirmDialog(ChessBoardView.this, "Would you like to save the game?".toUpperCase(), "Information Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("Icons/save.png")));
        if (returnValue == JOptionPane.YES_OPTION) {
            if ((returnValue = fileChooser.showSaveDialog(ChessBoardView.this)) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    Chess_Data.getChessData().save(file);
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(ChessBoardView.this, "Error: " + er.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
            }
            if (bridge != null) {
                bridge.killThread();
            }
        } else if (returnValue == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    /**
     * The method playOnLine simply starts the game on line
     * it restarts the game clears the captured pieces sets the isServer and isGameOnLine to true
     * and calls another method of this class which is restartLocalGame to complete the job
     */
    public void playOnLine() {
    	this.restartClientGame();
        Chess_Data.getChessData().isGameOnLine(true);
        board.isFirstTime(true);
        player1InfoPanel.stopTimer();
    }

    /**
     * The method flipClientBoard automatically flips the
     * board for the client side if the game is played online
     * for easier view on the client side however if the client decides to switch
     * back the view he would simply go in the options menu and change the flip back
     */
    public void flipClientBoard() {
    	Chess_Data data = Chess_Data.getChessData();
        if (!data.isServer()) {
            //board.removeAll();
            board.setCurrentBoard(BoardFlipMode.Flipped);
            board.flipBoard();
        }
    }

    /**
     * The method getMoves simply returns the text area of moves to the caller
     * @return as a JTextArea
     */
    public JTextArea getMoves() {
        return tArea;
    }

    /**
     * The main method of the class
     * @param args command line arguments if any
     */
    public static void main(String args[]) {
        new StartUpWindow();
    }
}
