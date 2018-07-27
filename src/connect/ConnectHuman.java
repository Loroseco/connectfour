package connect;

import framework.Player;
import framework.TextOutput;
import framework.User;

public class ConnectHuman extends Player {
	
	public ConnectHuman(int playerNumber) {
		super(playerNumber);
	}
	
	@Override
	public String getMove() {
		TextOutput.HUMAN_MOVE.print(playerNumber);
		return User.getInput();
	}
}
