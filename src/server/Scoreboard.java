package server;

class Scoreboard {
	private int[] score;
	private String[] symbol;
	
	Scoreboard(String[] symbol) {
		this.symbol = symbol;
		this.score = new int[symbol.length];
		for (int p = 0; p < score.length; p++) {
			score[p] = 0;
		}
	}
	
	void print() {
		for (int p = 0; p < symbol.length; p++) {
			System.out.print((p != 0) ? " , " : "");
			System.out.print("PLAYER " + symbol[p] + ": " + Integer.toString(score[p]));
		}
		System.out.println();		
	}
	
	void addScore(String symbol) {
		for (int p = 0; p < this.symbol.length; p++) {
			if (this.symbol[p].equals(symbol)) {
				score[p]++;
				break;
			}
		}
	}

}
