package framework;

public class Config {
	/** Symbol used to represent empty space*/
	public static final int EMPTY_SPACE = 2;
	public static final boolean[] IS_AI = {false, true};
	public static final String[] SYMBOLS = {"X", "O"};
	public static final int NO_OF_PLAYERS = SYMBOLS.length;
	public static final boolean DEBUG = false;

	//CONNECT 4
	public static final int ROW_NUMBER_CONNECT = 7;
	public static final int COL_NUMBER_CONNECT = 10;

	//TIC TAC TOE
	public static final int ROW_NUMBER_TIC = 3;
	public static final int COL_NUMBER_TIC = 3;
}
