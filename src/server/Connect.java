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
	
	void setBoard(Board board) {
		this.board = board;
	}
	/**
	 * @param boardObject	Board object to be used
	 * @param moveString	Move given by player
	 * @param symbol		Move symbol
	 * @return				Success output (to be printed for player)
	 */
	String makeMove(String moveString, String symbol) {
		String[][] board = this.board.getBoard();
		int rowN = board.length;
		try {
			int move = Integer.parseInt(moveString);
			for (int r = 0; r < rowN; r++) {
				if (board[r][move].equals(" ")) {
					this.board.setValue(r, move, symbol);
					return "MOVE MADE: PLAYER " + symbol + ", COLUMN " + moveString + ".";
				}
			}
			return "INVALID MOVE: COLUMN IS FULL.";
		} catch (NumberFormatException e) {
			return "INVALID MOVE.";
		} catch (IndexOutOfBoundsException e) {
			return "NUMBER OUT OF BOUNDS.";
		}
	}
	/**
	 * Checks if either player has won
	 * @return	Symbol of winner, "" if none
	 */
	String findWinner() {
		String[][] board = this.board.getBoard();
		g:
		for (int g = -1; g < 3; g++) {
			r:
			for (int r = 0; r < board.length; r++) {
				c:
				for (int c = 0; c < board[0].length; c++) {
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
							if (symbol.contains(board[row][col])) {
								symbol = board[row][col];
							}
							else continue c;
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
		setBoard(new Board(rowN, colN));
		Player[] player = new Player[2];
		for (int p = 0; p < 2; p++) {
			player[p] = ai[p] ? new AI(symbol[p])
							  : new Player(symbol[p]);
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
				String move = isAI ? player.getMove(board.getBoard())
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
		return findWinner();
	}
}