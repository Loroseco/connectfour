package connect;

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
				ConnectTextOutput.ERROR_COLUMN_FULL.println();
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
			ConnectTextOutput.ERROR_INVALID.println();
			return false;
		} catch (IndexOutOfBoundsException e) {
			ConnectTextOutput.ERROR_OUT_OF_BOUNDS.println();
			return false;
		}
		ConnectTextOutput.ERROR_UNKNOWN.println();
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
				columnLoop:
				for (int col = 0; col < ConnectConfig.NO_OF_COLS; col++) {
					int[] counter = {0, 0};
					try {
						int newRow;
						int newCol;
						for (int i = 0; i < 4; i++) {
							if (gradient == 2) {
								newRow = row + i;
								newCol = col;
							} else {
								newRow = row + (i * gradient);
								newCol = col + i;
							}
							for (int p = 0; p < ConnectConfig.NO_OF_PLAYERS; p++) {
								if (((ConnectBoard) board).isIndexEqual(newRow, newCol, p)) {
									counter[p]++;
								}
							}
						}
						for (int p = 0; p < counter.length; p++) {
							if (counter[p] == 4) {
								return p;
							}
						}
						continue columnLoop;
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
}