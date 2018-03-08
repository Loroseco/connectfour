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
	private final int rowN;
	private final int colN;
	private String[] boardText = 
		{" \\  / |",
		 "  \\/  |",
		 "  /\\  |",
		 " /  \\ |",
		 " /--\\ |",
		 " |  | |",
		 " |  | |",
		 " \\--/ |",
		 "      |",
		 "------+"};
	
	/**
	 * Constructor. Makes the board 2D array using rowN x colN dimensions
	 * @param rowN	Number of rows
	 * @param colN	Number of columns
	 */
	Board(int rowN, int colN) 
	{
		this.rowN = rowN;
		this.colN = colN;
		this.board = new String[rowN][colN];
	}
	
	/**
	 * Accessor
	 * @return	Number of rows
	 */
	public int getRowN() 
	{
		return this.rowN;
	}
	
	/**
	 * Accessor
	 * @return	Number of columns
	 */
	public int getColN() 
	{
		return this.colN;
	}
	
	/**
	 * Board index mutator
	 * @param row	Row to be changed
	 * @param col	Column to be changed
	 * @param p		New value
	 */
	void set(int row, int col, int p) 
	{
		board[row][col] = Output.SYMBOL[p];
	}
	
	/**
	 * Checks if column is full
	 * @param col	Column to be checked
	 * @return		Is column full
	 */
	public boolean isColumnFull(int col) 
	{
		if (isIndexEmpty(rowN - 1, col)) {
			
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	/**
	 * Checks if board is full
	 * @return	True or false
	 */
	public boolean isBoardFull()
	{
		for (int c = 0; c < colN; c++) 
		{
			if (!isColumnFull(c)) 
			{
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * Checks if the index is empty
	 * @param row	Row to be checked
	 * @param col	Column to be checked
	 * @return		Is index empty
	 */
	public boolean isIndexEmpty(int row, int col) 
	{
		if (board[row][col].equals(Output.EMPTY)) 
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if indwx is equal to given value
	 * @param row	Row to be checked
	 * @param col	Column to be checked
	 * @param p		Symbol to be checked for
	 * @return		Does index contain the symbol
	 */
	public boolean isIndexEqual(int row, int col, int p)
	{
		if (board[row][col].equals(Output.SYMBOL[p]))
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	/**
	 * Creates a new board for a new game
	 */
	public void createBoard() 
	{
		for (int r = 0;  r < rowN; r++) 
		{
			for (int c = 0; c < colN; c++) 
			{
				board[r][c] = Output.EMPTY;
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
		
		System.out.println("\n");
		for (int row = rowN - 1; row > -2; row--)
		{
			printRow(row);
		}
		printBottomKey();
	}
	
	/**
	 * Prints the chosen for in format suitable for display
	 * @param row	Chosen row
	 */
	private void printRow(int row) 
	{
		System.out.print(boardText[9].substring(4, 7));
		for (int col = 0; col < colN; col++) 
		{
			System.out.print(boardText[9]);
		}
		if (row == -1)
		{
			return;
		}
		System.out.println();
		for (int i = 0; i < 4; i++) 
		{
			if (i == 1)
			{
				System.out.print(String.format("%s%s|", row < 10 ? " " : "", row, "|"));
			}
			else 
			{
				System.out.print("  |");
			}
			for (int col = 0; col < colN; col++)
			{
				if (board[row][col].equals("X")) 
				{
					System.out.print(boardText[i]);
				} 
				else if (board[row][col].equals("O")) 
				{
					System.out.print(boardText[i + 4]);
				} 
				else 
				{
					System.out.print(boardText[8]);
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the bottom key for the board in format suitable for display
	 */
	private void printBottomKey() 
	{
		System.out.print("\n  |");
		for (int c = 0; c < colN; c++)
		{
			System.out.print(String.format("%s  %s  |", c < 10 ? " " : "", c));
		}
		System.out.println("\n");
	}
}