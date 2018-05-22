package client;

import java.util.Scanner;

import computer.Player;
import server.TextOutput;

/**
 * Human class to be used by player, handles player input from console
 * @author Loroseco
 *
 */
public class Human extends Player {
	
	private final Scanner scan;
	
	public Human(int playerNumber, Scanner scan) {
		super(playerNumber);
		this.scan = scan;
	}
	
	@Override
	public String getMove() {
		TextOutput.HUMAN_MOVE.print(playerNumber);
		return scan.next();
	}
}
