/*
 * Description: Class Board.java
 * Author: Dimtri Pankov
 * Date: 2-Feb-2011
 * Version: 1.0
 */
package ChessGameKenai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import GameElements.ElementColor;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.Behaviors.BehaviorResult;
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
    public Board(ChessBoardView view) {
        this.setLayout(null);
        int size = Chess_Data.getChessData().getDimension();
        this.squares = new Square[size][size];
        this.view = view;
        this.setupSquares();
        this.setOpaque(false);
        this.populateBoard();
        Chess_Data.getChessData().discardActivePiecesInSavedState();
    }

    /**
     * The populateBoard method creates visualPieces that represent non visualPieces
     * in the data class this method is executed ones in the constructor to draw visual pieces
     * the first time it fills the array list of visual piece for later use
     */
    public void populateBoard() {
    	int size = Chess_Data.getChessData().getDimension();
        for (int x = 0; x < size; x++) {
        	for (int y = 0; y < size; y++) {
        		Non_Visual_Piece activePieceModel = Chess_Data.getChessData().getPieceModelFromSaveState(x, y);
        		if(activePieceModel != null)
        		{
	            	Piece piece = new Piece(activePieceModel);
	            	this.addPiece(piece, new ChessGamePoint(x, y));
        		}
        	}
        	
        }
//      this.notifyView();//TODO
    }

    public Square[][] getSquares() {
		return squares;
	}
    
    /**
     */
    private void addPiece(Piece piece, ChessGamePoint piecePosition) {
    	if(piece != null)
    	{
    		if(ChessGameUtils.isInGridBounds(piecePosition))
    		{
	    		Piece prevPiece = this.squares[piecePosition.x][piecePosition.y].getPiece();
	    		if(prevPiece != null && !prevPiece.equals(piece))
	    		{
	    			throw new RuntimeException("Trying to add a piece on top of an existing piece.");
	    		}
	    		this.squares[piecePosition.x][piecePosition.y].setPieceWithoutUpdatingView(piece);
	    		this.squares[piecePosition.x][piecePosition.y].add(piece, ChessGameConstants.pieceIndex);
	    		this.squares[piecePosition.x][piecePosition.y].revalidate();
	    		this.squares[piecePosition.x][piecePosition.y].repaint();
    		}
    		else
    		{
    			throw new RuntimeException("Position Index out of bound.");
    		}
    	}
    }
    
    private void removePiece(Piece piece, ChessGamePoint piecePosition) {
    	if(piece != null)
    	{
    		if(ChessGameUtils.isInGridBounds(piecePosition))
    		{
	    		Piece pieceInSquare = this.squares[piecePosition.x][piecePosition.y].getPiece();
	    		if(pieceInSquare == null || !pieceInSquare.equals(piece))
	    		{
	    			throw new RuntimeException("Piece Model Square Out of Synch");
	    		}
	    		this.squares[piecePosition.x][piecePosition.y].setPieceWithoutUpdatingView(null);
	    		this.squares[piecePosition.x][piecePosition.y].remove(piece);
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
    	int size = Chess_Data.getChessData().getDimension();
    	for (int x = 0; x < size; x++) {
    		for (int y = 0; y < size; y++) {
    			Piece piece = this.squares[x][y].getPiece();
	            if (piece != null) {
	                this.removePiece(piece, new ChessGamePoint(x, y));
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
    	int size = Chess_Data.getChessData().getDimension();
    	for(int x = 0; x < size; x++)
        {
        	for(int y = 0; y < size; y++)
            {
        		Square 	 square = new Square(Chess_Data.getChessData().getSquareModel(x, y));
        		squares[x][y] = square;
                this.mapPositions(x, y);
        		this.add(square);
            }
        }
    }

    /**
     * The method flipBoard simply flips the board to normal
     * or flip state we use a switch statement to switch between states
     */
    public void flipBoard() {

    	int size = Chess_Data.getChessData().getDimension();
        //SWITCH STATEMENT FOR CURRENT BOARD VARIABLE IT'S EITHER NORMAL OR FLIPPED
        switch (this.currentBoard) {

            //IF NORMAL_BOARD EXECUTE THE CASE STATEMENT
        
            case Normal:
                for(int x = 0; x < size; x++)
                {
                	for(int y = 0; y < size; y++)
                    {
                		squares[x][y].setBounds((int)(455 - squares[x][y].getBounds().getX()),  (int)(455 - squares[x][y].getBounds().getY()), 65, 65);
                		squares[x][y].repaint();
                		this.add(squares[x][y]);
                    }
                }
                break;

            //IF FLIPPED_BOARD EXECUTE THE CASE STATEMENT
            case Flipped:
                for(int x = size - 1; x >= 0; x--)
                {
                	for(int y = size - 1; y >= 0; y--)
                    {
                		squares[x][y].setBounds((int)(455 - squares[x][y].getBounds().getX()),  (int)(455 - squares[x][y].getBounds().getY()), 65, 65);
                		squares[x][y].repaint();
                		this.add(squares[x][y]);
                    }
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
    public BoardFlipMode getCurrentBoard() {
        return currentBoard;
    }

    /**
     * The method setCurrentBoard sets the current board
     * it is either flipped or normal
     * @param currentBoard as an integer
     */
    public void setCurrentBoard(BoardFlipMode currentBoard) {
    		this.currentBoard = currentBoard;
    }

    public void addHandlers() {
    	System.out.println("Adding");
    	int size = Chess_Data.getChessData().getDimension();
        for (int x = 0; x < size; x++) {
        	for (int y = 0; y < size; y++) {
        		this.squares[x][y].addHandler();
        	}
        }
        System.out.println("---------------------");
    }

    public void removeHandlers() {
    	System.out.println("Removing");
    	int size = Chess_Data.getChessData().getDimension();
        for (int x = 0; x < size; x++) {
        	for (int y = 0; y < size; y++) {
        		this.squares[x][y].removeHandler();
        	}
        }
        System.out.println("++++++++++++++++++++++++");
    }

    /**
     * The method update is called each time the data class changes
     * for example if the data moves a pieces this method is executed so
     * it can in turn display changes to the view that happened in data
     * It redraws pieces then it distributes the listeners accordingly
     * and if any pieces were captured they are removed from the array list of pieces
     * @param observable as an Observable object
     * @param data as an Object any object
     */
    public void update(Observable observable, Object data) {
        this.redrawPieces();
        if (data != null && data instanceof BehaviorResult) {
            @SuppressWarnings("unchecked")
            BehaviorResult result = (BehaviorResult) data;
			ArrayList<SquareModel> list = result.getSquareModels();
            String turn = "";
            SquareModel squareModel2 = list.get(1);
            if(squareModel2 != null)
            {
                Non_Visual_Piece pieceModel = squareModel2.getPiece().getPieceModel();
                if (pieceModel.getColor() == ElementColor.White) {
                    turn = "W" + pieceModel.getType();
                } else {
                    turn = "B" + pieceModel.getType();
                }
            }
            SquareModel squareModel1 = list.get(0);
            ChessGamePoint srcPosition = squareModel1.getPosition();
            ChessGamePoint dstPosition = squareModel2.getPosition();
            view.getMoves().append(turn + " from: " + this.mapPositions.get(this.getMapPositionKey(srcPosition.x, srcPosition.y)) + " to " + this.mapPositions.get(this.getMapPositionKey(dstPosition.x, dstPosition.y)) + "\n");
            view.getMoves().append("--------------------------\n");
            view.getMoves().setCaretPosition(view.getMoves().getDocument().getLength());
        }
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
    	int size = Chess_Data.getChessData().getDimension();
        for (int x = 0; x < size; x++) {
        	for (int y = 0; y < size; y++) {
        		this.squares[x][y].updateView();
//        		Piece piece = 
//        		Piece piece = this.squares[x][y].updateView();
//        		if(piece != null)
//        		{
//                    piece.revalidate();
//                    piece.repaint();
//                    //piece.removeListener();
//        		}
        	}  
        }
    }

    /**
     * The method distributeOnLineListeners simply distribute listeners
     * depending on who's turn it is adds or removes listeners
     * This method is only used if the game is played online
     */
    public void distributeOnLineListeners() {
    	Chess_Data data = Chess_Data.getChessData();
        if (data.isServer()) {
            if (data.isWhiteTurn()) {
                this.addHandlers();
            } else {
                this.removeHandlers();
            }
        } else {
            if (!data.isWhiteTurn()) {
                this.addHandlers();
            } else {
                this.removeHandlers();
            }
        }
    }

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
    	String string = "" + Character.toString((char) (asciiForSmallA + x));
    	string += (Chess_Data.getChessData().getDimension() - y);
    	this.mapPositions.put(this.getMapPositionKey(x, y), string);
    }
    
    private String getMapPositionKey(int x, int y)
    {
    	return "" + x + ":" + y;
    }
    
    public void resetSelectedSquare() {
    	Chess_Data data = Chess_Data.getChessData();
    	Square selectedSquare = data.getSelectedSquare();
    	if(selectedSquare != null)
    	{
    		selectedSquare.setBackground(ChessGameUtils.getColorFromElementColor(selectedSquare.getBaseColor()));
    		data.setSelectedSquare(null);
    	}
    }

}
