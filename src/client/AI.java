/*
 * AI
 * 
 * Version 2.2
 */
package client;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.math.BigInteger;

/**
 * Player class to be used by AI
 * @author Loroseco
 *
 */
public class AI extends Player {
	
	private BigInteger[] priorityRatings;
	
	/* priorities matrix is all patterns the AI detects. the higher rows are higher priorities.
	 * O	Player symbol
	 * X	Opponent symbol		NOTE: O in priorities is always the player symbol, and X is always the opponent.
	 * _	Valid move
	 * 1	Empty space above a valid move
	 * 2	Empty space above "1"
	 * N	Valid move that is not assigned a priority from this string
	 * V	Vertical pattern. 2nd symbol is player symbol and 3rd is the height of the pattern.
	 */
	private String[][] priorities =
		{{"O_OO", "OO_O", "OOO_", "_OOO", "VO3"},
		 {"X_XX", "XX_X", "XXX_", "_XXX", "VX3"},
		 {"X1XX", "XX1X", "XXX1", "1XXX"},
		 {"N_OON", "NO_ON", "NOO_N"},
		 {"N_XXN", "NX_XN", "NXX_N"},
		 {"N1XXN", "NX1XN", "NXX1N", "1XXNN", "1NXXN", "1XNXN", "1NXNX", "NXXN1", "NNXX1", "NXNX1", "XNXN1"},
		 {"O2OO", "OO2O", "OOO2", "2OOO"},
		 {"O1OO", "OO1O", "OOO1", "1OOO"},
		 {"X2XX", "XX2X", "XXX2", "2XXX"},
		 {"N2OON", "NO2ON", "NOO2N", "2OONN", "2NOON", "2ONON", "2NONO", "NOON2", "NNOO2", "NONO2", "ONON2"},
		 {"N1OON", "NO1ON", "NOO1N", "1OONN", "1NOON", "1ONON", "1NONO", "NOON1", "NNOO1", "NONO1", "ONON1"},
		 {"N2XXN", "NX2XN", "NXX2N", "2XXNN", "2NXXN", "2XNXN", "2NXNX", "NXXN2", "NNXX2", "NXNX2", "XNXN2"},
		 {"OO__", "O_O_", "O__O", "_O_O", "__OO", "_OO_", "VO2"},
		 {"XX__", "X_X_", "X__X", "_X_X", "__XX", "_XX_", "VX2"},
		 {"OO1_", "O1O_", "O1_O", "1O_O", "1_OO", "1OO_", "OO_1", "O_O1", "O_1O", "_O1O", "_1OO", "_OO1"},
		 {"XX1_", "X1X_", "X1_X", "1X_X", "1_XX", "1XX_", "XX_1", "X_X1", "X_1X", "_X1X", "_1XX", "_XX1"},
		 {"OO11", "O1O1", "O11O", "1O1O", "11OO", "1OO1"},
		 {"XX11", "X1X1", "X11X", "1X1X", "11XX", "1XX1"},
		 {"O___", "___O", "__O_", "_O__", "VO1"},
		 {"X___", "___X", "__X_", "_X__", "VX1"},
		 {"O_1_", "_1_O", "_1O_", "_O1_", "O__1", "__1O", "__O1", "_O_1", "O1__", "1__O", "1_O_", "1O__"},
		 {"X_1_", "_1_X", "_1X_", "_X1_", "X__1", "__1X", "__X1", "_X_1", "X1__", "1__X", "1_X_", "1X__"},
		 {"O11_", "11_O", "11O_", "1O1_", "O1_1", "1_1O", "1_O1", "1O_1", "O_11", "_11O", "_1O1", "_O11"},
		 {"X11_", "11_X", "11X_", "1X1_", "X_11", "_11X", "_1X1", "_X11", "X1_1", "1_1X", "1_X1", "1X_1"},                          
		 {"O111", "111O", "11O1", "1O11"},
		 {"X111", "111X", "11X1", "1X11"}
		};
	
	/**
	 * Constructor. Populates "strings" (list of patterns AI searches for in the 
	 * board)
	 * @param symbol	Symbol the AI uses to play
	 */
	public AI(String symbol) {
		super(symbol);
		BigInteger[] priorityRatings = new BigInteger[this.priorities.length];
		for (int p = 0; p < this.priorities.length; p++) {
			priorityRatings[p] = new BigInteger("10").pow(this.priorities.length - (p + 1));
		}
		this.priorityRatings = priorityRatings;
	}
	
	/**
	 * Recursive function to find how far down the lowest valid move is in a column
	 * @param board	Board to be searched
	 * @param row	Starting row
	 * @param col	Starting column
	 * @param depth	Current depth
	 * @return		Depth
	 */
	private int findDepth(String[][] board, int row, int col, int depth) {
		if (row == 0 
				|| (board[row][col].equals(" ") 
						&& !board[row - 1][col].equals(" "))) {
			return depth;
		} else {
			return this.findDepth(board, row - 1, col, depth + 1);
		}
	}
	
	/**
	 * Checks specified part of board for string. 
	 * @param board		Board
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @param g			Gradient to be searched from start point (2 for vertical)
	 * @return			Has string been found, true or false
	 */
	private boolean searchBoard(String[][] board, int r, int c, String string, int g) {
		
		/* Always reads "O" as its own symbol (so sees "X" in board and "O" in pattern as the same if this.symbol is "X") */
		
		try {
			int counter = 0;
			if (g == 2) {
				
				/* Handles vertical patterns */
				int n = Integer.parseInt(string.substring(2, 3));
				for (int i = 0; i < 4; i++) {
					String value = board[r + i][c];
					String s = string.substring(1,2);
					if ((symbol.equals("O") && value.equals(s) && i < n) 
							|| (symbol.equals("X") && "XO".contains(value) 
									&& "XO".contains(s) && !value.equals(s) && i < n)
							|| (value.equals(" ") && i >= n)) {
						counter++;
					} else {
						break;
					}
				}
				if (counter == 4) {
					return true;
				}
			} else {
				
				/* Handles non-vertical patterns */
				for (int i = 0; i < string.length(); i++)
				{
					String s = string.substring(i, i + 1);
					String value = board[r + (i * g)][c + i];
					if ((symbol.equals("O") && value.equals(s))
							|| (symbol.equals("X") && "XO".contains(value) 
									&& "XO".contains(s) && !value.equals(s))
							|| (value.equals(" ") && s.equals("E")))
					{
						counter++;
						
					} else if (value.equals(" ") && "123456789_N".contains(s)) {
						int depth = this.findDepth(board,  r + (i * g), c + i, 0);
						if ((depth == 0 && "_N".contains(s)) 
								|| Integer.toString(depth).equals(s)) {
							counter++;
						}
					} else {
						break;
					}
				} if (counter == string.length()) {
					return true;
				}
			}
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		return false;
	}
	
	@Override
	public String getMove(String[][] board) {
		
		/* Creates list with one value representing each column */
		BigInteger[] priorityList = new BigInteger[board[0].length];
		for (int i = 0; i < priorityList.length; i++) {
			priorityList[i] = new BigInteger("0");
		}
		
		/*
		 * Calculates relevant priorities.
		 * Priorities are assigned in powers of 10. The lowest priority is 1, next lowest is 10, then 100, 1000, etc.
		 * 
		 * X, O and N result in no priorities given to the chosen column
		 * _ and V add the priority to the chosen column
		 * 1 adds the negative of the priority to the chosen column
		 * 2 adds the positive for O, negative for X.
		 */
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				for (int priority = 0; priority < priorities.length; priority++) {
					for (int string = 0; string < priorities[priority].length; string++) {
						
						/* Adds the priority columns where a vertical pattern occurs */
						if (priorities[priority][string].startsWith("V")) {
							if (this.searchBoard(board, row, col, priorities[priority][string], 2)) {
								priorityList[col] = priorityList[col].add(priorityRatings[priority]);
							}
						}
						
						/* 
						 * Handles non-vertical patterns.
						 * Iterates through each position in the pattern, adding priorities to the corresponding columns. 
						 */
						else {
							for (int gradient = -1; gradient < 2; gradient++) {
								String pattern = priorities[priority][string];
								if (this.searchBoard(board, row, col, pattern, gradient)) {
									for (int i = 0; i < pattern.length(); i++) {
										String letter = pattern.substring(i, i + 1);
										if (letter.equals("_") 
												|| (letter.equals("2") && pattern.contains("O"))) {
											priorityList[col + i] = priorityList[col + i].add(priorityRatings[priority]);
										} else if (letter.equals("1") 
												|| (letter.equals("2") && pattern.contains("X"))) {
											priorityList[col + i] = priorityList[col + i].add(priorityRatings[priority].multiply(new BigInteger("-1")));
										}
									}
								}
							}
						}
					}
				}
			}
		}

		/* Finds largest priority from lstPr to return as move. Choose at random from highest if there is a tie. */
		BigInteger returnPriority = null;
		ArrayList<Integer> returnMove = new ArrayList<Integer>();
		for (int c = 0; c < priorityList.length; c++) {
			if (board[board.length - 1][c] != " ") {
				continue;
			}
			if (returnPriority == null || priorityList[c].compareTo(returnPriority) >= 0) {
				if (returnPriority == null || priorityList[c].compareTo(returnPriority) == 1) {
					returnPriority = priorityList[c];
					returnMove = new ArrayList<Integer>();
				}
				returnMove.add(c);
			}
		}
		
		System.out.println(Arrays.toString(priorityList));
		Random r = new Random();
		return Integer.toString(returnMove.get(r.nextInt(returnMove.size())));
	}
}