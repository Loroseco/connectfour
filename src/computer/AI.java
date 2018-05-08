package computer;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import server.Board;
import server.Config;

import java.math.BigInteger;

/**
 * Player class to be used by AI
 * @author Loroseco
 *
 */
public class AI extends Player 
{
	
	private final Board board;
	
	/**
	 * Opponent player number
	 */
	private final int q;
	
	/**
	 * A matrix representing the priorities of priorityMatrix, one entry per priorityMatrix row.		<br>
	 * Generated in the constructor every time to make changes to priorityRatings easier to manage.
	 */
	private final BigInteger[] priorityRatings;
	
	/**
	 * Priority of play for each column in the board, calculated by AI.									<br>
	 * Each priority is a magnitude of 10.
	 */
	private BigInteger[] columnPriorities;
	
	/**
	 * Constructor. 																					<br>
	 * Populates symbol and opponentSymbol, calculates priorityRatings
	 * @param symbolArray		Array of symbols, both the AI and the opponent
	 * @param playerNumber	Index of player symbol in symbol array
	 * @param board			Board object currently in play
	 */
	public AI(int playerNumber, Board board) 
	{
		super(playerNumber);
		this.board = board;
		
		q = playerNumber == 0 ? 1 : 0;
		priorityRatings = new BigInteger[AIConfig.PRIORITY_MATRIX.length];
		for (int p = 0; p < AIConfig.PRIORITY_MATRIX.length; p++) 
		{
			priorityRatings[p] = new BigInteger("10").pow(AIConfig.PRIORITY_MATRIX.length - (p + 1));
		}
	}
	
	@Override
	/**
	 * Fetches optimal move using priorityMatrix to assign priorites
	 * @return	move
	 */
	public String getMove() 
	{
		resetColumnPriorities();
		
		for (int row = 0; row < Config.ROW_N; row++) 
		{
			for (int col = 0; col < Config.COL_N; col++) 
			{
				addPriorityFromBoardIndex(row, col);
			}
		}
		
		return calculateReturnMove();
	}
	
	/**
	 * Creates an empty array for the AI to use to assign priorities to columns
	 */
	private void resetColumnPriorities() 
	{
		columnPriorities = new BigInteger[Config.COL_N];
		for (int col = 0; col < Config.COL_N; col++)
		{
			columnPriorities[col] =  new BigInteger("0");
		}
	}
	
	/**
	 * Calculates the priority given to a column by a specific index on the board
	 * @param row	Index row
	 * @param col	Index column
	 */
	private void addPriorityFromBoardIndex(int row, int col)
	{
		for (int priorityRow = 0; priorityRow < AIConfig.PRIORITY_MATRIX.length; priorityRow++)
		{
			for (int priorityCol = 0; priorityCol < AIConfig.PRIORITY_MATRIX[priorityRow].length; priorityCol++) 
			{
				if (AIConfig.PRIORITY_MATRIX[priorityRow][priorityCol].startsWith(AIConfig.VERTICAL.get())) 
				{
					addVerticalPriority(row, col, priorityRow, priorityCol);
				} 
				else 
				{
					addHorizontalPriority(row, col, priorityRow, priorityCol);
				}
			}
		}
	}
	
	/**
	 * Calculates if the index gives priorities from any vertical patterns
	 * @param row				Index row
	 * @param col				Index column
	 * @param priorityRow		Row of priorityMatrix being checked
	 * @param priorityString	String in priorityRow being checked
	 */
	private void addVerticalPriority(int row, int col, int priorityRow, int priorityCol) 
	{	
		String priorityString = AIConfig.PRIORITY_MATRIX[priorityRow][priorityCol];
		if (doesVerticalStringStartAtIndex(row, col, priorityString))
		{
			columnPriorities[col] = columnPriorities[col].add(priorityRatings[priorityRow]);
		}
	}
	
	/**
	 * Calculates if the index gives priorities from any horizontal patterns
	 * @param row				Index row
	 * @param col				Index column
	 * @param priorityRow		Row of priorityMatrix being checked
	 * @param priorityString	String in priorityRow being checked
	 */
	private void addHorizontalPriority(int row, int col, int priorityRow, int priorityCol) 
	{	
		String priorityString = AIConfig.PRIORITY_MATRIX[priorityRow][priorityCol];
		for (int gradient = -1; gradient < 2; gradient++)
		{
			if (doesHorizontalStringStartAtIndex(row, col, priorityString, gradient))
			{
				for (int i = 0; i < priorityString.length(); i++)
				{
					String letter = priorityString.substring(i, i + 1);
					if (letter.equals(AIConfig.VALID.get())
							|| (letter.equals(AIConfig.TWO.get()) && priorityString.contains(AIConfig.PLAYER.get()))) 
					{
						columnPriorities[col + i] = columnPriorities[col + i].add(priorityRatings[priorityRow]);
					} 
					else if (letter.equals(AIConfig.ONE.get())
							|| (letter.equals(AIConfig.TWO.get()) && priorityString.contains(AIConfig.OPPONENT.get()))) 
					{
						columnPriorities[col + i] = columnPriorities[col + i].add(priorityRatings[priorityRow].multiply(new BigInteger("-1")));
					}
				}
			}
		}
	}
	
	/**
	 * Checks specified part of board for vertical strings
	 * @param row		Row
	 * @param col		Column
	 * @param pattern	String to be searched for
	 * @return			Has string been found
	 */
	private boolean doesVerticalStringStartAtIndex(int row, int col, String pattern)
	{
		int counter = 0;
		try 
		{
			int n = Integer.parseInt(pattern.substring(2, 3));
			for (int i = 0; i < 4; i++) 
			{
				String s = pattern.substring(1, 2);
				if ((board.isIndexEqual(row + i, col, p) && s.equals(AIConfig.PLAYER.get()) && i < n) 
						|| (board.isIndexEqual(row + i, col, q) && s.equals(AIConfig.OPPONENT.get()) && i < n)
						|| (board.isIndexEmpty(row + 1, col) && i >= n)) 
				{
					counter++;
				} 
				else 
				{
					break;
				}
			}
			if (counter == 4) 
			{
				return true;
			}
		} catch(IndexOutOfBoundsException e) 
		{
			
		}
		return false;
	}
	
	/**
	 * Checks specified part of board for horizontal strings. 
	 * @param r			Row
	 * @param c			Column
	 * @param pattern	String to be searched for
	 * @param g			Gradient to be searched from start point
	 * @return			Has string been found
	 */
	private boolean doesHorizontalStringStartAtIndex(int r, int c, String pattern, int gradient) 
	{
		int counter = 0;
		try 
		{
			for (int i = 0; i < pattern.length(); i++) 
			{
				int row = r + (i * gradient);
				int col = c + i;
				
				String letter = pattern.substring(i, i + 1);
				if ((board.isIndexEqual(row, col, p) && letter.equals(AIConfig.PLAYER.get()))
						|| (board.isIndexEqual(row, col, q) && letter.equals(AIConfig.OPPONENT.get()))) 
				{
					counter++;
				} 
				else if (board.isIndexEmpty(row, col) 
						&& (letter.equals(AIConfig.ONE.get()) || letter.equals(AIConfig.TWO.get()) 
								|| letter.equals(AIConfig.NO_SCORE.get()) || letter.equals(AIConfig.VALID.get()))) 
				{
					int depth = findDepth(row, col, 0);
					if ((depth == 0 && (letter.equals(AIConfig.VALID.get()) || letter.equals(AIConfig.NO_SCORE.get())))
							|| Integer.toString(depth).equals(letter)) 
					{
						counter++;
					}
				} 
				else 
				{
					break;
				}
			} if (counter == pattern.length()) 
			{
				return true;
			}
		} catch (IndexOutOfBoundsException e) 
		{
			
		}
		return false;
	}
	
	/**
	 * Recursive function to find how far down the lowest valid move is in a column
	 * @param row	Starting row
	 * @param col	Starting column
	 * @param depth	Current depth
	 * @return		Depth
	 */
	private int findDepth(int row, int col, int depth) 
	{
		if (row == 0
				|| (board.isIndexEmpty(row, col)
						&& !board.isIndexEmpty(row - 1, col))) 
		{
			return depth;
		}
		else 
		{
			return findDepth(row - 1, col, depth + 1);
		}
	}
	
	/**
	 * Returns the largest value in columnPriorities, picks one at random if there is a tie
	 * @return	Column of optimal move
	 */
	private String calculateReturnMove() 
	{
		BigInteger returnPriority = null;
		ArrayList<Integer> returnMove = new ArrayList<>();
		for (int col = 0; col < columnPriorities.length; col++) 
		{
			if (board.isColumnFull(col)) 
			{
				continue;
			} 
			else if (returnPriority == null || columnPriorities[col].compareTo(returnPriority) >= 0) 
			{
				if (returnPriority == null || columnPriorities[col].compareTo(returnPriority) == 1) 
				{
					returnPriority = columnPriorities[col];
					returnMove = new ArrayList<>();
				}
				returnMove.add(col);
			}
		}
		
		System.out.println(Arrays.toString(columnPriorities));
		Random r = new Random();
		return Integer.toString(returnMove.get(r.nextInt(returnMove.size())));
	}
}