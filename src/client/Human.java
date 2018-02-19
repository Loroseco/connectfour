package client;

import java.util.Scanner;

import computer.Player;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public class Human extends Player {
	
	private Scanner scan;
	
	/**
	 * Human constructor
	 * @param symbol	Symbol used to play
	 */
	public Human(String symbol, Scanner scan) {
		super(symbol);
		this.scan = scan;
	}
	
	@Override
	public String getMove() {
		System.out.print(String.format("PLAYER %s - ENTER MOVE: ", symbol));
		return scan.next();
	}
}
