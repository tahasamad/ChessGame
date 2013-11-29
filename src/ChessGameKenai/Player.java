/*
 * Description: Class Player.java
 * Author: Dimtri Pankov
 * Date: 16-Dec-2010
 * Version: 1.0
 */
package ChessGameKenai;

import java.io.Serializable;
import java.util.Observable;

/**
 * The Player class implements Serializable
 * Its the Player that is created each time you create a game
 * The two players will have default names which are player1 and player2 respectively
 * @author Dimitri Pankov
 * @see Serializable
 * @version 1.01
 */
public class Player implements Serializable {

    private String name;
    private int numberOfWins;
    private String imagePath;
    private int seconds, minutes,hours;
    private int NumOfHoursInADay = 24;
    private int NumOfSecondsInAMin = 60;
    private int NumOfMinsInAnHour = 60;

    /**
     * OverLoaded Constructor for creating Player object
     * @param name name of the Player object
     */
    public Player(String name) {
        this.name = name;
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void resetTime()
	{
		seconds = 0;
		minutes = 0;
		hours = 0;
		
	}
	public void incrementTime()
	{
		seconds++;    
		if (seconds == NumOfSecondsInAMin) 
		{
			incrementMinutes();
		}		
		if (minutes == NumOfMinsInAnHour) 
		{
			incrementHours();
		}
		if (hours == NumOfHoursInADay)
		{
			resetTime();
		}
	}
	
	private void incrementMinutes ()
	{
		seconds = 0;
     	minutes++;
	}
	
	private void incrementHours ()
	{
		 minutes = 0;
		 hours++;
	}
	

	/**
     * Overloaded constructor of the class
     * receives name and image path to represent itself
     * @param name as a String
     * @param imagePath as a String
     */
    public Player(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    /**
     * The method getName returns the name of the Player object
     * @return name of the Player
     */
    public String getName() {
        return name;
    }

    /**
     * The method setName sets name of the Player object
     * @param name of the object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method getNumberOfWins returns number of wins
     * @return number of wins
     */
    public int getNumberOfWins() {
        return numberOfWins;
    }

    /**
     * The method setNumberOfWins sets number of wins for the object
     * @param numberOfWins as an integer
     */
    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    /**
     * The method getIcon returns current icon of the Player object
     * @return icon of the Player object
     */
    public String getIconPath() {
        return imagePath;
    }

    /**
     * The method setImagePath simply sets the path to the image
     * of the current object
     * @param imagePath as a String
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * The class  needs to overwrite the method the toString
     * this method is very useful for debugging
     * @return s as a String
     */
    @Override
    public String toString() {
        String s = "";
        s += "Name: " + this.getName() + ", Wins: " + this.getNumberOfWins() + ", Icon: " + this.getIconPath();
        return s;
    }
    
    public void updateInfo(Player player)
    {
    	this.name = player.getName();
        this.numberOfWins = player.getNumberOfWins();
        this.imagePath = player.getIconPath();
    }
}
