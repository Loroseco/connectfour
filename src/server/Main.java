package server;

import java.util.Scanner;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class Main 
{
	
	/**
	 * Main function, used to initiate game.
	 */
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		Connect game = new Connect(scan);
		
		boolean playing = true;
		while (playing) 
		{
			game.play();
			
			while (true) 
			{
				Output.playAgain();
				String input = scan.next().toLowerCase();
				if (input.equals("y") || input.equals("yes")) 
				{
					break;
				} 
				if (input.equals("n") || input.equals("no")) 
				{
					playing = false;
					break;
				}
				Output.error(1);
			}
		}
		scan.close();
	}
}