package framework;

import java.util.Scanner;

import connect.*;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		playConnect(scan);
		
		scan.close();
	}
	
	public static void playConnect(Scanner scan) {
		ConnectBoard board = new ConnectBoard();
		Scoreboard score = new Scoreboard();
		Player[] player = new Player[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < 2; playerNumber++) {
			player[playerNumber] = ConnectConfig.IS_AI[playerNumber] ? new ConnectAI(playerNumber, (ConnectBoard) board)
							    							  : new ConnectHuman(playerNumber, scan);
		}

		ConnectGame game = new ConnectGame(board, score, player);
		game.play();
	}
}