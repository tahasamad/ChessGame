package UIMenus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


import ChessGameKenai.BoardFlipMode;
import ChessGameKenai.Chat;
import ChessGameKenai.ChessBoardView;
import ChessGameKenai.Chess_Data;
import ChessGameKenai.ChoosePlayerIcon;
import ChessGameKenai.ConnectionBridge;

public class OptionsMenu extends JMenu
{
	private JMenuItem clickableMenuItem;
	private JMenuItem connectAsClient;
	private JMenuItem connectAsServer;
	private ChessBoardView chessBoard;
	private Chat chat;
	
	public OptionsMenu (String title, ChessBoardView boardRef, Chat chatRef) {
		super (title);
		chessBoard = boardRef;
		chat = chatRef;
	}
	
	public void createView ()
	{
		addMenuTab (MenuItemType.ConnectAsClient);
		addMenuTab (MenuItemType.ConnectAsServer);
		addMenuTab (MenuItemType.FlipBoard);
		addMenuTab (MenuItemType.ChooseIcon);
		addMenuTab (MenuItemType.SetPlayersName);
		
	}
	
	public void addMenuTab (MenuItemType menuItem)
	{
		switch (menuItem)
		{
		case ConnectAsClient:
			connectAsClient = new JMenuItem("Connect As Client");
			addActionListenerToMenuItem (connectAsClient, menuItem);
			this.add(connectAsClient);
    		break;
    	case ConnectAsServer:
    		connectAsServer = new JMenuItem("Connect As Server");
    		addActionListenerToMenuItem (connectAsServer, menuItem);
    		this.add(connectAsServer);
    		break;
    	case FlipBoard:
    		clickableMenuItem = new JMenuItem("Flip Board");
    		addActionListenerToMenuItem (clickableMenuItem, menuItem);
    		this.add(clickableMenuItem);
    		break;
    	case ChooseIcon:
    		clickableMenuItem = new JMenuItem("Choose Icon");
    		addActionListenerToMenuItem (clickableMenuItem, menuItem);
    		this.add(clickableMenuItem);
    		break;
    	case SetPlayersName:
    		clickableMenuItem = new JMenuItem("Set Player Names");
    		addActionListenerToMenuItem (clickableMenuItem, menuItem);
    		this.add(clickableMenuItem);
    		break;
		}
	}
	
	public void addActionListenerToMenuItem (JMenuItem menuItem, final MenuItemType menuItemType)
	{
		menuItem.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
            	switch (menuItemType)
            	{
            	case ConnectAsClient:
            		connectAsClient ();
            		break;
            	case ConnectAsServer:
            		connectAsServer ();
            		break;
            	case FlipBoard:
            		flipBoard ();
            		break;
            	case ChooseIcon:
            		chooseIcon ();
            		break;
            	case SetPlayersName:
            		setPlayerNames ();
            		break;
            	}
            }
		});
		
	}
	
	private void connectAsClient ()
	{
		try {
			new Thread(new Runnable() {

				InetAddress ipAddress = InetAddress.getByName(JOptionPane.showInputDialog("Enter Server IP"));

				public void run() {
					chessBoard.playOnLine();
					chessBoard.reEnableMenuItems(false);
					chessBoard.setConnectionBridge(new ConnectionBridge(Chess_Data.getChessData(), chessBoard, false, ipAddress, chat));
					Chess_Data.getChessData().addObserver(ChessBoardView.getConnectionInstance());
					Chess_Data.getChessData().setIsClientConnected(true);
				}
			}).start();
		} catch (UnknownHostException ex) {
			JOptionPane.showMessageDialog(chessBoard, ex.toString());
			chessBoard.reEnableMenuItems(true);
		}
	}
	
	private void connectAsServer ()
	{
		new Thread(new Runnable() {

			public void run() {
				chessBoard.playOnLine();
				chessBoard.reEnableMenuItems(false);
				chessBoard.setConnectionBridge(new ConnectionBridge(Chess_Data.getChessData(), chessBoard, true, null, chat));
				Chess_Data.getChessData().addObserver(ChessBoardView.getConnectionInstance());
				Chess_Data.getChessData().setIsClientConnected(false);
			}
		}).start();

	}

	private void flipBoard ()
	{
		if (chessBoard.getCurrentFlipMode() == BoardFlipMode.Normal)
		{
			chessBoard.setCurrentFlipMode(BoardFlipMode.Flipped);
		}
		else 
		{
			chessBoard.setCurrentFlipMode(BoardFlipMode.Normal);
		}

		chessBoard.flipChessBoard();

	}
	
	private void chooseIcon ()
	{
		new ChoosePlayerIcon(ChessBoardView.getConnectionInstance());
	}
	
	private void setPlayerNames ()
	{
		 if (!Chess_Data.getChessData().isGameOnLine()) {
             chessBoard.chooseNameLocal();
         } else {
             chessBoard.chooseNameOnLine();
         }
	}
	
	public JMenuItem getConnectAsClientRef ()
	{
		return connectAsClient;
	}
	
	public JMenuItem getConnectAsServerRef ()
	{
		return connectAsServer;
	}
	
	private enum MenuItemType
	{
		ConnectAsClient(1),
		ConnectAsServer(2),
		FlipBoard(3),
		ChooseIcon(4),
		SetPlayersName(5);
		
		
		public int currentValue = 0;

		private MenuItemType(int value) {
			currentValue = value;
		}

		public int value() {
			return currentValue;
		}
	}

}
