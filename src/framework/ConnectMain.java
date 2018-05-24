package framework;

import java.util.Scanner;

import server.ConnectGame;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class ConnectMain {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ConnectGame game = new ConnectGame(scan);
		
		game.play();
		scan.close();
	}
}