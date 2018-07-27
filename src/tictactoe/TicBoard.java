package tictactoe;

import framework.Board;

public class TicBoard extends Board {
	
	public TicBoard(int rowNumber, int colNumber) {
		super(rowNumber, colNumber);
	}

	@Override
	public boolean isColumnFull(int col) {
		for (int row = 0; row < rowNumber; row++) {
			if (isIndexEmpty(row, col)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}
}
