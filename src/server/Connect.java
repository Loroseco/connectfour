package server;

import java.util.ArrayList;
import java.util.Scanner;

import client.Human;
import computer.AI;
import computer.Player;

/**
 * Class used to handle functions specific to Connect 4 (win condition, and the game loop itself).
 * @author Loroseco
 *
 */
public class Connect {
	private final Scanner scan;
	private final Board board;
	private final ArrayList<Integer> movesPlayed;
	private final Scoreboard score;
	private final Player[] player;
	
	public Connect(Scanner scan) {
		this.scan = scan;
		this.board = new Board();
		this.movesPlayed = new ArrayList<>();
		this.score = new Scoreboard();
		this.player = new Player[2];
		
		for (int playerNumber = 0; playerNumber < 2; playerNumber++) {
			player[playerNumber] = Config.IS_AI[playerNumber] ? new AI(playerNumber, board)
							    							  : new Human(playerNumber, scan);
		}
	}
	
	public void play() {
		boolean playing = true;
		while (playing) {
			playGame();
			
			while (true) {
				TextOutput.PLAY_AGAIN.print();
				String input = scan.next().toLowerCase();
				if (input.equals("y") || input.equals("yes")) {
					break;
				}
				if (input.equals("n") || input.equals("no")) {
					playing = false;
					break;
				}
				TextOutput.ERROR_INVALID.println();
			}
		}
	}
	void playGame() {
		board.createBoard();
		movesPlayed.clear();

		int winner = 3;
		int playerNumber = 1;
		while (winner == 3) {
			playerNumber = playerNumber == 1 ? 0 : 1;
			winner = playTurn(playerNumber);
		}
		
		if (winner == 0 || winner == 1) {
			TextOutput.WINNER.println(winner);
			score.addScore(winner);
			printMoves(winner);
		} else {
			TextOutput.DRAW.println();
		}
		score.printAllScores();
	}
	
	private int playTurn(int playerNumber) {
		while (true) {
			if (board.isBoardFull()) {
				return 2;
			} 
			else {
				String move = player[playerNumber].getMove();
				if (makeMove(move, playerNumber)) {
					if (Config.IS_AI[playerNumber]) {
						board.print();
					}
					movesPlayed.add(Integer.parseInt(move));
					TextOutput.MOVE_MADE.println(playerNumber, move);
					break;
				}
			}
		}
		int winner = findWinner();
		if (winner == playerNumber && !Config.IS_AI[playerNumber]) {
			board.print();
		}
		return winner;
	}
	
	boolean makeMove(String moveString, int playerNumber) 
	{
		try {
			int move = Integer.parseInt(moveString);
			if (board.isColumnFull(move)) {
				TextOutput.ERROR_COLUMN_FULL.println();
				return false;
			} else {
				for (int row = 0; row < Config.NO_OF_ROWS; row++) {
					if (board.isIndexEmpty(row, move)) {
						board.set(row, move, playerNumber);
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
	private int findWinner() {
		gradientLoop:
		for (int gradient = -1; gradient < 3; gradient++) {
			rowLoop:
			for (int row = 0; row < Config.NO_OF_ROWS; row++) {
				columnLoop:
				for (int col = 0; col < Config.NO_OF_COLS; col++) {
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
							for (int p = 0; p < Config.NO_OF_PLAYERS; p++) {
								if (board.isIndexEqual(newRow, newCol, p)) {
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
	
	private void printMoves(int winner) {
		for (int p = 0; p < Config.NO_OF_PLAYERS; p++) {
			if (winner != p && Config.IS_AI[p]) {
				System.out.println(movesPlayed);
				break;
			}
		}
	}
}