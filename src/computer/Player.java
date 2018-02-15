package computer;

/**
 * Parent player class
 * @author Loroseco
 *
 */
public abstract class Player {
	
	public String symbol;
	
	public Player(String symbol) {
		this.symbol = symbol;
	}

	public abstract String getMove();

}