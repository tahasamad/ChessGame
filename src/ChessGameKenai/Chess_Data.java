/*
 * Description: Class Chess_Data.java
 * Author: Dimtri Pankov
 * Date: 16-Dec-2010
 * Version: 1.0
 */
package ChessGameKenai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;

import GameElements.ElementColor;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.PieceType;
import Utils.ChessGamePoint;

/**
 * The Chess_Data Class are the model for our chess game
 * This model is an Observable object which when changed notifies the
 * observers in order to update observer views
 * @author Dimitri Pankov
 * @see Observable Class
 * @version 1.1
 */
public final class Chess_Data extends Observable {

    private ArrayList<Non_Visual_Piece> capturedPieces = new ArrayList<Non_Visual_Piece>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> loadedPlayer = new ArrayList<Player>();
    private Non_Visual_Piece[][] activePiecesInSavedState;
    private boolean isWhiteTurn = true;
    private boolean isServer = false;
    private boolean isGameOnLine = false, isClientConnected;
    private SquareModel[][] squareModels;
    private Square selectedSquare;
    private ChessGamePoint whiteEnPessant;
    private ChessGamePoint blackEnPessant;
    
	private static Chess_Data chessData;
	
	public static Chess_Data getChessData()
	{
		if (chessData == null)
		{
			chessData = new Chess_Data ();
		}
		return chessData;
	}

    /**
     * Empty Constructor of the Chess_Data Class
     * When the object of Chess_Data is created
     * it will contain the information that is specified inside
     * the constructor the constructor creates the non visual pieces and fills the array with them
     */
    private Chess_Data() {
    	this.createSquareModels();
        this.loadActivePiecesFromSavedState();
    }

    /**
     * The method createNonVisualPieces simply creates non visual pieces
     * the pieces are stored in the Array of activePieces
     */
    public boolean posHasPiece(ChessGamePoint point)
    {
    	boolean hasPiece = false;
		SquareModel selectedSquare = this.getSquareModel(point.getX(), point.getY());
		Piece piece = selectedSquare.getPiece();
		if(piece != null)
		{
			hasPiece = true;
		}
		return hasPiece;
    }
    
    public void createSquareModels()
    {
    	int size = this.getDimension();
    	if (this.squareModels == null) {
    		this.squareModels = new SquareModel[size][size];
        }
        
        ElementColor columnStartColor = ElementColor.White;
    	ElementColor columnAlternateColor = ElementColor.Black;
        
    	for(int x = 0; x < size; x++)
        {
        	for(int y = 0; y < size; y++)
            {
        		this.setSquareModel(x, y, new SquareModel(new ChessGamePoint(x, y), columnStartColor, null));
        		//Row Color Switch
        		ElementColor prevColumnStartColor = columnStartColor;
            	columnStartColor = columnAlternateColor;
            	columnAlternateColor = prevColumnStartColor;
            }
        	//Column Color Switch
        	ElementColor prevColumnStartColor = columnStartColor;
        	columnStartColor = columnAlternateColor;
        	columnAlternateColor = prevColumnStartColor;
        }    	
    }
    
    public int getDimension()
    {
    	return ChessGameConstants.gridDimension;
    }
    
    public SquareModel getSquareModel(int x, int y)
    {
    	if(this.squareModels != null)
    	{
    		int size = this.getDimension();
    		if(x >= 0 && x < size && y >= 0 && y < size)
    		{
    			return this.squareModels[x][y];
    		}
    	}
    	return null;
    }
    
    public SquareModel getSquareModel(ChessGamePoint position)
    {
    	return this.getSquareModel(position.getX(), position.getY());
    }
        
    private void setSquareModel(int x, int y, SquareModel model)
    {
    	if(this.squareModels != null)
    	{
    		int size = this.getDimension();
    		if(x >= 0 && x < size && y >= 0 && y < size)
    		{
    			this.squareModels[x][y] = model;
    		}
    	}
    	else
    	{
    		throw new Error("Out of Bounds");
    	}
    }
    
    public void loadActivePiecesFromSavedState() {
    	int size = this.getDimension();
    	if(this.activePiecesInSavedState == null)
    	{
            this.activePiecesInSavedState = new Non_Visual_Piece[size][size];
    	}
    	if(this.activePiecesInSavedState != null)
    	{
	        //FILL NON VISUAL PIECES INTO THE ARRAY OF THE DATA CLASS
	        for (int x = 0; x < size; x++) {
	        	for (int y = 0; y < 2; y++) {
	        		addPieceInActivePieces (x, y, ElementColor.Black);
	        	}
	        	for (int y = size - 2; y < size; y++) {
		            addPieceInActivePieces (x, y, ElementColor.White);
	        	}
	        }
    	}
    }
    
    public Non_Visual_Piece getPieceModelFromSaveState(int x, int y)
    {
    	if(this.activePiecesInSavedState != null)
    	{
    		int size = this.getDimension();
    		if(x >= 0 && x < size && y >= 0 && y < size)
    		{
    			return this.activePiecesInSavedState[x][y];
    		}
    	}
    	return null;
    }
    
    public Non_Visual_Piece getPieceModelFromSavedState(ChessGamePoint position)
    {
    	return this.getPieceModelFromSaveState(position.getX(), position.getY());
    }
    
    public void discardActivePiecesInSavedState()
    {
    	this.activePiecesInSavedState = null;
    }
        
    private void setPieceModelInSavedState(int x, int y, Non_Visual_Piece pieceModel)
    {
    	if(this.activePiecesInSavedState != null)
    	{
    		int size = this.getDimension();
    		if(x >= 0 && x < size && y >= 0 && y < size)
    		{
    			this.activePiecesInSavedState[x][y] = pieceModel;
    		}
    		else
        	{
        		throw new Error("Out of Bounds");
        	}
    	}
    	else
    	{
    		throw new Error("Array is null");
    	}
    }
    
    private void addPieceInActivePieces (int xPos, int yPos, ElementColor color)
    {
    	if(yPos == 1 || yPos == this.getDimension() - 2)
        {
    		Non_Visual_Piece  nonVisualPiece = new Non_Visual_Piece(PieceType.Pawn, color);
    		this.setPieceModelInSavedState(xPos, yPos, nonVisualPiece);
        }
    	else if (xPos == 0 || xPos == 7) 
    	{
    		Non_Visual_Piece nonVisualPiece = new Non_Visual_Piece(PieceType.Rook, color);
    		this.setPieceModelInSavedState(xPos, yPos, nonVisualPiece);
        }
        else if (xPos == 1 || xPos == 6) {
        	Non_Visual_Piece nonVisualPiece = new Non_Visual_Piece(PieceType.Knight, color);
        	this.setPieceModelInSavedState(xPos, yPos, nonVisualPiece);
        }
        else if (xPos == 2 || xPos == 5) {
        	Non_Visual_Piece nonVisualPiece = new Non_Visual_Piece(PieceType.Bishop, color);
        	this.setPieceModelInSavedState(xPos, yPos, nonVisualPiece);
        }
        else if (xPos == 3) {
        	Non_Visual_Piece nonVisualPiece = new Non_Visual_Piece(PieceType.Queen, color);
        	this.setPieceModelInSavedState(xPos, yPos, nonVisualPiece);
        }
        else if (xPos == 4) {
        	Non_Visual_Piece nonVisualPiece = new Non_Visual_Piece(PieceType.King, color);
        	this.setPieceModelInSavedState(xPos, yPos, nonVisualPiece);
        }
    }

    /**
     * The method isGameOnLine returns true or false
     * depending if the game is online true else to false
     * @return as a boolean
     */
    public boolean isGameOnLine() {
        return isGameOnLine;
    }

    /**
     * The method isGameOnLine simply sets the boolean value to true or false
     * depending if the game is online to true else to false
     * @param isGameOnLine as a boolean
     */
    public void isGameOnLine(boolean isGameOnLine) {
        this.isGameOnLine = isGameOnLine;
        this.notifyView();
    }

    /**
     * The method isServer simply returns true or false
     * if the current player is server true else false
     * @return isServer as a boolean
     */
    public boolean isServer() {
        return isServer;
    }
    
    /**
     * The method isServer simply sets the boolean value to true or false
     * if the current player is server true else false
     * @param isServer as a boolean
     */
    public void isServer(boolean isServer) {
        this.isServer = isServer;
        this.notifyView();
    }

    /**
     * The method isWhiteTurn simply returns true or false
     * if it is white turn to play true else false
     * @return isWhiteTurn as a boolean
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    /**
     * The method isWhiteTurn simply sets the boolean value to true or false
     * if it is the white turn to play true else false
     * @param isWhiteTurn as a boolean
     */
    public void isWhiteTurn(boolean isWhiteTurn) {
        this.isWhiteTurn = isWhiteTurn;
    }
    
    public void changeTurn ()
    {
    	this.isWhiteTurn = !this.isWhiteTurn;
    }
    
    /**
     * The getPlayers method simply returns the
     * players ArrayList to the caller object
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayerOneImage(Packet packet)
    {
    	players.get(0).setImagePath(packet.getPlayerIconPath());
    }
    public void setPlayerOneName(Packet packet)
    {
    	players.get(0).setName(packet.getGuestName());
    }
    public void setPlayerTwoImage(Packet packet)
    {
    	players.get(1).setImagePath(packet.getPlayerIconPath());
    }
    public void setPlayerTwoName(Packet packet)
    {
    	players.get(1).setName(packet.getGuestName());
    }
    

    /**
     * The method setPlayers sets players
     * @param players Player objects in the ArrayList<Player> players
     */
    public void setPlayers(ArrayList<Player> players) {
    	if(players != null)
    	{
	    	for(int i = 0; i < players.size(); i++)
	    	{
	    		Player newPlayer = players.get(i);
	    		Player currentPlayer = this.players.get(i);
	    		currentPlayer.updateInfo(newPlayer);
	    	}
	    	this.notifyView();
    	}
    }

    /**
     * The method getLoadedPlayers simply returns the Player ArrayList that
     * just been loaded from the file to the user so he will choose what to do next what player he wants to load up
     * @return ArrayList<Player> loadedPlayer
     */
    public ArrayList<Player> getLoadedPlayers() {
        return loadedPlayer;
    }

	public Square getSelectedSquare() {
		return selectedSquare;
	}

	public void setSelectedSquare(Square selectedSquare) {
		this.selectedSquare = selectedSquare;
	}

	
    public ChessGamePoint getEnPessant(ElementColor color) {
		if(color == ElementColor.White)
		{
			return whiteEnPessant;
		}
		else
		{
			return blackEnPessant;
		}
	}

	public void setEnPessant(ElementColor color, ChessGamePoint enPessant) {
		if(color == ElementColor.White)
		{
			this.whiteEnPessant = enPessant;
		}
		else
		{
			this.blackEnPessant = enPessant;
		}
	}

	/**
     * The method isWinner checks if there is a winner in the game
     * @return boolean value true if isWinner and false otherwise
     */
    public boolean isWinner() {
        for (int i = 0; i < this.capturedPieces.size(); i++) {
            Non_Visual_Piece pieceModel = this.capturedPieces.get(i);
            if (pieceModel.getType() == PieceType.King) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method getCapturedPieces simply returns the ArrayList when called upon
     * @return ArrayList<Piece>
     */
    @SuppressWarnings("unchecked")
	public synchronized ArrayList<Non_Visual_Piece> getCapturedPieces() {
        return (ArrayList<Non_Visual_Piece>) capturedPieces.clone();
    }
    
    public void addToCapturedPieces(Non_Visual_Piece pieceModel) {
        if(this.capturedPieces != null)
        {
        	if(!this.capturedPieces.contains(pieceModel))
        	{
        		this.capturedPieces.add(pieceModel);
        	}
        }
    }
    
    public void discardState() {
        if(this.squareModels != null)
        {
        	int size = this.getDimension();
        	for(int x = 0; x < size; x++)
        	{
        		for(int y = 0; y < size; y++)
        		{
        			this.squareModels[x][y].setPiece(null);
        		}
        	}
        }
        this.discardActivePiecesInSavedState();
        this.capturedPieces.clear();
        this.isWhiteTurn(true);
    }
    
    private void makeActivePiecesFromSquareModels() {
    	if(this.squareModels != null)
        {
	    	this.discardActivePiecesInSavedState();
	    	int size = this.getDimension();
	    	if(this.activePiecesInSavedState == null)
	    	{
	    		this.activePiecesInSavedState = new Non_Visual_Piece[size][size];
	    	}
        	for(int x = 0; x < size; x++)
        	{
        		for(int y = 0; y < size; y++)
        		{
        			if (this.squareModels[x][y] != null && this.squareModels[x][y].getPiece() != null)
        			{
        				this.activePiecesInSavedState[x][y] = this.squareModels[x][y].getPiece().getPieceModel();
        			}
        		}
        	}
        }
    }
    
    public static void destroy() {
        Chess_Data.chessData = null;
    }
    
    /**
     * The method notifyView simply calls the view objects
     * And in turn their update method is executed
     */
    public void notifyView() {
        this.setChanged();
        this.notifyObservers();
    }
    
    public void notifyView(Object data) {
        this.setChanged();
        this.notifyObservers(data);
    }

    /**
     * The method savePlayer saves the player properties to a file
     * Using ObjectOutputStream it saves ArrayList<Player> players
     */
    public void savePlayer() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("player.dat")));
            oos.writeObject(players);
            oos.flush();
            oos.close();
        } catch (Exception ioe) {
            JOptionPane.showMessageDialog(null, ioe.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The method loadPlayer loads the player properties from a file
     * Using ObjecInputStream it loads and stores player data to the ArrayList<Player> players
     */
    public void loadPlayer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("player.dat")));
            loadedPlayer = (ArrayList<Player>) ois.readObject();
            this.setPlayers(loadedPlayer);
            ois.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * The method save simply saves the current game
     * Using ObjectOutputStream all Serialized objects are
     * able to be saved
     */
    public void save() {
        try {
        	this.makeActivePiecesFromSquareModels();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(ChessGameConstants.saveFile)));
            ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(new File(ChessGameConstants.saveFileCapturedPieces)));
            oos.writeObject(activePiecesInSavedState);
            oss.writeObject(capturedPieces);
            oss.flush();
            oos.flush();
            oss.close();
            oos.close();
            this.discardActivePiecesInSavedState();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error " + ioe.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The overloaded save method simply saves the current game
     * Using ObjectOutputStream all Serialized objects are
     * able to be saved
     * @param file is the file to save
     */
    public void save(File file) {
        try {
        	this.makeActivePiecesFromSquareModels();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(file.getName() + ".bak"));
            oos.writeObject(activePiecesInSavedState);
            oss.writeObject(capturedPieces);
            oss.flush();
            oos.flush();
            oss.close();
            oos.close();
            this.discardActivePiecesInSavedState();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error " + ioe.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The load method simply loads the old game
     * Using ObjectInputStream it loads the game from the file
     * into an ArrayList and then notifies observers which in turn update their views
     */
    public boolean load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(ChessGameConstants.saveFile)));
            ObjectInputStream iis = new ObjectInputStream(new FileInputStream(new File(ChessGameConstants.saveFileCapturedPieces)));
            this.activePiecesInSavedState = (Non_Visual_Piece[][]) ois.readObject();
            this.capturedPieces = (ArrayList) iis.readObject();
            this.setPieces();
            iis.close();
            ois.close();
            return true;
        } catch (Exception ioe) {
            JOptionPane.showMessageDialog(null, "Error " + ioe.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * The overloaded load method simply loads the old game
     * Using ObjectInputStream it loads the game from the file
     * into an ArrayList and then notifies observers which in turn update their views
     * @param file to load from the System
     */
    @SuppressWarnings("unchecked")
	public boolean load(File file) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ObjectInputStream iis = new ObjectInputStream(new FileInputStream(file + ".bak"));
            this.activePiecesInSavedState = (Non_Visual_Piece[][]) ois.readObject();
            this.setPieces();
            this.capturedPieces = (ArrayList<Non_Visual_Piece>) iis.readObject();
            iis.close();
            ois.close();
            return true;
        } catch (Exception ioe) {
            JOptionPane.showMessageDialog(null, "Error " + ioe.toString(), "Error Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * The method setPieces simply sets the pieces that were just loaded from the file
     * back on the board
     */
    private void setPieces() {
    	int size = this.getDimension();
        for (int x = 0; x < size; x++) {
        	for (int y = 0; y < size; y++) {
        		if(this.activePiecesInSavedState != null)
        		{
        			Non_Visual_Piece oldModel = this.activePiecesInSavedState[x][y];
        			if(oldModel != null)
        			{
        				this.activePiecesInSavedState[x][y] = oldModel.copy();
        			}
        		}
        	}
        }        
    }
    public boolean getIsClientConnected()
    {
    	return isClientConnected;
    }
    public void setIsClientConnected(boolean isConnected)
    {
    	isClientConnected = isConnected;
    }
    
}
