package client;
import java.util.Scanner;

import server.Board;
/**
 * Player class to be used by human player
 * @author Loroseco
 *
 */
public abstract class Player {
	
	String symbol;
	
	/**
	 * Abstract player constructor
	 * @param symbol	Player symbol
	 */
	public Player(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Gets move from player
	 * @param scan	Scanner used to get move
	 * @return	Move in string format
	 */
	public abstract String getMove(Scanner scan);
	
	/**
	 * Gets move from AI
	 * @param board	Board for AI to read
	 * @return	Move in string format
	 */
	public abstract String getMove(Board board);
}