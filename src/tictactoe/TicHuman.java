package tictactoe;

import framework.Player;
import framework.TextOutput;
import framework.User;

public class TicHuman extends Player {
	
	public TicHuman(int playerNumber) {
		super(playerNumber);
	}
	
	@Override
	public String getMove() {
		String[] playerMove = new String[2];
		
		TextOutput.HUMAN_MOVE.print(playerNumber);
		playerMove[0] = User.getInput();
		
		TextOutput.HUMAN_MOVE.print(playerNumber);
		playerMove[1] = User.getInput();
		
		return String.join("", playerMove);
		
	}
}
