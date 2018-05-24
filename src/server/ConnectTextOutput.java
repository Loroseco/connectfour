package server;

/**
 * enum that stores all console output for the game.
 * @author Loroseco
 *
 */
public enum ConnectTextOutput {
	HUMAN_MOVE("PLAYER %s - ENTER MOVE: "),
	SCORE("PLAYER %s SCORE: %s"),
	MOVE_MADE("MOVE MADE: PLAYER %s, COLUMN %s."),
	PLAY_AGAIN("PLAY AGAIN? (Y/N): "),
	WINNER("WINNER: PLAYER %s."),
	DRAW("GAME OVER: DRAW."),
	ERROR_COLUMN_FULL("INVALID MOVE: COLUMN IS FULL."),
	ERROR_INVALID("INVALID CHOICE."),
	ERROR_OUT_OF_BOUNDS("NUMBER OUT OF BOUNDS."),
	ERROR_UNKNOWN("UNKNOWN ERROR. PLEASE TRY AGAIN.");
	
	private String message;
	
	ConnectTextOutput(String message) {
		this.message = message;
	}
	
	public void print() {
		System.out.print(message);
	}
	
	public void print(int p) {
		System.out.print(String.format(message, ConnectConfig.SYMBOLS[p]));
	}
	
	public void print(int p, String col) {
		System.out.print(String.format(message, ConnectConfig.SYMBOLS[p], col));
	}
	
	public void println() {
		System.out.println(message);
	}
	
	public void println(int p) {
		System.out.println(String.format(message, ConnectConfig.SYMBOLS[p]));
	}
	
	public void println(int p, String col) {
		System.out.println(String.format(message, ConnectConfig.SYMBOLS[p], col));
	}
}
