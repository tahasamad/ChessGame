package UIMenus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ChessGameKenai.About;
import ChessGameKenai.Chat;
import ChessGameKenai.ChessBoardView;
import ChessGameKenai.HelpTopics;
import ChessGameKenai.UserManual;

public class HelpMenu extends JMenu
{
	private ChessBoardView chessBoard;
	
	public HelpMenu (String title, ChessBoardView boardRef) {
		super (title);
		chessBoard = boardRef;
	}
	
	
	public void createView ()
	{
	     //ADD JMENUITEMS TO THE HELP MENU
        this.add(new JMenuItem("Chess Rules")).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new HelpTopics(chessBoard);
            }
        });
         this.add(new JMenuItem("User Guide")).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new UserManual(chessBoard);
            }
        });

        this.add(new JMenuItem("About")).addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new About(chessBoard);
            }
        });
	}
	
//	private enum MenuItemType
//	{
//		ChessRules(1),
//		UserGuide(2),
//		About(3);
//		
//		
//		public int currentValue = 0;
//
//		private MenuItemType(int value) {
//			currentValue = value;
//		}
//
//		public int value() {
//			return currentValue;
//		}
//	}

}
