package ChessGameKenai;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ChessGameUtil { // Applying Utility Pattern

	
	
	public static String getCurrentPlayerName ()
	{
		String playerName = "";
		if (Chess_Data.getChessData().isServer()) 
		{
			playerName = Chess_Data.getChessData().getPlayers().get(0).getName();
		} 
		else 
		{
			playerName = Chess_Data.getChessData().getPlayers().get(1).getName();
		}
		return playerName;
	}
	
	public static JButton getButtonWithText (String buttonText)
	{
		JButton button = new JButton ( buttonText );
		return button;
	}
	
	public static JPanel getPaintedJPanelWithBorderLayOut (final int width, final int height, final String backgroundImagePath)
	{
		JPanel panel = new JPanel ( new BorderLayout() ) 
		{
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/**
             * The method painComponent of allChatPanel
             * is used here to paint the JPanel as we want
             * @param g Graphics object used to paint this object
             */
            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                URL url = this.getClass().getResource( backgroundImagePath );
                
                Toolkit toolkit = this.getToolkit();
                Image image = toolkit.getImage(url);
                graphics.drawImage(image, 0, 0, width, height, this);
            }
            
        };
        
        return panel;
	}
	
//	public static Player getFirstPlayer ()
//	{
//		
//	}
}
