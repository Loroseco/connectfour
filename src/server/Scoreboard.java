package server;

/**
 * Class used to track number of games each player has won
 * @author Loroseco
 *
 */
class Scoreboard {
	private int[] score;
	private String[] symbol;
	
	/**
	 * Constructor, allows the scoreboard to potentially support more than two players
	 * @param symbol	Array of player symbols
	 */
	Scoreboard(String[] symbol) {
		this.symbol = symbol;
		this.score = new int[symbol.length];
		for (int p = 0; p < score.length; p++) {
			score[p] = 0;
		}
	}
	
	/**
	 * Prints all players' scores in format suitable for display
	 */
	void print() {
		for (int p = 0; p < symbol.length; p++) {
			System.out.print((p != 0) ? " , " : "");
			System.out.print("PLAYER " + symbol[p] + ": " + Integer.toString(score[p]));
		}
		System.out.println();		
	}
	
	/**
	 * Adds one point to the chosen player
	 * @param symbol	Chosen player symbol
	 */
	void add(String symbol) {
		for (int p = 0; p < this.symbol.length; p++) {
			if (this.symbol[p].equals(symbol)) {
				score[p]++;
				break;
			}
		}
	}

}
