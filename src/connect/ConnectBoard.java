package connect;

import framework.Board;
import framework.Config;

/**
 * Board object. Handles Board reading and editing.
 * @author Loroseco
 *
 */
public class ConnectBoard extends Board {
	
	public ConnectBoard(int rowNumber, int colNumber) {
		super(rowNumber, colNumber);
	}
	
	public boolean isColumnFull(int col) {
		return !isIndexEmpty(rowNumber - 1, col);
	}
	
	@Override
	public void print() {
		if (Config.DEBUG) {
			debugBoard();
		}
		for (int row = rowNumber - 1; row > -1; row--) {
			printBorder();	
			for (int rowSection = 0; rowSection < 4; rowSection++) {
				printRow(row, rowSection);
			}
		}
		printBottomKey();
	}
	
	private void printRow(int row, int rowSection) {
		printSide(row, rowSection);
		for (int col = 0; col < colNumber; col++) {
			if (board[row][col] == 0) {
				ConnectBoardOutput.print(rowSection);
			} else if (board[row][col] == 1) {
				ConnectBoardOutput.print(rowSection + 4);
			} else {
				ConnectBoardOutput.print(8);
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
		ConnectBoardOutput.print(10, rowString);
	}
	
	private void printBorder() {
		ConnectBoardOutput.printPartial(9);
		for (int col = 0; col < colNumber - 1; col++) {
			ConnectBoardOutput.print(9);
		}
		ConnectBoardOutput.println(9);
	}

	private void printBottomKey() {
		printBorder();
		printSide(0, 0);
		for (int col = 0; col < colNumber; col++) {
			String colString = Integer.toString(col);
			if (col < 10) {
				colString = " " + colString;
			}
			ConnectBoardOutput.print(11, colString);
		}
		System.out.println("\n");
	}
}