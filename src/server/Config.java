package server;

/**
 * Config class to hold priority order for AI class.
 * Will be replaced by a json file as soon as I work out how json files work.
 * @author Loroseco
 *
 */
public final class Config 
{
	
	/**
	 * Array of player symbols
	 */
	public static final String[] SYMBOLS = {"X", "O"};
	
	/**
	 * Number of players
	 */
	public static final int PLAYER_N = SYMBOLS.length;
	
	/**
	 * Number of rows
	 */
	public static final int ROW_N = 7;
	
	/**
	 * Number of columns
	 */
	public static final int COL_N = 10;
	
	/**
	 * Which players will be AI
	 */
	public static final boolean[] IS_AI = {false, true};
	
	/**
	 * Used as empty space in the board
	 */
	public static final String EMPTY = " ";
}
