package server;
/**
 * Object that stores srtings to be outputted when the board is constructed.	<br>
 * 																				<br>
 * Key:																			<br>
 * 00: Player X, row 0															<br>
 * 01: Player X, row 1															<br>
 * 02: Player X, row 2															<br>
 * 03: Player X, row 3															<br>
 * 04: Player O, row 0															<br>
 * 05: Player O, row 1															<br>
 * 06: Player O, row 2															<br>
 * 07: Player O, row 3															<br>
 * 08: Empty																	<br>
 * 09: Border																	<br>
 * 10: Side Key																	<br>
 * 11: Bottom Key																<br>
 * 
 * @author Loroseco
 * 
 */
public final class ConnectBoardOutput {
	/**
	 * Text used to draw board
	 */
	public static final String[] BOARD = 
	     {" \\  / |",  //Player X
		  "  \\/  |",
		  "  /\\  |",
		  " /  \\ |",
		  " /--\\ |",  //Player O
		  " |  | |",
		  " |  | |",
		  " \\--/ |",
		  "      |",   //Empty
		  "------+",   //Border
		  "%s|",   //Side key
		  "  %s  |"};//Bottom key 
	
	public static void print(int i) {
		System.out.print(BOARD[i]);
	}
	
	public static void print(int i, String key) {
		System.out.print(String.format(BOARD[i], key));
	}
	
	public static void printPartial(int i) {
		System.out.print(BOARD[i].substring(4));
	}
	
	public static void println(int i) {
		System.out.println(BOARD[i]);
	}
	
	public static void println(int i, String key) {
		System.out.println(String.format(BOARD[i], key));
	}
	
	public static void printlnPartial(int i) {
		System.out.println(BOARD[i].substring(4));
	}
}
