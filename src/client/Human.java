package client;

import java.util.Scanner;

import server.Board;

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
		System.out.print("PLAYER " + symbol + " - ENTER MOVE: ");
		return scan.next();
	}

	@Override
	public String getMove(Board board) {
		return "n";
	}
}
