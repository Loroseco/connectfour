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
	
	/**
	 * Fetches move
	 * @return	Column of move
	 */
	public abstract String getMove();

}