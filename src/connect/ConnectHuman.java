package connect;

import java.util.Scanner;

import framework.Player;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public class ConnectHuman extends Player {
	
	private final Scanner scan;
	
	public ConnectHuman(int playerNumber, Scanner scan) {
		super(playerNumber);
		this.scan = scan;
	}
	
	@Override
	public String getMove() {
		ConnectTextOutput.HUMAN_MOVE.print(playerNumber);
		return scan.next();
	}
}
