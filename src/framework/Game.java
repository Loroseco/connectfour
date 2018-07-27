package framework;

import java.util.ArrayList;

/**
 * Abstract Game class
 * @author Loroseco
 *
 */
public abstract class Game
{
	protected final Board board;
	private final ArrayList<Integer> movesPlayed;
	private final Scoreboard score;
	private final Player[] player;
	
	public Game(Board board, Scoreboard score, Player[] player) {
		this.board = board;
		this.score = score;
		this.player = player;
		this.movesPlayed = new ArrayList<>();
	}
	
	public void play() {
		boolean playing = true;
		while (playing) {
			playGame();
			
			while(true) {
				TextOutput.PLAY_AGAIN.print();
				String input = User.getInput();
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
	
	public void playGame() {
		board.createBoard();
		movesPlayed.clear();
		
		int winner = 3;
		int playerNumber = 1;
		while (winner == 3) {
			playerNumber = playerNumber == 1 ? 0 : 1;
			winner = playTurn(playerNumber);
		}
		
		if (winner == 0 || winner == 1) {
			System.out.println("WINNER: PLAYER " + Config.SYMBOLS[winner]);
			score.addScore(winner);
			printMoves(winner);
		} else {
			System.out.println("DRAW");
		}
		score.printAllScores();
	}
	
	public int playTurn(int playerNumber) {
		while (true) {
			if (board.isBoardFull()) {
				return 2;
			} else {
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
	
	public abstract boolean makeMove(String move, int playerNumber);
	
	public abstract int findWinner();
	
	public void printMoves(int winner) {
		for (int p = 0; p < Config.NO_OF_PLAYERS; p++) {
			if (winner != p && Config.IS_AI[p]) {
				System.out.println(movesPlayed);
				break;
			}
		}
	}
}
