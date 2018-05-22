package server;

import java.util.Arrays;

/**
 * Board object. Handles Board reading and editing.
 * @author Loroseco
 *
 */
public class Board 
{
	private String[][] board;
	
	Board() 
	{
		this.board = new String[Config.NO_OF_ROWS][Config.NO_OF_COLS];
	}
	
	void set(int row, int col, int playerNumber) 
	{
		board[row][col] = Config.SYMBOLS[playerNumber];
	}
	
	public boolean isColumnFull(int col) 
	{
		return !isIndexEmpty(Config.NO_OF_ROWS - 1, col);
	}
	
	public boolean isBoardFull()
	{
		for (int col = 0; col < Config.NO_OF_COLS; col++) 
		{
			if (!isColumnFull(col)) 
			{
				return false; 
			}
		}
		return true;
	}
	
	public boolean isIndexEmpty(int row, int col) 
	{
		return board[row][col].equals(Config.EMPTY_SPACE);
	}
	
	public boolean isIndexEqual(int row, int col, int playerNumber)
	{
		return board[row][col].equals(Config.SYMBOLS[playerNumber]);
	}
	/**
	 * Creates a new board for a new game
	 */
	public void createBoard() 
	{
		for (int row = 0;  row < Config.NO_OF_ROWS; row++) 
		{
			for (int col = 0; col < Config.NO_OF_COLS; col++) 
			{
				board[row][col] = Config.EMPTY_SPACE;
			}
		}
		print();
	}
	
	/**
	 * Prints board in 2D array format
	 */
	void debugBoard()
	{
		System.out.println(Arrays.deepToString(board));
	}
	
	/**
	 * Prints board in format suitable for display
	 */
	void print()
	{
		debugBoard();
		for (int row = Config.NO_OF_ROWS - 1; row > -1; row--)
		{
			printBorder();	
			for (int rowSection = 0; rowSection < 4; rowSection++)
			{
				printRow(row, rowSection);
			}
		}
		printBottomKey();
	}
	
	private void printRow(int row, int rowSection)
	{
		printSide(row, rowSection);
		for (int col = 0; col < Config.NO_OF_COLS; col++)
		{
			if (board[row][col].equals(Config.SYMBOLS[0]))
			{
				BoardOutput.print(rowSection);
			}
			else if (board[row][col].equals(Config.SYMBOLS[1]))
			{
				BoardOutput.print(rowSection + 4);
			}
			else
			{
				BoardOutput.print(8);
			}
		}
		System.out.println();
	}
	
	private void printSide(int row, int rowSection)
	{
		String rowStr = "  ";
		if (rowSection == 1)
		{
			rowStr = Integer.toString(row);
			if (row < 10)
			{
				rowStr = " " + rowStr;
			}
		}
		BoardOutput.print(10, rowStr);
	}
	
	private void printBorder()
	{
		BoardOutput.printPartial(9);
		for (int col = 0; col < Config.NO_OF_COLS - 1; col++)
		{
			BoardOutput.print(9);
		}
		BoardOutput.println(9);
	}

	private void printBottomKey() 
	{
		printBorder();
		System.out.print("  |");
		for (int c = 0; c < Config.NO_OF_COLS; c++)
		{
			System.out.print(String.format("%s  %s  |", c < 10 ? " " : "", c));
		}
		System.out.println("\n");
	}
}