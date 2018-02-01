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
	public String getMove(Scanner scan) {
		System.out.print("PLAYER " + getSymbol() + " - ENTER MOVE: ");
		return scan.next();
	}

	@Override
	public String getMove(String[][] board) {
		return "n";
	}
}
