package server;

import java.util.ArrayList;
import java.util.Scanner;

import client.*;
import computer.*;
/**
 * Class used to handle functions specific to Connect 4 (win condition, and the game loop itself).
 * @author Loroseco
 *
 */
class Connect 
{
	private final Board board;
	
	/**
	 * Arraylist of moves played by both players, in order
	 */
	private final ArrayList<Integer> moves;
	private final Scoreboard score;
	private final Player[] player;
	
	
	/**
	 * Main game constructor
	 * @param rowN	Number of rows
	 * @param colN	Number of columns
	 * @param isAI	Array showing which players are AI
	 * @param scan	Scanner for player input
	 */
	Connect(Scanner scan) 
	{
		this.board = new Board();
		this.moves = new ArrayList<Integer>();
		this.score = new Scoreboard();
		this.player = new Player[2];
		
		for (int p = 0; p < 2; p++) 
		{
			player[p] = Config.IS_AI[p] ? new AI(p, board)
							    : new Human(p, scan);
		}
	}
	
	/**
	 * Main game loop
	 */
	void play() 
	{
		board.createBoard();
		moves.clear();

		int winner = 3;
		int playerNumber = 1;
		while (winner == 3) 
		{
			playerNumber = playerNumber == 1 ? 0 : 1;
			winner = playTurn(playerNumber);
		}
		
		if (winner == 0 || winner == 1) 
		{
			System.out.println(String.format("WINNER: PLAYER %s.", winner));
			score.add(winner);
			printMoves(winner);
		} 
		else 
		{
			System.out.println("GAME OVER: DRAW.");
		}
		score.print();
		
	}
	
	/**
	 * Plays one turn of the game
	 * @param p	Player number
	 * @return	Symbol of winner, "n" for draw or "" for not finished
	 */
	private int playTurn(int p)
	{
		while (true) 
		{
			if (board.isBoardFull()) 
			{
				return 2;
			} 
			else
			{
				String move = player[p].getMove();
				if (makeMove(move, p)) 
				{
					if (Config.IS_AI[p]) 
					{
						board.print();
					}
					moves.add(Integer.parseInt(move));
					Output.MOVE_MADE.println(p, move);
					break;
				}
			}
		}
		int winner = findWinner();
		if (winner == p && !Config.IS_AI[p]) 
		{
			board.print();
		}
		return winner;
	}
	
	/**
	 * @param moveString	Move given by player
	 * @param symbol		Move symbol
	 * @return				Success output (to be printed for player)
	 */
	boolean makeMove(String moveString, int p) 
	{
		try 
		{
			int move = Integer.parseInt(moveString);
			if (board.isColumnFull(move)) 
			{
				Output.EXCEPTION_COLUMN_FULL.println();
				return false;
			} 
			else 
			{
				for (int row = 0; row < Config.ROW_N; row++) 
				{
					if (board.isIndexEmpty(row, move)) 
					{
						board.set(row, move, p);
						return true;
					}
				}
			}
		} 
		catch (NumberFormatException e) 
		{
			Output.EXCEPTION_INVALID.println();
			return false;
		} 
		catch (IndexOutOfBoundsException e) 
		{
			Output.EXCEPTION_OUT_OF_BOUNDS.println();
			return false;
		}
		Output.EXCEPTION_UNKNOWN.println();
		return false;
	}
	
	/**
	 * Checks if either player has won
	 * @return	Symbol of winner, "" if none
	 */
	private int findWinner() 
	{
		g:
		for (int g = -1; g < 3; g++) 
		{
			r:
			for (int r = 0; r < Config.ROW_N; r++)
			{
				c:
				for (int c = 0; c < Config.COL_N; c++) 
				{
					int[] counter = {0, 0};
					try 
					{
						int row;
						int col;
						for (int i = 0; i < 4; i++) 
						{
							if (g == 2) 
							{
								row = r + i;
								col = c;
							}
							else 
							{
								row = r + (i * g);
								col = c + i;
							}
							for (int p = 0; p < Config.PLAYER_N; p++) 
							{
								if (board.isIndexEqual(row, col, p)) 
								{
									counter[p]++;
								}
							}
						}
						for (int p = 0; p < counter.length; p++) 
						{
							if (counter[p] == 4) 
							{
								return p;
							}
						}
						continue c;
					} catch(IndexOutOfBoundsException e) 
					{
						if (g == 2) 
						{
							break g;
						} 
						else 
						{
							continue r;
						}
					}
				}
			}
		}
		return 3;
	}
	
	/**
	 * Prints moves arraylist if AI loses a game, for analysis purposes
	 * @param winner	Winning symbol
	 */
	private void printMoves(int winner)
	{
		for (int p = 0; p < Config.PLAYER_N; p++) {
			if (winner != p && Config.IS_AI[p])
			{
				System.out.println(moves);
				break;
			}
		}
	}
}