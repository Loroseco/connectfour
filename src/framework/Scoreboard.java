package framework;

/**
 * Class used to track number of games each player has won
 * @author Loroseco
 *
 */
public class Scoreboard {
	private int[] score;
	
	public Scoreboard() {
		this.score = new int[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < score.length; playerNumber++) {
			score[playerNumber] = 0;
		}
	}
	
	public void printAllScores() {
		for (int playerNumber = 0; playerNumber < Config.NO_OF_PLAYERS; playerNumber++) {
			System.out.println(score); //TODO: Proper output handling here
		}	
	}
	
	public void addScore(int winner) {
		for (int playerNumber = 0; playerNumber < Config.NO_OF_PLAYERS; playerNumber++) {
			if (playerNumber == winner) {
				score[playerNumber]++;
				break;
			}
		}
	}

}
