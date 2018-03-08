package computer;

/**
 * Parent player class
 * @author Loroseco
 *
 */
public abstract class Player 
{
	
	/**
	 * Player number
	 */
	public int p;
	
	public Player(int p)
	{
		this.p = p;
	}
	
	/**
	 * Fetches move
	 * @return	Column of move
	 */
	public abstract String getMove();

}