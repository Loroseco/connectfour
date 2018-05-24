package server;

import java.util.Arrays;

/**
 * Board object. Handles Board reading and editing.
 * @author Loroseco
 *
 */
public class ConnectBoard {
	private int[][] board;
	
	ConnectBoard() {
		this.board = new int[ConnectConfig.NO_OF_ROWS][ConnectConfig.NO_OF_COLS];
	}
	
	void set(int row, int col, int playerNumber) {
		board[row][col] = playerNumber;
	}
	
	public boolean isColumnFull(int col) {
		return !isIndexEmpty(ConnectConfig.NO_OF_ROWS - 1, col);
	}
	
	public boolean isBoardFull() {
		for (int col = 0; col < ConnectConfig.NO_OF_COLS; col++) 
		{
			if (!isColumnFull(col)) {
				return false; 
			}
		}
		return true;
	}
	
	public boolean isIndexEmpty(int row, int col) {
		return board[row][col] == ConnectConfig.EMPTY_SPACE;
	}
	
	public boolean isIndexEqual(int row, int col, int playerNumber) {
		return board[row][col] == playerNumber;
	}

	public void createBoard() {
		for (int row = 0;  row < ConnectConfig.NO_OF_ROWS; row++) {
			for (int col = 0; col < ConnectConfig.NO_OF_COLS; col++) {
				board[row][col] = ConnectConfig.EMPTY_SPACE;
			}
		}
		print();
	}
	
	/**
	 * Prints board in 2D array format
	 */
	void debugBoard() {
		System.out.println(Arrays.deepToString(board));
	}
	
	void print() {
		if (ConnectConfig.DEBUG) {
			debugBoard();
		}
		for (int row = ConnectConfig.NO_OF_ROWS - 1; row > -1; row--) {
			printBorder();	
			for (int rowSection = 0; rowSection < 4; rowSection++) {
				printRow(row, rowSection);
			}
		}
		printBottomKey();
	}
	
	private void printRow(int row, int rowSection) {
		printSide(row, rowSection);
		for (int col = 0; col < ConnectConfig.NO_OF_COLS; col++) {
			if (board[row][col] == 0) {
				BoardOutput.print(rowSection);
			} else if (board[row][col] == 1) {
				BoardOutput.print(rowSection + 4);
			} else {
				BoardOutput.print(8);
			}
		}
		System.out.println();
	}
	
	private void printSide(int row, int rowSection) {
		String rowString = "  ";
		if (rowSection == 1) {
			rowString = Integer.toString(row);
			if (row < 10) {
				rowString = " " + rowString;
			}
		}
		BoardOutput.print(10, rowString);
	}
	
	private void printBorder() {
		BoardOutput.printPartial(9);
		for (int col = 0; col < ConnectConfig.NO_OF_COLS - 1; col++) {
			BoardOutput.print(9);
		}
		BoardOutput.println(9);
	}

	private void printBottomKey() {
		printBorder();
		printSide(0, 0);
		for (int col = 0; col < ConnectConfig.NO_OF_COLS; col++) {
			String colString = Integer.toString(col);
			if (col < 10) {
				colString = " " + colString;
			}
			BoardOutput.print(11, colString);
		}
		System.out.println("\n");
	}
}