package client;

/**
 * Parent player class
 * @author Loroseco
 *
 */
public abstract class Player {
	
	String symbol;
	
	public Player(String symbol) {
		this.symbol = symbol;
	}

	public abstract String getMove(Object obj);

}