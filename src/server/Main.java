package server;
import java.util.Scanner;

/**
 * Connect 4 game. Play at your own peril. Please tell me if you manage to break it tia
 * @author Loroseco
 *
 */
class Main {
	/**
	 * Main function, used to initiate game.
	 */
	public static void main(String[] args) {
		Connect game = new Connect();
		boolean[] ai = {false, true};
		Scanner scan = new Scanner(System.in);
		game.play(7, 10, ai, scan);
		scan.close();
	}
}