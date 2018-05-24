package server;

/**
 * Config class to hold priority order for AI class.
 * Will be replaced by a json file as soon as I work out how json files work.
 * @author Loroseco
 *
 */
public final class ConnectConfig {
	
	public static final String[] SYMBOLS = {"X", "O"};
	public static final int NO_OF_PLAYERS = SYMBOLS.length;
	public static final int NO_OF_ROWS = 7;
	public static final int NO_OF_COLS = 10;
	public static final boolean[] IS_AI = {false, true};
	public static final int EMPTY_SPACE = 2;
	
	public static final boolean DEBUG = false;
}
