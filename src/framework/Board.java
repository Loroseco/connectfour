package framework;

import java.util.Arrays;

/**
 * Abstract board class for framework
 * @author Loroseco
 *
 */
public abstract class Board
{
	protected int rowNumber;
	protected int colNumber;
	protected int[][] board;
	
	public Board(int rowNumber, int colNumber) {
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
		this.board = new int[rowNumber][colNumber];
	}

	public void createBoard() {
		for (int row = 0;  row < rowNumber; row++) {
			for (int col = 0; col < colNumber; col++) {
				board[row][col] = Config.EMPTY_SPACE;
			}
		}
		print();
	}

	public void set(int row, int col, int playerNumber) {
		board[row][col] = playerNumber;
	}

	public boolean isIndexEqual(int row, int col, int playerNumber) {
		return board[row][col] == playerNumber;
	}

	public boolean isIndexEmpty(int row, int col) {
		return isIndexEqual(row, col, Config.EMPTY_SPACE);
	}

	public boolean isBoardFull() {
		for (int col = 0; col < colNumber; col++) {
			if (!isColumnFull(col)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Prints board in 2D array format
	 */
	protected void debugBoard() {
		System.out.println(Arrays.deepToString(board));
	}

	public abstract boolean isColumnFull(int col);
	
	public abstract void print();
}
