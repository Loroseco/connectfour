package framework;

import connect.*;
import tictactoe.TicAI;
import tictactoe.TicBoard;
import tictactoe.TicGame;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class Main {
	public static void main(String[] args) {
		User.openScanner();
		
//		System.out.print("CHOOSE A GAME: ");
//		while (true) {
//			String choice = User.getUserInput();
//			if (choice.equals("0")) {
//				playConnect();
//				break;
//			} else if (choice.equals("1")) {
		playTicTacToe();
//				break;
//			} else {
//				TextOutput.ERROR_INVALID.println();
//			}
//		}
		User.closeScanner();
	}
	
	public static void playConnect() {
		ConnectBoard board = new ConnectBoard();
		Scoreboard score = new Scoreboard();
		Player[] player = new Player[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < 2; playerNumber++) {
			player[playerNumber] = Config.IS_AI[playerNumber] ? new ConnectAI(playerNumber, (ConnectBoard) board)
							    							  : new Human(playerNumber);
		}

		ConnectGame game = new ConnectGame(board, score, player);
		game.play();
	}
	
	public static void playTicTacToe() {
		TicBoard board = new TicBoard();
		Scoreboard score = new Scoreboard();
		Player[] player = new Player[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < 2; playerNumber++) {
			player[playerNumber] = Config.IS_AI[playerNumber] ? new TicAI(playerNumber, (TicBoard) board)
															  : new Human(playerNumber);
		}
		
		TicGame game = new TicGame(board, score, player);
		game.play();
	}
}