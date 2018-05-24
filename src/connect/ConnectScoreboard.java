package connect;

import framework.Scoreboard;

/**
 * Class used to track number of games each player has won
 * @author Loroseco
 *
 */
public class ConnectScoreboard extends Scoreboard {
	private int[] score;
	
	public ConnectScoreboard() {
		this.score = new int[ConnectConfig.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < score.length; playerNumber++) {
			score[playerNumber] = 0;
		}
	}
	
	@Override
	public void printAllScores() {
		for (int playerNumber = 0; playerNumber < ConnectConfig.NO_OF_PLAYERS; playerNumber++) {
			ConnectTextOutput.SCORE.println(playerNumber, Integer.toString(score[playerNumber]));
		}	
	}
	
	@Override
	public void addScore(int winner) {
		for (int playerNumber = 0; playerNumber < ConnectConfig.NO_OF_PLAYERS; playerNumber++) {
			if (playerNumber == winner) {
				score[playerNumber]++;
				break;
			}
		}
	}

}
