package client;
import java.util.Scanner;
/**
 * Player class to be used by human player
 * @author Loroseco
 *
 */
public class Player {
	
	String symbol;
	
	/**
	 * Player constructor
	 * @param symbol	Symbol used to play
	 */
	public Player(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Accessor
	 * @return	Symbol
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Gets move from player
	 * @param scan	Scanner used to get move
	 * @return	Move in string format
	 */
	public String getMove(Scanner scan) {
		System.out.print("PLAYER " + this.getSymbol() + " - ENTER MOVE: ");
		return scan.next();
	}
	
	/**
	 * Gets move from AI
	 * @param board	Board for AI to read
	 * @return	Move in string format
	 */
	public String getMove(String[][] board) {
		return "n";
	}
}