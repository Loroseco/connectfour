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
	
	private String opponentSymbol;
	
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
	 * priorityMatrix is all patterns the AI detects. the higher rows are higher priorities.			<br>
	 * P	AI symbol																					<br>
	 * Q	Opponent symbol																				<br>
	 * _	Valid move																					<br>
	 * 1	Empty space above a valid move																<br>
	 * 2	Empty space above "1"																		<br>
	 * N	Valid move that is not assigned a priority from this string									<br>
	 * V	Vertical pattern. 2nd symbol is player symbol and 3rd is the height of the pattern.
	 */
	private String[][] priorityMatrix =
		{{"P_PP", "PP_P", "PPP_", "_PPP", "VP3"},
		 {"Q_QQ", "QQ_Q", "QQQ_", "_QQQ", "VQ3"},
		 {"Q1QQ", "QQ1Q", "QQQ1", "1QQQ"},
		 {"N_PPN", "NP_PN", "NPP_N"},
		 {"N_QQN", "NQ_QN", "NQQ_N"},
		 {"N1QQN", "NQ1QN", "NQQ1N", "1QQNN", "1NQQN", "1QNQN", "1NQNQ", "NQQN1", "NNQQ1", "NQNQ1", "QNQN1"},
		 {"P2PP", "PP2P", "PPP2", "2PPP"},
		 {"P1PP", "PP1P", "PPP1", "1PPP"},
		 {"Q2QQ", "QQ2Q", "QQQ2", "2QQQ"},
		 {"N2PPN", "NP2PN", "NPP2N", "2PPNN", "2NPPN", "2PNPN", "2NPNP", "NPPN2", "NNPP2", "NPNP2", "PNPN2"},
		 {"N1PPN", "NP1PN", "NPP1N", "1PPNN", "1NPPN", "1PNPN", "1NPNP", "NPPN1", "NNPP1", "NPNP1", "PNPN1"},
		 {"N2QQN", "NQ2QN", "NQQ2N", "2QQNN", "2NQQN", "2QNQN", "2NQNQ", "NQQN2", "NNQQ2", "NQNQ2", "QNQN2"},
		 {"PP__", "P_P_", "P__P", "_P_P", "__PP", "_PP_", "VP2"},
		 {"QQ__", "Q_Q_", "Q__Q", "_Q_Q", "__QQ", "_QQ_", "VQ2"},
		 {"PP1_", "P1P_", "P1_P", "1P_P", "1_PP", "1PP_", "PP_1", "P_P1", "P_1P", "_P1P", "_1PP", "_PP1"},
		 {"QQ1_", "Q1Q_", "Q1_Q", "1Q_Q", "1_QQ", "1QQ_", "QQ_1", "Q_Q1", "Q_1Q", "_Q1Q", "_1QQ", "_QQ1"},
		 {"PP11", "P1P1", "P11P", "1P1P", "11PP", "1PP1"},
		 {"QQ11", "Q1Q1", "Q11Q", "1Q1Q", "11QQ", "1QQ1"},
		 {"P___", "___P", "__P_", "_P__", "VP1"},
		 {"Q___", "___Q", "__Q_", "_Q__", "VQ1"},
		 {"P_1_", "_1_P", "_1P_", "_P1_", "P__1", "__1P", "__P1", "_P_1", "P1__", "1__P", "1_P_", "1P__"},
		 {"Q_1_", "_1_Q", "_1Q_", "_Q1_", "Q__1", "__1Q", "__Q1", "_Q_1", "Q1__", "1__Q", "1_Q_", "1Q__"},
		 {"P11_", "11_P", "11P_", "1P1_", "P1_1", "1_1P", "1_P1", "1P_1", "P_11", "_11P", "_1P1", "_P11"},
		 {"Q11_", "11_Q", "11Q_", "1Q1_", "Q_11", "_11Q", "_1Q1", "_Q11", "Q1_1", "1_1Q", "1_Q1", "1Q_1"},                          
		 {"P111", "111P", "11P1", "1P11"},
		 {"Q111", "111Q", "11Q1", "1Q11"}
		};
	
	/**
	 * Constructor. 																					<br>
	 * Populates symbol and opponentSymbol, calculates priorityRatings
	 * @param symbol	Symbol the AI uses to play
	 */
	public AI(String[] symbol, int playerNumber, Board board) {
		super(symbol[playerNumber]);
		for (int p = 0; p < symbol.length; p++) {
			if (!symbol[p].equals(this.symbol)) {
				opponentSymbol = symbol[p];
				break;
			}
		}
		
		this.board = board;
		this.priorityRatings = new BigInteger[priorityMatrix.length];
		for (int p = 0; p < priorityMatrix.length; p++) {
			priorityRatings[p] = new BigInteger("10").pow(priorityMatrix.length - (p + 1));
		}
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
				addPriorityFromBoardIndex(row, col);
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
	 * Calculates the priority given to a column by a specific index on the board
	 * @param row	Index row
	 * @param col	Index column
	 */
	private void addPriorityFromBoardIndex(int row, int col) {
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
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @return			Has string been found
	 */
	private boolean doesVerticalStringStartAtIndex(int row, int col, String string) {
		int counter = 0;
		try {
			int n = Integer.parseInt(string.substring(2, 3));
			for (int i = 0; i < 4; i++) {
				String value = board.get(row + i, col);
				String s = string.substring(1, 2);
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
	 * @param r			Row
	 * @param c			Column
	 * @param string	String to be searched for
	 * @param g			Gradient to be searched from start point (2 for vertical)
	 * @return			Has string been found
	 */
	private boolean doesHorizontalStringStartAtIndex(int row, int col, String string, int gradient) {
		int counter = 0;
		try {
			for (int i = 0; i < string.length(); i++) {
				String s = string.substring(i, i + 1);
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