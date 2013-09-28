/*
 * Description: Class VisualPiece.java
 * Author: Dimtri Pankov
 * Date: 2-Feb-2011
 * Version: 1.0
 */
package GameElements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

import ChessGameKenai.Board;
import ChessGameKenai.ChessGameConstants;
import Utils.ChessGamePoint;
import Utils.ChessGameRect;
import Utils.ChessGameUtils;

/**
 * The VisualPiece class is the piece that is visual to the user
 * it is only displayed in our view class which is a ChessBoardView
 * The Visual Piece knows its color, position, imagePath type and etc....
 * This calls has two constructors so we can create visual pieces  into two different ways.
 * Each Visual Piece has reference to the board so it can call the board methods and each
 * Visual Piece also has the reference of the non visual piece it represents for easy communication
 * We have decided to use visual and non visual pieces so when we serialize the piece only non visual piece
 * will be serialized it foolproof method to save objects or transport objects over the network, because graphical
 * components are not saveable the will be out of date and no use to us after loading them back so the decision was made
 * to have both visual and non visual pieces for the chess game
 * @author Dimitri Pankov
 * @see JPanel
 * @version 1.5
 */
public class Piece extends JPanel {

    private String imagePath;
	private Non_Visual_Piece pieceModel;
	private Board board;

    /**
     * Overloaded constructor of our class receives the path to its image
     * @param imagePath as a String
     */
    public Piece(Non_Visual_Piece pieceModel, Board board) {
    	this.pieceModel = pieceModel;
    	this.board = board;
        this.imagePath = ChessGameUtils.getPieceImageFilePathForTypeAndColor(this.pieceModel.getType(), this.pieceModel.getColor());
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(ChessGameConstants.pieceDimension, ChessGameConstants.pieceDimension));
        ChessGameRect bounds = ChessGameConstants.pieceBounds.clone();
        this.setBounds(bounds.point.x, bounds.point.y, bounds.size.width, bounds.size.height);
    }

    /**
     * The method getPiece returns the non visual piece that represents this object
     * @return piece as Non_Visual_Piece
     */
    public Non_Visual_Piece getPieceModel() {
        return pieceModel;
    }

    /**
     * The method getColor returns color of the object
     * @return color
     */
    public PieceColor getPieceColor() {
        return this.pieceModel.getColor();
    }

    /**
     * The method setColor sets Color
     * @param color sets Color of the object
     */
    public void setColor(PieceColor color) {
        this.pieceModel.setColor(color);
    }

    /**
     * The method getPosition returns Position
     * @return position of the object
     */
    public ChessGamePoint getPosition() {
        return this.pieceModel.getPosition();
    }

    /**
     * The method setPosition sets object's position
     * @param position position of the object
     */
    public void setPosition(ChessGamePoint position) {
        this.pieceModel.setPosition(position);
    }
    
    public void setPositionOnBoard(ChessGamePoint position) throws Error {
    	if(this.board == null)
    	{
    		throw new Error("Board is null.");
    	}
    	else
    	{
    		this.pieceModel.setPosition(position);
    		this.board.addPiece(this);
    	}
    }

    /**
     * The method getType returns type as a String
     * @return the type of object
     */
    public PieceType getType() {
        return this.pieceModel.getType();
    }

    /**
     * The method getTextColor return a color as a String
     * @return color as a String
     */
    public String getTextColor() {
        if (this.getPieceColor() == PieceColor.White) {
            return "White";
        } else {
            return "Black";
        }
    }

    /**
     * The method toString() is overridden by our class
     * which has implementation in the super class
     * @return s as String text representation of the object
     */
    @Override
    public String toString() {
        String s = "";
        s += this.getType() + ", " + this.getTextColor() + ", " + this.getPosition();
        return s;
    }

    /**
     * The method paintComponent of the Pawn_View class
     * @param g Graphics object used to paint this object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();
        URL url = this.getClass().getResource(imagePath);
        Toolkit toolkit = this.getToolkit();
        Image image = toolkit.getImage(url);
        g.drawImage(image, 0, 0, w, h, this);
    }
}
