package client;

import java.util.Scanner;

import computer.Player;
import server.Output;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public class Human extends Player 
{
	
	private final Scanner scan;
	
	/**
	 * Human constructor
	 * @param symbol	Symbol used to play
	 * @param scan		Scanner used for human input
	 */
	public Human(int p, Scanner scan) 
	{
		super(p);
		this.scan = scan;
	}
	
	@Override
	public String getMove() 
	{
		Output.printHumanMove(p);
		return scan.next();
	}
}
