package connect;

/**
 * Class used to track number of games each player has won
 * @author Loroseco
 *
 */
class ConnectScoreboard {
	private int[] score;
	
	ConnectScoreboard() {
		this.score = new int[ConnectConfig.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < score.length; playerNumber++) {
			score[playerNumber] = 0;
		}
	}
	
	void printAllScores() {
		for (int playerNumber = 0; playerNumber < ConnectConfig.NO_OF_PLAYERS; playerNumber++) {
			ConnectTextOutput.SCORE.println(playerNumber, Integer.toString(score[playerNumber]));
		}	
	}
	
	void addScore(int winner) {
		for (int playerNumber = 0; playerNumber < ConnectConfig.NO_OF_PLAYERS; playerNumber++) {
			if (playerNumber == winner) {
				score[playerNumber]++;
				break;
			}
		}
	}

}
