package server;

/**
 * Class used to track number of games each player has won
 * @author Loroseco
 *
 */
class Scoreboard 
{
	private int[] score;
	
	/**
	 * Constructor, allows the scoreboard to potentially support more than two players
	 * @param symbol	Array of player symbols
	 */
	Scoreboard()
	{
		this.score = new int[Output.SYMBOL.length];
		for (int p = 0; p < score.length; p++) 
		{
			score[p] = 0;
		}
	}
	
	/**
	 * Prints all players' scores in format suitable for display
	 */
	void print() 
	{
		Output.score(score);	
	}
	
	/**
	 * Adds one point to the chosen player
	 * @param winner	Chosen player symbol
	 */
	void add(int winner) 
	{
		for (int p = 0; p < Output.SYMBOL.length; p++) 
		{
			if (p == winner) 
			{
				score[p]++;
				break;
			}
		}
	}

}
