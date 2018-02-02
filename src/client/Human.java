package client;

import java.util.Scanner;

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
