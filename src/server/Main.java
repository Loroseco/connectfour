package server;

import java.util.Scanner;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class Main {
	
	/**
	 * Main function, used to initiate game.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean[] isAI = {false, true}; // Change here to assign AI to certain players
		Connect game = new Connect(7, 10, isAI, scan);
		
		boolean playing = true;
		while (playing) {
			game.play();

			while (true) {
				System.out.print("PLAY AGAIN? (Y/N): ");
				String input = scan.next().toLowerCase();
				if (input.equals("y") || input.equals("yes")) {
					break;
				} 
				if (input.equals("n") || input.equals("no")) {
					playing = false;
					break;
				}
				System.out.println("INVALID CHOICE.");
			}
		}
		scan.close();
	}
}