package server;

public enum Output {
	HUMAN_MOVE("PLAYER %s - ENTER MOVE: "),
	SCORE("PLAYER %s SCORE: %s"),
	MOVE_MADE("MOVE MADE: PLAYER %s, COLUMN %s."),
	PLAY_AGAIN("PLAY AGAIN? (Y/N): "),
	EXCEPTION_COLUMN_FULL("INVALID MOVE: COLUMN IS FULL."),
	EXCEPTION_INVALID("INVALID CHOICE."),
	EXCEPTION_OUT_OF_BOUNDS("NUMBER OUT OF BOUNDS."),
	EXCEPTION_UNKNOWN("UNKNOWN ERROR. PLEASE TRY AGAIN.");
	
	private String message;
	
	Output(String message)
	{
		this.message = message;
	}
	
	public void print()
	{
		System.out.print(message);
	}
	
	public void print(int p)
	{
		System.out.print(String.format(message, Config.SYMBOLS[p]));
	}
	
	public void print(int p, String arg)
	{
		System.out.print(String.format(message, Config.SYMBOLS[p], arg));
	}
	
	public void println()
	{
		System.out.println(message);
	}
	
	public void println(int p)
	{
		System.out.println(String.format(message, Config.SYMBOLS[p]));
	}
	
	public void println(int p, String arg)
	{
		System.out.println(String.format(message, Config.SYMBOLS[p], arg));
	}
}
