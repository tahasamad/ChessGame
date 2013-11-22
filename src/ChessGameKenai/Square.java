/*
 * Description: Class Square.java
 * Author: Dimtri Pankov
 * Date: 20-Dec-2010
 * Version: 1.0
 */
package ChessGameKenai;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import GameElements.ElementColor;
import GameElements.Piece;
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
    private MouseEventHandler handler;
    private boolean handlerAdded;

    /**
     * OverLoaded Constructor for cr2eating square objects
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
        this.setBounds(position.getX() * ChessGameConstants.squareSize, position.getY() * ChessGameConstants.squareSize, ChessGameConstants.squareSize, ChessGameConstants.squareSize);        
        //ADD MOUSELISTENER TO THIS OBJECT AND PASS A REFERENCE TO THE OBJECT THAT HANDLES THE MOUSE EVENTS
        this.handler = new MouseEventHandler();
        this.handlerAdded = false;
        this.addHandler();
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

    //Note update view yourself.
	public void setPieceWithoutUpdatingView(Piece piece) {
		this.squareModel.setPiece(piece);
		this.squareModel.setViewDirty(false);
	}

	public ElementColor getBaseColor() {
		return this.squareModel.getBaseColor();
	}
	
	public void updateView() {
		if(this.squareModel.getViewDirty())
		{
			this.squareModel.setViewDirty(false);
			try
			{
				Component comp = this.getComponent(ChessGameConstants.pieceIndex);
				if(comp != null)
				{
					this.remove(comp);
				}
			}
			catch(Exception ex)
			{
				//do nothing
			}
			Piece piece = this.squareModel.getPiece();
			if(piece != null)
			{
				this.add(piece);
				piece.updateView();
			}
			this.revalidate();
			this.repaint();
		}
	}
		
	public void addHandler()
	{
		if(!this.handlerAdded)
		{
			this.addMouseListener(this.handler);
			this.handlerAdded = true;
		}
	}
	
	public void removeHandler()
	{
		this.removeMouseListener(this.handler);
		this.handlerAdded = false;
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
    protected class MouseEventHandler extends MouseAdapter {

        /**
         * Overloaded Constructor of the inner class
         * @param data the address of the model for later communication purposes
         */
        protected MouseEventHandler() {

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
            	ElementColor turnColor = ElementColor.Black;
            	if(whiteTurn)
            	{
            		turnColor = ElementColor.White;
            	}
            	ElementColor pieceColor = piece.getPieceColor();
            	if(pieceColor == turnColor)
            	{
            		if(selectedSquare != null)
            		{
            			selectedSquare.setBackground(ChessGameUtils.getColorFromElementColor(selectedSquare.getBaseColor()));
            		}
            		Square.this.setBackground(ChessGameConstants.selectedSquareColor);
            		Chess_Data.getChessData().setSelectedSquare(Square.this);
            		selectedSquare = Square.this;
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
        	boolean hasMoved = selectedPiece.tryToMove(selectedSquare.getPosition().clone(), Square.this.getPosition().clone());
        	if(hasMoved)
        	{
        		selectedSquare.setBackground(ChessGameUtils.getColorFromElementColor(selectedSquare.getBaseColor()));
        		Chess_Data.getChessData().setSelectedSquare(null);
        	}
        }
    }
}
