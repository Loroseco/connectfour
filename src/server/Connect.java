package server;

import java.util.ArrayList;
import java.util.Scanner;

import client.*;
/**
 * Class used to handle functions specific to Connect 4 (win condition, and the game loop itself).
 * @author Loroseco
 *
 */
class Connect {
	
	private Board board;
	public ArrayList<Integer> moves = new ArrayList<Integer>();
	
	/**
	 * @param boardObject	Board object to be used
	 * @param moveString	Move given by player
	 * @param symbol		Move symbol
	 * @return				Success output (to be printed for player)
	 */
	String makeMove(String moveString, String symbol) {
		int rowN = board.getRowN();
		try {
			int move = Integer.parseInt(moveString);
			if (board.isColumnFull(move)) {
				return "INVALID MOVE: COLUMN IS FULL.";
			} else {
				for (int row = 0; row < rowN; row++) {
					if (board.isEqual(row, move, " ")) {
						board.set(row, move, symbol);
						return "MOVE MADE: PLAYER " + symbol + ", COLUMN " + moveString + ".";
					}
				}
			}
		} catch (NumberFormatException e) {
			return "INVALID MOVE.";
		} catch (IndexOutOfBoundsException e) {
			return "NUMBER OUT OF BOUNDS.";
		}
		return "UNKNOWN ERROR. PLEASE TRY AGAIN.";
	}
	
	/**
	 * Checks if either player has won
	 * @return	Symbol of winner, "" if none
	 */
	String findWinner() {
		g:
		for (int g = -1; g < 3; g++) {
			r:
			for (int r = 0; r < board.getRowN(); r++) {
				c:
				for (int c = 0; c < board.getColN(); c++) {
					String symbol = "XO";
					try {
						int row;
						int col;
						for (int i = 0; i < 4; i++) {
							if (g == 2) {
								row = r + i;
								col = c;
							} else {
								row = r + (i * g);
								col = c + i;
							}
							String element = board.get(row, col);
							if (symbol.contains(element)) {
								symbol = element;
							} else {
								continue c;
							}
						}
						return symbol;
					} catch(IndexOutOfBoundsException e) {
						if (g == 2) {
							break g;
						} else {
							continue r;
						}
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * Main game loop
	 * @param rowN	new rowN
	 * @param colN	new colN
	 * @param ai	ai
	 * @param scan	scanner
	 */
	void play(int rowN, int colN, boolean[] ai, Scanner scan) {
		String[] symbol = {"X", "O"};
		board = new Board(rowN, colN);
		Player[] player = new Player[2];
		for (int p = 0; p < 2; p++) {
			player[p] = ai[p] ? new AI(symbol[p])
							  : new Human(symbol[p]);
		}
		board.printBoard();
		String winner = "";
		while (winner.equals("")) {
			for (int p = 0; p < 2; p++) {
				winner = playTurn(player[p], ai[p], symbol[p], scan);
				if (!winner.equals("")) {
					break;
				}
			}
		}
		System.out.println("XO".contains(winner) ? "WINNER: PLAYER " + winner 
																	 : "GAME OVER: DRAW.");
	}
	
	/**
	 * Plays one turn of the game
	 * @param player	Player
	 * @param isAI		Is current player AI?
	 * @param symbol	Player symbol
	 * @param scan		Scanner
	 * @return			Symbol of winner, "n" for draw or "" for not finished
	 */
	private String playTurn(Player player, boolean isAI, String symbol, Scanner scan) {
		while (true) {
			if (board.isBoardFull()) {
				return "n";
			} else {
				String move = isAI ? player.getMove(board)
						           : player.getMove(scan);
				String output = makeMove(move, symbol);
				if (output.substring(0,  4).equals("MOVE")) {
					if (isAI) {
						board.printBoard();
					}
					moves.add(Integer.parseInt(move));
					System.out.println(output);
					break;
				}
				System.out.println(output);
			}
		}
		String result = findWinner();
		if (result.equals(symbol) && !isAI) {
			board.printBoard();
		}
		return result;
	}
}