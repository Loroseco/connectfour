package server;

import java.util.Scanner;

/**
 * Class containing main function
 * @author Loroseco
 *
 */
class Main 
{
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
				TextOutput.PLAY_AGAIN.print();
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
				TextOutput.ERROR_INVALID.println();
			}
		}
		scan.close();
	}
}