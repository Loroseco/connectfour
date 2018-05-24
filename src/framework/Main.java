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
		ConnectConfig.configure();
		ConnectGame game = new ConnectGame(scan);
		game.play();
	}
}