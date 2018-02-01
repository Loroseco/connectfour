package client;
import java.util.Scanner;
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
	 * Accessor
	 * @return	Symbol
	 */
	public String getSymbol() {
		return null;
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
	public abstract String getMove(String[][] board);
}