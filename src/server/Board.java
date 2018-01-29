package server;
import java.util.Arrays;

/**
 * Board object. Handles Board reading and editing.
 * @author Loroseco
 * Here is a test comment
 */
class Board {
	private String[][] board;
	/**
	 * Constructor. Makes the board 2D array using rowN x colN dimensions
	 * @param rowN	Number of rows
	 * @param colN	Number of columns
	 */
	Board(int rowN, int colN) {
		String[][]board = new String[rowN][colN];
		for (int r = 0; r < rowN; r++) {
			for (int c = 0; c < colN; c++) {
				board[r][c] = " ";
			}
		}
		this.board = board;
	}
	/**
	 * Accessor
	 * @return board
	 */
	String[][] getBoard() {
		return this.board;
	}
	/**
	 * Board value mutator
	 * @param row	Value row
	 * @param col	Value column
	 * @param value	New value
	 */
	void setValue(int row, int col, String value) {
		this.board[row][col] = value;
	}
	/**
	 * Checks if board is full
	 * @return	True or false
	 */
	boolean isBoardFull() {
		for (int c = 0; c < board[0].length; c++) {
			if (board[board.length - 1][c].equals(" ")) {
				return false; 
			}
		}
		return true;
	}
	/**
	 * Prints board in 2D array format
	 */
	void debugBoard() {
		System.out.println(Arrays.deepToString(this.getBoard()));
	}
	/**
	 * Prints board in format usable for game
	 */
	void printBoard() {
		int rowN = board.length;
		int colN = board[0].length;
		String[] text = {" \\  / |",
						 "  \\/  |",
						 "  /\\  |",
						 " /  \\ |",
						 " /--\\ |",
						 " |  | |",
						 " |  | |",
						 " \\--/ |",
						 "      |",
						 "------+"};
		System.out.println("\n");
		for (int r = rowN - 1; r > -2; r--) {
			System.out.print(text[9].substring(4, 7));
			for (int c = 0; c < colN; c++) {
				System.out.print(text[9]);
			}
			if (r == -1) {
				break;
			}
			System.out.println();
			for (int i = 0; i < 4; i++) {
				if (i == 1) {
					System.out.print((r < 10 ? " " 
											 : "") 
							+ Integer.toString(r) + "|");
				} else {
        			System.out.print("  |");
        		}
				for (int c = 0; c < colN; c++) {
					System.out.print(board[r][c].equals("X") ? text[i] 
															 : board[r][c].equals("O") ? text[i + 4] 
																	 				   : text[8]);
				}
				System.out.println();
			}
		}
        System.out.print("\n  |");
        for (int c = 0; c < colN; c++) {
        	System.out.print((c < 10 ? "   " 
        							 : "  ") 
        			+ Integer.toString(c) + "  |");
        }
        System.out.println("\n");
	}
}