package computer;

/**
 * Parent player class
 * @author Loroseco
 *
 */
public abstract class Player 
{
	public int playerNumber;
	
	public Player(int playerNumber)
	{
		this.playerNumber = playerNumber;
	}
	
	public abstract String getMove();

}