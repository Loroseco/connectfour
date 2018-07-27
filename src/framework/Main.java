package framework;

import connect.ConnectAI;
import connect.ConnectBoard;
import connect.ConnectGame;
import connect.ConnectHuman;
import tictactoe.TicAI;
import tictactoe.TicBoard;
import tictactoe.TicGame;
import tictactoe.TicHuman;

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
		ConnectBoard board = new ConnectBoard(Config.ROW_NUMBER_CONNECT, Config.COL_NUMBER_CONNECT);
		Scoreboard score = new Scoreboard();
		Player[] player = new Player[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < 2; playerNumber++) {
			player[playerNumber] = Config.IS_AI[playerNumber] ? new ConnectAI(playerNumber, (ConnectBoard) board)
							    							  : new ConnectHuman(playerNumber);
		}

		ConnectGame game = new ConnectGame(board, score, player);
		game.play();
	}
	
	public static void playTicTacToe() {
		TicBoard board = new TicBoard(Config.ROW_NUMBER_TIC, Config.COL_NUMBER_TIC);
		Scoreboard score = new Scoreboard();
		Player[] player = new Player[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < 2; playerNumber++) {
			player[playerNumber] = Config.IS_AI[playerNumber] ? new TicAI(playerNumber, (TicBoard) board)
															  : new TicHuman(playerNumber);
		}
		
		TicGame game = new TicGame(board, score, player);
		game.play();
	}
}