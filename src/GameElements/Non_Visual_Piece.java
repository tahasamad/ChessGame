/*
 * Description: Class Non_Visual_Piece.java
 * Author: Dimtri Pankov
 * Date: 27-Jan-2011
 * Version: 1.0
 */
package GameElements;

import java.io.Serializable;

import ChessGameKenai.ChessGameConstants.PieceColor;
import ChessGameKenai.ChessGameConstants.PieceType;
import Utils.ChessGamePoint;

/**
 * The Non_Visual_Piece class is the abstract piece that is not visual to the user but
 * it's the piece that our data class(Chess_Data) uses to move the piece then when the move is made
 * the view will adjust the position of the visual piece that represents the non visual piece that was moved
 * it is done by asking the piece what is type, color or position. The non visual piece has the reference to the data class
 * because all the data to move, save load is stored the data class is the data holder class of our chess game
 * The Non_Visual_Piece is also a serializable object that means that it can be serialized to be transported
 * over the network or saved to a file
 * @author Dimitri Pankov
 * @see Serializable
 * @version 1.5
 */
public class Non_Visual_Piece implements Serializable {

	private PieceColor color;
	private PieceType type;
    private ChessGamePoint position;
    private boolean isCaptured;
    private boolean isMoved;
    private boolean isQueenFromPawn;

    /**
     * Overloaded constructor of our class it receives all needed references
     * for its creation during its lifespan this object will know all the information
     * specified in the constructor for later use when we will ask the object for its contents
     * like color, type and etc.....
     * @param data as Chess_Data
     * @param type as a String
     * @param position as an integer
     * @param color as Color
     */
    public Non_Visual_Piece(PieceType type, ChessGamePoint position, PieceColor color) {
        this.type = type;
        this.position = position;
        this.color = color;
    }

    /**
     * The method isMoved simply tells the caller if the current pieces was moved
     * this method is only used by the rook and king for castling purposes for example]
     * if the rook or king was moved castling are not permitted
     * @return isMoved as a boolean
     */
    public boolean isMoved() {
        return isMoved;
    }

    /**
     * The isQueenFromPawn method sets the boolean value to true or false
     * to tell the game that this queen was created from pawn
     * @param isQueenFromPawn as a boolean
     */
    public void isQueenFromPawn(boolean isQueenFromPawn) {
        this.isQueenFromPawn = isQueenFromPawn;
    }

    /**
     * The method isQueenFromPawn tells the user if the current Queen was created from pawn or not
     * for example when pawn reaches the last square it becomes a queen
     * @return isQueenFromPawn as a boolean
     */
    public boolean isQueenFromPawn() {
        return isQueenFromPawn;
    }

    /**
     * The method isMoved simply sets the piece that was moved to true
     * default is false to tell the game that this piece was moved
     * @param isMoved as a boolean
     */
    public void isMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    /**
     * The method isCaptured tells the game that the piece was captured or not
     * @return isCaptured as a boolean
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * The method isCaptured simply sets the boolean value of the
     * captured piece to true to tell the game that a certain piece as captured
     * @param isCaptured as a boolean
     */
    public void isCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    /**
     * The method getColor simply returns the current color of the
     * current piece to the caller
     * @return color as a Color
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * The method setColor simply sets the color of the piece to
     * what the user chooses
     * @param color as a Color
     */
    public void setColor(PieceColor color) {
        this.color = color;
    }

    /**
     * The method setPosition simply sets position of the piece
     * @param position as an integer
     */
    public void setPosition(ChessGamePoint point) {
        this.position = point;
    }

    /**
     * The method getPosition simply returns the position
     * of the piece object
     * @return position as an integer
     */
    public ChessGamePoint getPosition() {
        return position;
    }

    /**
     * The method setType simply sets the type of the piece
     * @param type as a String
     */
    public void setType(PieceType type) {
        this.type = type;
    }

    /**
     * The method getType simply returns the type of the piece to the caller
     * @return type as a String
     */
    public PieceType getType() {
        return type;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Type: " + this.getType() + ", Color:" + this.getColor() + ", PositionX:" + this.getPosition().x + ", PositionY:" + this.getPosition().y;
        return s;
    }
}
