/*
 * Description: Class Board.java
 * Author: Dimtri Pankov
 * Date: 2-Feb-2011
 * Version: 1.0
 */
package ChessGameKenai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.PieceColor;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

/**
 * The Board class is the Board of our Chess Game it consists of a JPanel
 * with the 8 by 8 layout it also has squares and some squares will have pieces on them and some will not
 * This class is an Observer so each time change happens to the data class it is notified by executing its update method
 * The Board gets the non visual pieces array list from the data class when its update method is called and asks the non
 * visual pieces for their color, type and position and them replaces them with visual pieces that each non visual piece
 * represents it is happened each time the move method of the data class is called thus executing this method
 * The Board also knows how to flip itself it only changes the view nothing is change in the code
 * Also depending on who's turn it is the listeners will be added or removed
 * @author Dimitri Pankov
 * @see Observer
 * @version 1.6
 */
public final class Board extends JPanel implements Observer {

    private BoardFlipMode currentBoard = BoardFlipMode.Normal;
    private Square[][] squares;
    private boolean isFirstTime = true;
    private ChessBoardView view;
    private HashMap<String, String> mapPositions = new HashMap<String, String>();
    
    /**
     * The overloaded constructor of the class sets the layout of the board
     * to 8 by 8 adds the squares the board by calling setSquares method
     * It also creates a map that we use to map the images when non visual piece is we use to
     * draw images. Each non visual piece has a unique type which is used to map its image
     * when for drawing the visual piece it represents
     * Then it populates the board with visual piece using map draw them
     * @param data as Chess_Data
     * @param view as ChessBoardView
     */
    public Board(Chess_Data data, ChessBoardView view) {
        this.setLayout(null);
        this.squares = new Square[ChessGameConstants.gridDimension][ChessGameConstants.gridDimension];
        this.view = view;
        this.setupSquares();
        this.setOpaque(false);
        
        imageMap.put("WKing", "ChessPieces/wKing46.gif");
        imageMap.put("BKing", "ChessPieces/bKing46.gif");
        imageMap.put("WQueen", "ChessPieces/wQueen46.gif");
        imageMap.put("BQueen", "ChessPieces/bQueen46.gif");
        imageMap.put("WBishop", "ChessPieces/wBishop46.gif");
        imageMap.put("BBishop", "ChessPieces/bBishop46.gif");
        imageMap.put("WKnight", "ChessPieces/wKnight46.gif");
        imageMap.put("BKnight", "ChessPieces/bKnight46.gif");
        imageMap.put("WRook", "ChessPieces/wRook46.gif");
        imageMap.put("BRook", "ChessPieces/bRook46.gif");
        imageMap.put("WPawn", "ChessPieces/wPawn46.gif");
        imageMap.put("BPawn", "ChessPieces/bPawn46.gif");

        this.populateBoard();
    }

    /**
     * The populateBoard method creates visualPieces that represent non visualPieces
     * in the data class this method is executed ones in the constructor to draw visual pieces
     * the first time it fills the array list of visual piece for later use
     */
    public void populateBoard() {
    	ArrayList<Non_Visual_Piece> activePieces = Chess_Data.getChessData().getActivePieces();
    	int size = activePieces.size();
        for (int i = 0; i < size; i++) {
        	Non_Visual_Piece activePieceModel = activePieces.get(i);
            if (activePieceModel != null) {
            	Piece piece = new Piece(activePieceModel, this);
            	this.addPiece(piece);
                piece.repaint();
            }
            //this.mapPositions(i + 1);
        }
//        for (int i = 0; i < pieces.size(); i++) {
//            squares.get(pieces.get(i).getPosition() - 1).add(pieces.get(i));
//            pieces.get(i).setBounds(5, 5, 55, 55);
//            pieces.get(i).repaint();
//        }
        this.notifyView();
    }

    /**
     */
    public void addPiece(Piece piece) {
    	if(piece != null)
    	{
    		ChessGamePoint piecePosition = piece.getPosition();
    		if(ChessGameUtils.isInGridBounds(piecePosition))
    		{
	    		Piece prevPiece = this.squares[piecePosition.x][piecePosition.y].getPiece();
	    		if(prevPiece != null && !prevPiece.equals(piece))
	    		{
	    			throw new RuntimeException("Trying to add a piece on top of an existing piece.");
	    		}
	    		this.squares[piecePosition.x][piecePosition.y].setPiece(piece);
	    		this.add(piece);
    		}
    		else
    		{
    			throw new RuntimeException("Position Index out of bound.");
    		}
    	}
    }
    
    public void removePiece(Piece piece) {
    	if(piece != null)
    	{
    		ChessGamePoint piecePosition = piece.getPosition();
    		if(ChessGameUtils.isInGridBounds(piecePosition))
    		{
	    		Piece pieceInSquare = this.squares[piecePosition.x][piecePosition.y].getPiece();
	    		if(pieceInSquare == null || !pieceInSquare.equals(piece))
	    		{
	    			throw new RuntimeException("Piece Model Square Out of Synch");
	    		}
	    		this.squares[piecePosition.x][piecePosition.y].setPiece(null);
	    		piece.setPosition(new ChessGamePoint(-1, -1));
	    		this.remove(piece);
    		}
    		else
    		{
    			throw new RuntimeException("Position Index out of bound.");
    		}
    	}
    }

    /**
     * The method removeAllPieces removes all pieces from all squares
     * this method is used when we want to restart the game
     */
    public void removeAllPieces() {
    	for (int x = 0; x < ChessGameConstants.gridDimension; x++) {
    		for (int y = 0; y < ChessGameConstants.gridDimension; y++) {
    			Piece piece = this.squares[x][y].getPiece();
	            if (piece != null) {
	                this.removePiece(piece);
	            }
    		}
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * The method setSquares simply sets the squares
     * This method actually creates the squares and adds them to the board
     * As well as this method also adds the pieces to the squares
     */
    public void setupSquares() {
    	PieceColor columnStartColor = PieceColor.Black;
    	PieceColor columnAlternateColor = PieceColor.White;
        for(int x = 0; x < ChessGameConstants.gridDimension; x++)
        {
        	for(int y = 0; y < ChessGameConstants.gridDimension; y++)
            {
        		Square square;
        		if(y % ChessGameConstants.gridDimension == 0)
        		{
        			 square = new Square(columnStartColor, new ChessGamePoint(x, y));
        		}
        		else
        		{
        			square = new Square(columnAlternateColor, new ChessGamePoint(x, y));
        		}
        		squares[x][y] = square;
        		this.add(square);
            }
        	PieceColor prevColumnStartColor = columnStartColor;
        	columnStartColor = columnAlternateColor;
        	columnAlternateColor = prevColumnStartColor;
        }
    }

    /**
     * The method flipBoard simply flips the board to normal
     * or flip state we use a switch statement to switch between states
     */
    public void flipBoard() {

        //SWITCH STATEMENT FOR CURRENT BOARD VARIABLE IT'S EITHER NORMAL OR FLIPPED
        switch (this.currentBoard) {

            //IF NORMAL_BOARD EXECUTE THE CASE STATEMENT
            case Board.NORMAL_BOARD:
                for (int i = 0; i < squares.size(); i++) {
                    squares.get(i).setBounds((int) (455 - squares.get(i).getBounds().getX()), (int) (455 - squares.get(i).getBounds().getY()), 65, 65);
                    squares.get(i).repaint();
                    this.add(squares.get(i));
                }
                break;

            //IF FLIPPED_BOARD EXECUTE THE CASE STATEMENT
            case Board.FLIPPED_BOARD:
                for (int i = squares.size() - 1; i > -1; i--) {
                    squares.get(i).setBounds((int) (455 - squares.get(i).getBounds().getX()), (int) (455 - squares.get(i).getBounds().getY()), 65, 65);
                    squares.get(i).repaint();
                    this.add(squares.get(i));
                }
                break;
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * The method getCurrentBoard simply returns the current board to the caller
     * @return currentBoard as an integer
     */
    public int getCurrentBoard() {
        return currentBoard;
    }

    /**
     * The method setCurrentBoard sets the current board
     * it is either flipped or normal
     * @param currentBoard as an integer
     */
    public void setCurrentBoard(int currentBoard) {
        this.currentBoard = currentBoard;
    }

    /**
     * The method addListeners adds the listeners to the specified color
     * it loops through the list of visual pieces and adds the listeners to the specified color pieces
     * @param color as a Color
     */
    public void addListeners(Color color) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece p = pieces.get(i);
            if (p.getColor() == color) {
                p.addListener();
            }
        }
    }

    /**
     * The method removeListeners removes the listeners to the specified color
     * it loops through the list of visual pieces and removes the listeners from the specified color pieces
     * @param color as a Color
     */
    public void removeListeners(Color color) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece p = pieces.get(i);
            if (p.getColor() == color) {
                p.removeListener();
            }
        }
    }

    /**
     * The method update is called each time the data class changes
     * for example if the data moves a pieces this method is executed so
     * it can in turn display changes to the view that happened in data
     * It redraws pieces then it distributes the listeners accordingly
     * and if any pieces were captured they are removed from the array list of pieces
     * @param o as an Observable object
     * @param arg as an Object any object
     */
    public void update(Observable o, Object arg) {
        this.redrawPieces();
        if (!data.isWinner() && !data.isGameOnLine()) {
            this.distributeListeners();
        }
        if (data.isGameOnLine()) {
            this.distributeOnLineListeners();
        }
        if (!data.isServer() && isFirstTime) {
            this.removeListeners(Color.BLACK);
            this.removeListeners(Color.WHITE);
            isFirstTime = false;
        }
        if (arg != null) {
            ArrayList list = (ArrayList) arg;
            String turn = "";
            if (squares.get((Integer) list.get(1) - 1).getComponentCount() > 0) {
                Piece p = ((Piece) squares.get((Integer) list.get(1) - 1).getComponent(0));
                if (p.getColor() == Color.WHITE) {
                    turn = "W" + p.getType();
                } else {
                    turn = "B" + p.getType();
                }
            }
            view.getMoves().append(turn + " from: " + mapPositions.get(list.get(0)) + " to " + mapPositions.get(list.get(1)) + "\n");
            view.getMoves().append("--------------------------\n");
            view.getMoves().setCaretPosition(view.getMoves().getDocument().getLength());
        }
        this.removeCapturedPieces();
        this.revalidate();
        this.repaint();
    }

    /**
     * The method isFirstTime simply sets the boolean value to true or false
     * depending on the user's choice
     * @param isFirstTime as a boolean
     */
    public void isFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    /**
     * The method redrawPieces is called each time the data notifies the views
     * because this method is inside the update method the job of this method is to
     * Check the list of active pieces that is stored in the data class loop through the array list
     * and check if the which non visual piece was moved then redraw its view accordingly to do that
     * it needs to know the last position of the non visual piece and compare it to the current position of the visual piece
     * if they are equal that means its the same piece so the visual piece changes its current position to the current position
     * of the non visual piece then redraws its view as needed
     */
    public void redrawPieces() {
        for (int i = 0; i < data.getActivePieces().size(); i++) {
            if (data.getActivePieces().get(i) != null) {
                Non_Visual_Piece p = data.getActivePieces().get(i);
                for (int j = 0; j < pieces.size(); j++) {
                    Piece peice = pieces.get(j);
                    if (p.isQueenFromPawn() && peice.getPosition() == p.getPreviousPosition()) {
                        Piece pi = (Piece) squares.get(p.getPreviousPosition() - 1).getComponent(0);
                        pieces.remove(pi);
                        squares.get(p.getPreviousPosition() - 1).remove(0);
                        Piece piece = new Piece(this, p, "Queen", p.getColor(), p.getPosition(), imageMap.get(p.getType()));
                        pieces.add(piece);
                        getSquares().get(p.getPosition() - 1).add(piece);
                        piece.setBounds(5, 5, 55, 55);
                        peice.revalidate();
                        peice.repaint();
                        peice.removeListener();
                        p.isQueenFromPawn(false);
                    } else {
                        if (p.getColor() == peice.getColor() && p.getPieceType().equals(peice.getType()) && p.getPreviousPosition() == peice.getPosition()) {
                            getSquares().get(p.getPosition() - 1).add((Piece) peice);
                            peice.setPosition(p.getPosition());
                        }
                        if (p.getPreviousPosition() > 0) {
                            Square currentSquare = squares.get(p.getPreviousPosition() - 1);
                            currentSquare.setBackground(currentSquare.getColor());
                            currentSquare.revalidate();
                            currentSquare.repaint();
                        }
                        peice.revalidate();
                        peice.repaint();
                        peice.removeListener();
                    }
                }
            }
        }
    }

    /**
     * The method removeCapturedPieces simply removes the captured pieces from
     * the array list of pieces so it is no longer on the board
     * the piece will be added to the captured pieces panel
     */
//    public void removeCapturedPieces() {
//        if (!data.getCapturedPieces().isEmpty()) {
//            Non_Visual_Piece p = (Non_Visual_Piece) data.getCapturedPieces().get(data.getCapturedPieces().size() - 1);
//            for (int i = 0; i < pieces.size(); i++) {
//                if (pieces.get(i).getPiece().equals(p)) {
//                    pieces.remove(pieces.get(i));
//                }
//            }
//        }
//    }

    /**
     * The method distributeOnLineListeners simply distribute listeners
     * depending on who's turn it is adds or removes listeners
     * This method is only used if the game is played online
     */
//    public void distributeOnLineListeners() {
//        if (data.isServer()) {
//            if (data.isWhiteTurn()) {
//                this.addListeners(Color.WHITE);
//                this.removeListeners(Color.BLACK);
//            } else {
//                this.removeListeners(Color.WHITE);
//                this.removeListeners(Color.BLACK);
//            }
//        } else {
//            if (!data.isWhiteTurn()) {
//                this.addListeners(Color.BLACK);
//                this.removeListeners(Color.WHITE);
//            } else {
//                this.removeListeners(Color.BLACK);
//                this.removeListeners(Color.WHITE);
//            }
//        }
//    }

    /**
     * This method only notifies the view to update the display
     * it calls data notify method which has setChanged and notifyObservers methods only
     * it is used to update changes
     */
    public void notifyView() {
        Chess_Data.getChessData().notifyView();
    }

    /**
     * The startLocalTimer method starts the timer if the game
     * is played locally no connection
     */
    public void startLocalTimer() {
        if (ChessBoardView.getConnectionInstance() == null) {
            view.startTimer();
        }
    }

    /**
     * The mapPositions method simply maps the positions to of the squares to
     * standard chess game positions like e4, f5 etc... are all mapped to integers from 1 to 64
     * @param position as an integer
     */
    public void mapPositions(int x, int y) {
    	int asciiForSmallA = 97;
    	String string = Character.toString((char) (asciiForSmallA + x));
    	string += y;
    	this.mapPositions.put(this.getMapPositionKey(x, y), string);
    }
    
    private String getMapPositionKey(int x, int y)
    {
    	return "" + x + ":" + y;
    }
}
