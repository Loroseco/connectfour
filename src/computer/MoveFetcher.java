package computer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * Class used by AI to store move priorities per turn, and calculate the priority move
 * @author Loroseco
 *
 */
public class MoveFetcher {
	private String symbol;
	private String[][] board;
	private String[][] priorityMatrix;
	private BigInteger[] priorityRatings;
	private BigInteger[] columnPriorities;
	
	/**
	 * Class used by AI to store move priorities per turn, and calculate the priority move
	 * @param symbol			AI symbol
	 * @param board				State of board at move in quesion
	 * @param priorityMatrix	Matrix of priorities in order of importance, as specified in AI class
	 * @param priorityRatings	Ratings per row of priorityMatrix, calculated in AI class constructor
	 */
	MoveFetcher(String symbol, String[][] board, String[][] priorityMatrix, BigInteger[] priorityRatings) {
		this.symbol = symbol;
		this.board = board;
		this.priorityMatrix = priorityMatrix;
		this.columnPriorities = null;
		this.priorityRatings = priorityRatings;
		this.columnPriorities = new BigInteger[board[0].length];
		for (int col = 0; col < board[0].length; col++) {
			columnPriorities[col] = new BigInteger("0");
		}
	}
	
	/**
	 * Fetches optimal move from information given in constructor
	 * @return	move
	 */
	String getMove() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				addPriorityFromTableElement(row, col);
			}
		}
		
		return calculateReturnMove();
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
	 * Calculates the optimal move, once columnPriorities has been fully populated
	 * @return	move
	 */
	private String calculateReturnMove() {
		BigInteger returnPriority = null;
		ArrayList<Integer> returnMove = new ArrayList<Integer>();
		for (int col = 0; col < columnPriorities.length; col++) {
			if (board[board.length - 1][col] != " ") {
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
	
	/**
	 * Calculates if the element gives priorities from any vertical patterns
	 * @param row				Element row
	 * @param col				Element column
	 * @param priorityRow		Row of priorityMatrix being checked
	 * @param priorityString	String in priorityRow being checked
	 */
	private void addVerticalPriority(int row, int col, int priorityRow, String priorityString) {
		if (doesStringStartAtElement(row, col, priorityString, 2)) {
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
			if (doesStringStartAtElement(row, col, priorityString, gradient)) {
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
	 * Checks specified part of board for string. 
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @param g			Gradient to be searched from start point (2 for vertical)
	 * @return			Has string been found
	 */
	private boolean doesStringStartAtElement(int r, int c, String string, int g) {
		if ((g == 2 && doesVerticalStringStartAtElement(r, c, string))
				|| (g != 2 && doesHorizontalStringStartAtElement(r, c, string, g))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks vertical strings
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @return			Has string been found
	 */
	private boolean doesVerticalStringStartAtElement(int r, int c, String string) {
		int counter = 0;
		try {
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
		} catch (IndexOutOfBoundsException e) {
			
		}
		return false;
	}
	
	/**
	 * Checks horizontal strings. 
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @param g			Gradient to be searched from start point (2 for vertical)
	 * @return			Has string been found
	 */
	private boolean doesHorizontalStringStartAtElement(int r, int c, String string, int g) {
		int counter = 0;
		try {
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
					int depth = this.findDepth(r + (i * g), c + i, 0);
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
				|| (board[row][col].equals(" ") 
						&& !board[row - 1][col].equals(" "))) {
			return depth;
		} else {
			return this.findDepth(row - 1, col, depth + 1);
		}
	}
}