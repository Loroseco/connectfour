package server;

/**
 * Class used to track number of games each player has won
 * @author Loroseco
 *
 */
class Scoreboard 
{
	private int[] score;
	
	Scoreboard()
	{
		this.score = new int[Config.NO_OF_PLAYERS];
		for (int playerNumber = 0; playerNumber < score.length; playerNumber++) 
		{
			score[playerNumber] = 0;
		}
	}
	
	void printAllScores() 
	{
		for (int playerNumber = 0; playerNumber < Config.NO_OF_PLAYERS; playerNumber++)
		{
			TextOutput.SCORE.println(playerNumber, Integer.toString(score[playerNumber]));
		}	
	}
	
	void addScore(int winner) 
	{
		for (int playerNumber = 0; playerNumber < Config.NO_OF_PLAYERS; playerNumber++) 
		{
			if (playerNumber == winner) 
			{
				score[playerNumber]++;
				break;
			}
		}
	}

}
