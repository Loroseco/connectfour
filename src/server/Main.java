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
		Scanner scan = new Scanner(System.in);
		boolean end = false;
		while (!end) {
			Connect game = new Connect();
			boolean[] isAI = {false, true};
			game.play(7, 10, isAI, scan);
			while (true) {
				System.out.print("PLAY AGAIN? (Y/N): ");
				String userInput = scan.next();
				String inputLower = userInput.toLowerCase();
				if (inputLower.equals("y") || inputLower.equals("yes")) {
					break;
				} else if (inputLower.equals("n") || inputLower.equals("no")) {
					end = true;
					break;
				} else {
					System.out.println("INVALID CHOICE");
				}
			}
		}
		scan.close();
	}
}