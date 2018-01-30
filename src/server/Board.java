package server;
import java.util.Arrays;

/**
 * Board object. Handles Board reading and editing.
 * @author Loroseco
 *
 */
class Board {
	private String[][] board;
	private int rowN;
	private int colN;
	private String[] boardText = {" \\  / |",
			 "  \\/  |",
			 "  /\\  |",
			 " /  \\ |",
			 " /--\\ |",
			 " |  | |",
			 " |  | |",
			 " \\--/ |",
			 "      |",
			 "------+"};
	
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
		this.rowN = rowN;
		this.colN = colN;
	}
	
	/**
	 * Accessor
	 * @return board
	 */
	String[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Accessor
	 * @return	Number of rows
	 */
	int getRowN() {
		return this.rowN;
	}
	
	/**
	 * Accessor
	 * @return	Number of columns
	 */
	int getColN() {
		return this.colN;
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
		
		System.out.println("\n");
		for (int row = rowN - 1; row > -2; row--) {
			printRow(row);
		}
		printBottomKey();
	}
	
	private void printRow(int row) {
		System.out.println(boardText[0].substring(4, 7));
		for (int col = 0; col < colN; col++) {
			System.out.print(boardText[9]);
		}
		if (row == -1) {
			return;
		}
		System.out.println();
		for (int i = 0; i < 4; i++) {
			if (i == 1) {
				if (row < 10) {
					System.out.print(" ");
				System.out.print(Integer.toString(row) + "|");
				}
			} else {
				System.out.print("  |");
			}
			for (int col = 0; col < colN; col++) {
				if (board[row][col].equals("X")) {
					System.out.println(boardText[i]);
				} else if (board[row][col].equals("O")) {
					System.out.println(boardText[i + 4]);
				} else {
					System.out.println(boardText[8]);
				}
				System.out.println();
			}
		}
	}
	
	private void printBottomKey() {
		System.out.print("\n  |");
		for (int c = 0; c < colN; c++) {
			if (c < 10) {
				System.out.print(" ");
			}
			System.out.print("  " + Integer.toString(c) + "  |");
		}
		System.out.println("\n");
	}
}