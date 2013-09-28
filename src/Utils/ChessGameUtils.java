package Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;

import ChessGameKenai.ChessGameConstants;
import ChessGameKenai.Chess_Data;
import GameElements.PieceColor;
import GameElements.PieceType;

public class ChessGameUtils { // Applying Utility Pattern
	
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
	
	public static boolean isInGridBounds(ChessGamePoint point)
	{
		return ChessGameUtils.isInGridBounds(point.x, point.y);
	}
	
	public static boolean isInGridBounds(int x, int y)
	{
		return ((x >= 0 && x < ChessGameConstants.gridDimension) && (y >= 0 && y < ChessGameConstants.gridDimension)); 
	}
	
	public static Color getColorFromElementColor(PieceColor pieceColor)
	{
		if(pieceColor == PieceColor.White)
		{
			return Color.WHITE;
		}
		else
		{
			return Color.BLACK;
		}
	}
	
	public static String getPieceImageFilePathForTypeAndColor(PieceType type, PieceColor color)
	{
		String path = "ChessPieces/";
		if(color == PieceColor.White)
		{
			path += "w";
		}
		else
		{
			path += "b";
		}
		switch(type)
		{
			case Bishop:
				path += "Bishop46.gif";
				break;
			case King:
				path += "King46.gif";
				break;
			case Knight:
				path += "Knight46.gif";
				break;
			case Pawn:
				path += "Pawn46.gif";
				break;
			case Queen:
				path += "Queen46.gif";
				break;
			case Rook:
				break;
		}
		return path;
		
	}
//	public static Player getFirstPlayer ()
//	{
//		
//	}
}