package server;

import java.util.ArrayList;
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
		boolean[] isAI = {false, true};
		Connect game = new Connect(7, 10, isAI, scan);
		
		boolean end = false;
		while (!end) {
			ArrayList<Integer> moves = game.play();
			if (moves != null) {
				System.out.println(moves);
			}
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