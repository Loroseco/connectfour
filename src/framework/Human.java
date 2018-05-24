package framework;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public class Human extends Player {
	
	public Human(int playerNumber) {
		super(playerNumber);
	}
	
	@Override
	public String getMove() {
		TextOutput.HUMAN_MOVE.print(playerNumber);
		return User.getUserInput();
	}
}
