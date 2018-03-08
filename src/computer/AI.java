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
	
	private final Board board;
	private final String opponentSymbol;
	
	/**
	 * A matrix representing the priorities of priorityMatrix, one entry per priorityMatrix row.		<br>
	 * Generated in the constructor every time to make changes to priorityRatings easier to manage.
	 */
	private BigInteger[] priorityRatings;
	
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
	public AI(String[] symbolArray, int playerNumber, Board board) {
		super(symbolArray[playerNumber]);
		this.board = board;
		
		opponentSymbol = symbolArray[playerNumber == 0 ? 1 : 0];
		priorityRatings = new BigInteger[Config.PRIORITY_MATRIX.length];
		for (int p = 0; p < Config.PRIORITY_MATRIX.length; p++) {
			priorityRatings[p] = new BigInteger("10").pow(Config.PRIORITY_MATRIX.length - (p + 1));
		}
	}
	
	@Override
	/**
	 * Fetches optimal move using priorityMatrix to assign priorites
	 * @return	move
	 */
	public String getMove() {
		resetColumnPriorities();
		
		for (int row = 0; row < board.getRowN(); row++) {
			for (int col = 0; col < board.getColN(); col++) {
				addPriorityFromBoardIndex(row, col);
			}
		}
		
		return calculateReturnMove();
	}
	
	/**
	 * Creates an empty array for the AI to use to assign priorities to columns
	 */
	private void resetColumnPriorities() {
		columnPriorities = new BigInteger[board.getColN()];
		for (int col = 0; col < board.getColN(); col++) {
			columnPriorities[col] =  new BigInteger("0");
		}
	}
	
	/**
	 * Calculates the priority given to a column by a specific index on the board
	 * @param row	Index row
	 * @param col	Index column
	 */
	private void addPriorityFromBoardIndex(int row, int col) {
		for (int priorityRow = 0; priorityRow < Config.PRIORITY_MATRIX.length; priorityRow++) {
			for (int priorityCol = 0; priorityCol < Config.PRIORITY_MATRIX[priorityRow].length; priorityCol++) {
				String priorityString = Config.PRIORITY_MATRIX[priorityRow][priorityCol];
				if (priorityString.startsWith("V")) {
					addVerticalPriority(row, col, priorityRow, priorityString);
				} else {
					addHorizontalPriority(row, col, priorityRow, priorityString);
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
	private void addVerticalPriority(int row, int col, int priorityRow, String priorityString) {
		if (doesVerticalStringStartAtIndex(row, col, priorityString)) {
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
	private void addHorizontalPriority(int row, int col, int priorityRow, String priorityString) {
		for (int gradient = -1; gradient < 2; gradient++) {
			if (doesHorizontalStringStartAtIndex(row, col, priorityString, gradient)) {
				for (int i = 0; i < priorityString.length(); i++) {
					String letter = priorityString.substring(i, i + 1);
					if (letter.equals("_")
							|| (letter.equals("2") && priorityString.contains("P"))) {
						columnPriorities[col + i] = columnPriorities[col + i].add(priorityRatings[priorityRow]);
					} else if (letter.equals("1")
							|| (letter.equals("2") && priorityString.contains("Q"))) {
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
	private boolean doesVerticalStringStartAtIndex(int row, int col, String pattern) {
		int counter = 0;
		try {
			int n = Integer.parseInt(pattern.substring(2, 3));
			for (int i = 0; i < 4; i++) {
				String value = board.get(row + i, col);
				String s = pattern.substring(1, 2);
				if ((value.equals(symbol) && s.equals("P") && i < n) 
						|| (value.equals(opponentSymbol) && s.equals("Q") && i < n)
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
	 * @param row		Row
	 * @param col		Column
	 * @param pattern	String to be searched for
	 * @param g			Gradient to be searched from start point
	 * @return			Has string been found
	 */
	private boolean doesHorizontalStringStartAtIndex(int row, int col, String pattern, int gradient) {
		int counter = 0;
		try {
			for (int i = 0; i < pattern.length(); i++) {
				String s = pattern.substring(i, i + 1);
				String value = board.get(row + (i * gradient), col + i);
				if ((value.equals(symbol) && s.equals("P"))
						|| (value.equals(opponentSymbol) && s.equals("Q"))
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
			} if (counter == pattern.length()) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			
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
	 * Returns the largest value in columnPriorities, picks one at random if there is a tie
	 * @return	Column of optimal move
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