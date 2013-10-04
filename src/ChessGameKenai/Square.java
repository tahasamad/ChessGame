/*
 * Description: Class Square.java
 * Author: Dimtri Pankov
 * Date: 20-Dec-2010
 * Version: 1.0
 */
package ChessGameKenai;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import GameElements.Piece;
import GameElements.PieceColor;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

/**
 * Public Square class which is a template for making Square objects when called upon
 * Some Square objects will have Piece on them some will be empty
 * Square objects will carry either white or black color depending on their position and they will know their position as well
 * in the ChessBoardView class they also listen for click events to register their position at the source and at destination
 * @author Dimitri Pankov
 * @see JPanel Class
 * @see ChessBoardView Class
 * @version 1.0
 */
public class Square extends JPanel {

    private Color currentColor;
    private Color previousColor;
    private SquareModel squareModel;

    /**
     * OverLoaded Constructor for creating square objects
     * @param color Square object color
     * @param position Square object position on the ChessBoardView's MainPanel
     * @see ChessBoardView Class
     */
    public Square(SquareModel squareModel) {
    	if(squareModel == null)
    	{
    		throw new Error("Can not accept squareModel = null");
    	}
        this.squareModel = squareModel;
        this.setBackground(ChessGameUtils.getColorFromElementColor(this.squareModel.getBaseColor()));
        this.setLayout(null);
        ChessGamePoint position = this.getPosition();
        this.setBounds(position.x * ChessGameConstants.squareSize, position.y * ChessGameConstants.squareSize, ChessGameConstants.squareSize, ChessGameConstants.squareSize);        
        //ADD MOUSELISTENER TO THIS OBJECT AND PASS A REFERENCE TO THE OBJECT THAT HANDLES THE MOUSE EVENTS
        this.addMouseListener(new SendData());
    }

    /**
     * Empty Constructor of the Square Class
     */
    public Square() {
    }

    /**
     * The method getColor returns the color of particular object
     * @return color of the object
     */
    public Color getColor() {
        return currentColor;
    }

    /**
     * The method setColor sets object color to whatever is its argument
     * @param color the color that the object will have
     */
    public void setColor(Color color) {
        this.currentColor = color;
    }

    /**
     * The method getPosition returns the position of particular object
     * @return position of the object
     */
    public ChessGamePoint getPosition() {
        return this.squareModel.getPosition();
    }

    /**
     * The method setPreviuosColor simply sets the previous color
     * of the square object
     * @param previousColor as a Color
     */
    public void setPreviousColor(Color previousColor) {
        this.previousColor = previousColor;
    }

    /**
     * The method getPreviuosColor returns the previous color to the caller
     * @return previousColor as a Color
     */
    public Color getPreviousColor() {
        return previousColor;
    }

    public Piece getPiece() {
		return this.squareModel.getPiece();
	}

	public void setPiece(Piece piece) {
		this.squareModel.setPiece(piece);
	}

	public PieceColor getBaseColor() {
		return this.squareModel.getBaseColor();
	}

	/**
     * The SendData Class is a nested inner class of our object
     * It does as its name suggests sends data to the model after receiving the data and
     * the address of the model from its outer class it completes the job by sending the request to the model
     * @author Dimitri Pankov
     * @see MouseAdapter Class
     * @see Square Class
     * @version 1.0
     */
    protected class SendData extends MouseAdapter {

        /**
         * Overloaded Constructor of the inner class
         * @param data the address of the model for later communication purposes
         */
        protected SendData() {

        }

        /**
         * The method mousePressed is executed when the mouse is pressed
         * @param e MouseEvent object that is generated when mouse is pressed
         */
        @Override
        public void mousePressed(MouseEvent e) {
            Piece piece = Square.this.getPiece();
            Square selectedSquare = Chess_Data.getChessData().getSelectedSquare();
            if(piece != null)
            {
            	boolean whiteTurn = Chess_Data.getChessData().isWhiteTurn();
            	PieceColor turnColor = PieceColor.Black;
            	if(whiteTurn)
            	{
            		turnColor = PieceColor.White;
            	}
            	PieceColor pieceColor = piece.getPieceColor();
            	if(pieceColor == turnColor)
            	{
            		if(selectedSquare != null)
            		{
            			selectedSquare.setBackground(ChessGameUtils.getColorFromElementColor(Square.this.getBaseColor()));
            		}
            		Square.this.setBackground(Color.BLUE);
            		Chess_Data.getChessData().setSelectedSquare(Square.this);
            	}
            	else if(selectedSquare != null)
            	{
                	this.tryToCompleteMove(selectedSquare);
            	}
            }
            else if(selectedSquare != null)//if prev selected)
            {
            	this.tryToCompleteMove(selectedSquare);
            }            
        }
        
        private void tryToCompleteMove(Square selectedSquare)
        {
        	Piece selectedPiece = selectedSquare.getPiece();
        	boolean hasMoved = selectedPiece.tryToMove(Square.this.getPosition().clone());
        	if(hasMoved)
        	{
        		Chess_Data.getChessData().changeTurn(whiteTurn);
        	}
        }
    }
}
