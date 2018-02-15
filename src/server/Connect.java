package server;

import java.util.ArrayList;
import java.util.Scanner;

import client.*;
import computer.*;
/**
 * Class used to handle functions specific to Connect 4 (win condition, and the game loop itself).
 * @author Loroseco
 *
 */
class Connect {
	
	private int rowN;
	private int colN;
	private boolean[] isAI;
	private Scanner scan;
	
	private Board board;
	private Player[] player;
	private ArrayList<Integer> moves;
	private String[] symbol = {"X", "O"};
	
	/**
	 * Main game constructor
	 * @param rowN	Number of rows
	 * @param colN	Number of columns
	 * @param isAI	Array showing which players are AI
	 * @param scan	Scanner for player input
	 */
	Connect(int rowN, int colN, boolean[] isAI, Scanner scan) {
		this.rowN = rowN;
		this.colN = colN;
		this.isAI = isAI;
		this.scan = scan;
		
		player = new Player[2];
		for (int p = 0; p < 2; p++) {
			player[p] = isAI[p] ? new AI(symbol[p])
							    : new Human(symbol[p]);
		}
	}
	
	/**
	 * Moves list accessor
	 * @return	List of played moves
	 */
	ArrayList<Integer> getMoves() {
		return this.moves;
	}
	
	/**
	 * @param moveString	Move given by player
	 * @param symbol		Move symbol
	 * @return				Success output (to be printed for player)
	 */
	String makeMove(String moveString, String symbol) {
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
			for (int r = 0; r < rowN; r++) {
				c:
				for (int c = 0; c < colN; c++) {
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
	 */
	void play() {
		board = new Board(rowN, colN);
		moves = new ArrayList<Integer>();
		board.print();
		String winner = "";
		while (winner.equals("")) {
			for (int p = 0; p < 2; p++) {
				winner = playTurn(p);
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
	 * @param p	Player number
	 * @return	Symbol of winner, "n" for draw or "" for not finished
	 */
	private String playTurn(int p) {
		while (true) {
			if (board.isBoardFull()) {
				return "n";
			} else {
				String move = isAI[p] ? player[p].getMove(board)
						           : player[p].getMove(scan);
				String output = makeMove(move, symbol[p]);
				if (output.substring(0,  4).equals("MOVE")) {
					if (isAI[p]) {
						board.print();
					}
					moves.add(Integer.parseInt(move));
					System.out.println(output);
					break;
				}
				System.out.println(output);
			}
		}
		String result = findWinner();
		if (result.equals(symbol[p]) && !isAI[p]) {
			board.print();
		}
		return result;
	}
}