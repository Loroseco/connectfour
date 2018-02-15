package client;

import java.util.Scanner;

import computer.Player;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public class Human extends Player {
	
	/**
	 * Human constructor
	 * @param symbol	Symbol used to play
	 */
	public Human(String symbol) {
		super(symbol);
	}
	
	@Override
	public String getMove(Object scanObj) {
		Scanner scan = (Scanner) scanObj;
		System.out.print("PLAYER " + symbol + " - ENTER MOVE: ");
		return scan.next();
	}
}
