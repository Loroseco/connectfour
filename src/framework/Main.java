package framework;

import java.util.Scanner;

import server.Connect;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Connect game = new Connect(scan);
		
		game.play();
		scan.close();
	}
}