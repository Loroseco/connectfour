package framework;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public abstract class Human extends Player {
	
	public Human(int playerNumber) {
		super(playerNumber);
	}
	
	@Override
	public abstract String getMove();
}
