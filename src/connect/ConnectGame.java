package connect;

import framework.TextOutput;
import framework.Game;
import framework.Player;
import framework.Scoreboard;

/**
 * Class used to handle functions specific to Connect 4 (win condition, and the game loop itself).
 * @author Loroseco
 *
 */
public class ConnectGame extends Game {
	
	public ConnectGame(ConnectBoard board, Scoreboard score, Player[] player) {
		super(board, score, player);
	}
	
	@Override
	public boolean makeMove(String moveString, int playerNumber) 
	{
		try {
			int move = Integer.parseInt(moveString);
			if (((ConnectBoard) board).isColumnFull(move)) {
				TextOutput.ERROR_COLUMN_FULL.println();
				return false;
			} else {
				for (int row = 0; row < ConnectConfig.NO_OF_ROWS; row++) {
					if (((ConnectBoard) board).isIndexEmpty(row, move)) {
						((ConnectBoard) board).set(row, move, playerNumber);
						return true;
					}
				}
			}
		} catch (NumberFormatException e) {
			TextOutput.ERROR_INVALID.println();
			return false;
		} catch (IndexOutOfBoundsException e) {
			TextOutput.ERROR_OUT_OF_BOUNDS.println();
			return false;
		}
		TextOutput.ERROR_UNKNOWN.println();
		return false;
	}
	
	/**
	 * @return	0 or 1 for winning players, 3 if no winner
	 */
	@Override
	public int findWinner() {
		gradientLoop:
		for (int gradient = -1; gradient < 3; gradient++) {
			rowLoop:
			for (int row = 0; row < ConnectConfig.NO_OF_ROWS; row++) {
				for (int col = 0; col < ConnectConfig.NO_OF_COLS; col++) {
					try {
						int winner = checkForWinningPattern(row, col, gradient);
						if (winner == 3) {
							continue;
						} else {
							return winner;
						}
					} catch(IndexOutOfBoundsException e) {
						if (gradient == 2) {
							break gradientLoop;
						} else {
							continue rowLoop;
						}
					}
				}
			}
		}
		return 3;
	}
	
	private int checkForWinningPattern(int startRow, int startCol, int gradient) {
		int[] counter = {0, 0};
		int row;
		int col;
		for (int i = 0; i < 4; i++) {
			if (gradient == 2) {
				row = startRow + i;
				col = startCol;
			} else {
				row = startRow + (i * gradient);
				col = startCol + i;
			}
			for (int playerNumber = 0; playerNumber < ConnectConfig.NO_OF_PLAYERS; playerNumber++) {
				if (((ConnectBoard) board).isIndexEqual(row, col, playerNumber)) {
					counter[playerNumber]++;
				}
			}
		}
		for (int playerNumber = 0; playerNumber < counter.length; playerNumber++) {
			if (counter[playerNumber] == 4) {
				return playerNumber;
			}
		}
		return 3;
	}
}