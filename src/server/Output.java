package server;

public class Output {
	
	public static final String[] SYMBOL = {"X", "O"};
	public static final String EMPTY = " ";
	public static final String[] BOARD_TEXT = 
		     {" \\  / |",
			  "  \\/  |",
			  "  /\\  |",
			  " /  \\ |",
			  " /--\\ |",
			  " |  | |",
			  " |  | |",
			  " \\--/ |",
			  "      |",
			  "------+"};
	
	public static void printHumanMove(int p) {
		System.out.print(String.format("PLAYER %s - ENTER MOVE: ", SYMBOL[p]));
	}
	
	public static void score(int[] score) {
		for (int p = 0; p < Output.SYMBOL.length; p++) {
			System.out.print((p != 0) ? " , " : "");
			System.out.print("PLAYER " + Output.SYMBOL[p] + ": " + Integer.toString(score[p]));
		}
		System.out.println();
	}
	
	public static void moveMade(int p, String move) {
		System.out.println(String.format("MOVE MADE: PLAYER %s, COLUMN %s.", Output.SYMBOL[p], move));
	}
	
	public static void error(int i) {
		if (i == 0) {
			System.out.println("INVALID MOVE: COLUMN IS FULL.");
		} else if (i == 1) {
			System.out.println("INVALID CHOICE.");
		} else if (i == 2) {
			System.out.println("NUMBER OUT OF BOUNDS.");
		} else {
			System.out.println("UNKNOWN ERROR. PLEASE TRY AGAIN.");
		}
	}
	
	public static void playAgain() {
		System.out.print("PLAY AGAIN? (Y/N): ");
	}
}
