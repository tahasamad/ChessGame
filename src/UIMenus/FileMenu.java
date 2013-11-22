package UIMenus;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ChessGameKenai.ChessBoardView;
import ChessGameKenai.Chess_Data;
import ChessGameKenai.Start_Game;

public class FileMenu extends JMenu{
	

	private JMenuItem clickableMenuItem;
	private ChessBoardView chessBoard;
	private JFileChooser fileChooser;
	private int returnValue = 0;

	public FileMenu (String title, ChessBoardView boardRef, JFileChooser fileChooserRef, int retVal) {
		super (title);
		chessBoard = boardRef;
		fileChooser = fileChooserRef;
		returnValue = retVal;
	}
	
	
	public void createView ()
	{
		addMenuTab (MenuItemType.NewGame);
		addMenuTab (MenuItemType.SavePlayer);
		addMenuTab (MenuItemType.LoadPlayer);
        addUISeperator ();
		addMenuTab (MenuItemType.SaveGame);
		addMenuTab (MenuItemType.LoadGame);
		addUISeperator ();
		addMenuTab (MenuItemType.SaveGameAs);
		addMenuTab (MenuItemType.LoadGameFromFile);
		addUISeperator ();
		addMenuTab (MenuItemType.Exit);
	}

	public void addMenuTab (MenuItemType menuItem)
	{
		switch (menuItem)
		{
		case NewGame:
			clickableMenuItem = new JMenuItem("New Game");
			break;
			
		case SavePlayer:
			clickableMenuItem = new JMenuItem("Save Player");
			break;
			
		case LoadPlayer:
			clickableMenuItem = new JMenuItem("Load Player");
			break;
			
		case SaveGame:	
			clickableMenuItem = new JMenuItem("Save Game");
			break;
			
		case LoadGame:
			clickableMenuItem =  new JMenuItem("Load Game");
			break;
			
			
		case SaveGameAs:
			clickableMenuItem =  new JMenuItem("Save As...");
			 break;
			 
		case LoadGameFromFile:
			clickableMenuItem =  new JMenuItem("Load from file");
			break;
		
		case Exit:
			clickableMenuItem =  new JMenuItem("Exit");
			break;

		}
		
		addActionListenerToMenuItem(menuItem);
		this.add(clickableMenuItem);
	}
	
	public void addActionListenerToMenuItem (final MenuItemType menuItem)
	{
		clickableMenuItem.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
            	switch (menuItem)
            	{
            	case NewGame:
            		newGame ();
            		break;
            	case SavePlayer:
            		savePlayer ();
            		break;
            		
            	case LoadPlayer:
            		loadPlayer ();
            		break;
            		
            	case SaveGame:
            		saveGame ();
            		break;
            		
            	case LoadGame:
            		loadGame ();
            		break;
            		
            	case SaveGameAs:
            		saveGameAs ();
            		break;
            		
            	case LoadGameFromFile:
            		loadFromFile();
            		break;
            		
            	case Exit:
            		exitGame();
            		break;
            	}
                
            }
        });
	}
	
	
	private void saveGameAs ()
	{
		 if ((returnValue = fileChooser.showSaveDialog(chessBoard)) == JFileChooser.APPROVE_OPTION) {
             File file = fileChooser.getSelectedFile();
             Chess_Data.getChessData().save(file);
         }
	}
	
	private void loadGame ()
	{
		chessBoard.clearLocalGame();
    	if(!Chess_Data.getChessData().load())
        {
    		Chess_Data.getChessData().discardActivePiecesInSavedState();
    		Chess_Data.getChessData().loadActivePiecesFromSavedState();
        }
    	chessBoard.populateChessBoard();
        chessBoard.loadCapturedPieces();
	}
	
	private void saveGame ()
	{
		Chess_Data.getChessData().save();
	}
	private void loadPlayer ()
	{
         Chess_Data.getChessData().loadPlayer();
	}
	
	private void newGame ()
	{
		if (Chess_Data.getChessData().isGameOnLine()) 
		{
			int returnValue = JOptionPane.showConfirmDialog(chessBoard, "The Connection will be lost would you like to proceed?Y/N", "Confirmation Message", JOptionPane.YES_NO_OPTION);
			if (returnValue == JOptionPane.YES_OPTION) 
			{
				Chess_Data.destroy();
				chessBoard.dispose();
				EventQueue.invokeLater(new Runnable() 
				{

					public void run() 
					{
						new Start_Game();
					}
				});
			}
		} 
		else 
		{
			Chess_Data.destroy();
			chessBoard.dispose();
			EventQueue.invokeLater(new Runnable() 
			{

				public void run() 
				{
					new Start_Game();
				}
			});
		}
	}
	
	private void savePlayer ()
	{
		Chess_Data.getChessData().savePlayer();
	}
	
	private void loadFromFile ()
	{
		if ((returnValue = fileChooser.showOpenDialog(chessBoard)) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
        	chessBoard.clearLocalGame();
        	if(!Chess_Data.getChessData().load(file))
            {
        		Chess_Data.getChessData().discardActivePiecesInSavedState();
        		Chess_Data.getChessData().loadActivePiecesFromSavedState();
            }
        	chessBoard.populateChessBoard();
            chessBoard.loadCapturedPieces();
        }
	}
	
	private void exitGame ()
	{
		 System.exit(0);
	}
	
	public void addUISeperator ()
	{
		addSeparator ();
	}

	private enum MenuItemType
	{
		NewGame(1),
		SaveGame(2),
		LoadGame(3),
		LoadGameFromFile(4),
		SaveGameAs(5),
		SavePlayer(6),
		LoadPlayer(7),
		Exit (8);
		
		
		public int currentValue = 0;

		private MenuItemType(int value) {
			currentValue = value;
		}

		public int value() {
			return currentValue;
		}
	}
	
}
