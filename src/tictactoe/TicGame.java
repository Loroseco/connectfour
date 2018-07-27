package tictactoe;

import framework.Game;
import framework.Player;
import framework.Scoreboard;
import framework.TextOutput;

public class TicGame extends Game{
	
	public TicGame(TicBoard board, Scoreboard score, Player[] player) {
		super(board, score, player);
	}

	@Override
	public boolean makeMove(String moveString, int playerNumber) {
		try {
			String[] moveArr = moveString.split("");
			int row = Integer.parseInt(moveArr[0]);
			int col = Integer.parseInt(moveArr[1]);

			if (((TicBoard) board).isIndexEmpty(row, col)) {
				((TicBoard) board).set(row, col, playerNumber);
				return true;
			} else {
				TextOutput.ERROR_INDEX_FULL.println();
				return false;
			}
		} catch (NumberFormatException e) {
			TextOutput.ERROR_INVALID.println();
			return false;
		} catch (IndexOutOfBoundsException e) {
			TextOutput.ERROR_OUT_OF_BOUNDS.println();
			return false;
		}
	}

	@Override
	public int findWinner() {
		// TODO Auto-generated method stub
		return 0;
	}

}
