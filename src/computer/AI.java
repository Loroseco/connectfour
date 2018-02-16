package computer;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import server.Board;

import java.math.BigInteger;

/**
 * Player class to be used by AI
 * @author Loroseco
 *
 */
public class AI extends Player {
	
	private Board board;
	
	/**
	 * A matrix representing the priorities of priorityMatrix, one entry per priorityMatrix row.					<br>
	 * Generated in the constructor every time to make changes to priorityRatings easier to manage.
	 */
	private BigInteger[] priorityRatings;
	
	/**
	 * Priority of play for each column in the board, calculated by AI.												<br>
	 * Each priority is a magnitude of 10.
	 */
	private BigInteger[] columnPriorities;
	
	/**
	 * priorityMatrix is all patterns the AI detects. the higher rows are higher priorities.						<br>
	 * O	Player symbol																							<br>
	 * X	Opponent symbol		NOTE: O in priorities is always the player symbol, and X is always the opponent.	<br>
	 * _	Valid move																								<br>
	 * 1	Empty space above a valid move																			<br>
	 * 2	Empty space above "1"																					<br>
	 * N	Valid move that is not assigned a priority from this string												<br>
	 * V	Vertical pattern. 2nd symbol is player symbol and 3rd is the height of the pattern.
	 */
	private String[][] priorityMatrix =
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
	 * Constructor. Populates priorityRatings, used to give ratings specific to each column in priorityMatrix
	 * @param symbol	Symbol the AI uses to play
	 */
	public AI(String symbol, Board board) {
		super(symbol);
		BigInteger[] priorityRatings = new BigInteger[priorityMatrix.length];
		for (int p = 0; p < priorityMatrix.length; p++) {
			priorityRatings[p] = new BigInteger("10").pow(priorityMatrix.length - (p + 1));
		}
		
		this.priorityRatings = priorityRatings;
		this.board = board;
	}
	
	@Override
	/**
	 * Fetches optimal move using priorityMatrix to assign priorites
	 * @return	move
	 */
	public String getMove() {
		resetColumnPrioritiesArray();
		
		for (int row = 0; row < board.getRowN(); row++) {
			for (int col = 0; col < board.getColN(); col++) {
				addPriorityFromTableElement(row, col);
			}
		}
		
		return calculateReturnMove();
	}
	
	/**
	 * Creates an empty array for the AI to use to assign priorities to columns
	 */
	private void resetColumnPrioritiesArray() {
		columnPriorities = new BigInteger[board.getColN()];
		for (int col = 0; col < board.getColN(); col++) {
			columnPriorities[col] =  new BigInteger("0");
		}
	}
	
	/**
	 * Calculates the priority given to a column by a specific element on the board
	 * @param row	Element row
	 * @param col	Element column
	 */
	private void addPriorityFromTableElement(int row, int col) {
		for (int priorityRow = 0; priorityRow < priorityMatrix.length; priorityRow++) {
			for (int priorityCol = 0; priorityCol < priorityMatrix[priorityRow].length; priorityCol++) {
				String priorityString = priorityMatrix[priorityRow][priorityCol];
				if (priorityString.startsWith("V")) {
					addVerticalPriority(row, col, priorityRow, priorityString);
				} else {
					addHorizontalPriority(row, col, priorityRow, priorityString);
				}
			}
		}
	}
	
	/**
	 * Calculates if the element gives priorities from any vertical patterns
	 * @param row				Element row
	 * @param col				Element column
	 * @param priorityRow		Row of priorityMatrix being checked
	 * @param priorityString	String in priorityRow being checked
	 */
	private void addVerticalPriority(int row, int col, int priorityRow, String priorityString) {
		if (doesVerticalStringStartAtElement(row, col, priorityString)) {
			columnPriorities[col] = columnPriorities[col].add(priorityRatings[priorityRow]);
		}
	}
	
	/**
	 * Calculates if the element gives priorities from any horizontal patterns
	 * @param row				Element row
	 * @param col				Element column
	 * @param priorityRow		Row of priorityMatrix being checked
	 * @param priorityString	String in priorityRow being checked
	 */
	private void addHorizontalPriority(int row, int col, int priorityRow, String priorityString) {
		for (int gradient = -1; gradient < 2; gradient++) {
			if (doesHorizontalStringStartAtElement(row, col, priorityString, gradient)) {
				for (int i = 0; i < priorityString.length(); i++) {
					String letter = priorityString.substring(i, i + 1);
					if (letter.equals("_")
							|| (letter.equals("2") && priorityString.contains("O"))) {
						columnPriorities[col + i] = columnPriorities[col + i].add(priorityRatings[priorityRow]);
					} else if (letter.equals("1")
							|| (letter.equals("2") && priorityString.contains("X"))) {
						columnPriorities[col + i] = columnPriorities[col + i].add(priorityRatings[priorityRow].multiply(new BigInteger("-1")));
					}
				}
			}
		}
	}
	
	/**
	 * Checks specified part of board for vertical strings
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @return			Has string been found
	 */
	private boolean doesVerticalStringStartAtElement(int row, int col, String string) {
		int counter = 0;
		try {
			int n = Integer.parseInt(string.substring(2, 3));
			for (int i = 0; i < 4; i++) {
				String value = board.get(row + i, col);
				String s = string.substring(1, 2);
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
		} catch(IndexOutOfBoundsException e) {
			
		}
		return false;
	}
	
	/**
	 * Checks specified part of board for horizontal strings. 
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @param g			Gradient to be searched from start point (2 for vertical)
	 * @return			Has string been found
	 */
	private boolean doesHorizontalStringStartAtElement(int row, int col, String string, int gradient) {
		int counter = 0;
		try {
			for (int i = 0; i < string.length(); i++) {
				String s = string.substring(i, i + 1);
				String value = board.get(row + (i * gradient), col + i);
				if ((symbol.equals("O") && value.equals(s))
						|| (symbol.equals("X") && "XO".contains(value)
								&& "XO".contains(s) && !value.equals(s))
						|| (value.equals(" ") && s.equals("E"))) {
					counter++;
				} else if (value.equals(" ") && "123456789_N".contains(s)) {
					int depth = findDepth(row + (i * gradient), col + i, 0);
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
		} catch (IndexOutOfBoundsException e) {
			
		}
		return false;
	}
	
	/**
	 * Recursive function to find how far down the lowest valid move is in a column
	 * @param board	Board to be searched
	 * @param row	Starting row
	 * @param col	Starting column
	 * @param depth	Current depth
	 * @return		Depth
	 */
	private int findDepth(int row, int col, int depth) {
		if (row == 0
				|| (board.isEqual(row, col, " ")
						&& !board.isEqual(row - 1, col, " "))) {
			return depth;
		} else {
			return findDepth(row - 1, col, depth + 1);
		}
	}
	
	/**
	 * Calculates the optimal move, once columnPriorities has been fully populated
	 * @return	move
	 */
	private String calculateReturnMove() {
		BigInteger returnPriority = null;
		ArrayList<Integer> returnMove = new ArrayList<Integer>();
		for (int col = 0; col < columnPriorities.length; col++) {
			if (board.isColumnFull(col)) {
				continue;
			} else if (returnPriority == null || columnPriorities[col].compareTo(returnPriority) >= 0) {
				if (returnPriority == null || columnPriorities[col].compareTo(returnPriority) == 1) {
					returnPriority = columnPriorities[col];
					returnMove = new ArrayList<Integer>();
				}
				returnMove.add(col);
			}
		}
		
		System.out.println(Arrays.toString(columnPriorities));
		Random r = new Random();
		return Integer.toString(returnMove.get(r.nextInt(returnMove.size())));
	}
}