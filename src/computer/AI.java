package computer;
 
import java.util.ArrayList;
import java.util.Arrays;

import server.Board;
import server.Config;

import java.math.BigInteger;

/**
 * Player class to be used by AI
 * @author Loroseco
 *
 */
public class AI extends Player {
	
	private final Board board;
	private final int opponentNumber;
	
	private final BigInteger[] priorityPerMatrixRow;
	private BigInteger[] priorityPerColumn;
	
	public AI(int playerNumber, Board board) {
		super(playerNumber);
		this.board = board;
		
		opponentNumber = playerNumber == 0 ? 1 : 0;
		priorityPerMatrixRow = new BigInteger[AIConfig.PRIORITY_MATRIX.length];
		for (int p = 0; p < AIConfig.PRIORITY_MATRIX.length; p++) {
			priorityPerMatrixRow[p] = new BigInteger("10").pow(AIConfig.PRIORITY_MATRIX.length - (p + 1));
		}
	}
	
	@Override
	public String getMove() {
		setPriorityPerColumn("0");
		
		for (int boardRow = 0; boardRow < Config.NO_OF_ROWS; boardRow++) {
			for (int boardCol = 0; boardCol < Config.NO_OF_COLS; boardCol++) {
				addPriorityFromBoardIndex(boardRow, boardCol);
			}
		}
		
		return calculateReturnMove();
	}
	
	private void setPriorityPerColumn(String value) {
		priorityPerColumn = new BigInteger[Config.NO_OF_COLS];
		for (int col = 0; col < Config.NO_OF_COLS; col++) {
			priorityPerColumn[col] =  new BigInteger(value);
		}
	}
	
	private void addPriorityFromBoardIndex(int boardRow, int boardCol) {
		for (int matrixRow = 0; matrixRow < AIConfig.PRIORITY_MATRIX.length; matrixRow++) {
			for (int matrixCol = 0; matrixCol < AIConfig.PRIORITY_MATRIX[matrixRow].length; matrixCol++) {
				String pattern = AIConfig.PRIORITY_MATRIX[matrixRow][matrixCol];

				if (isPatternVertical(pattern)) {
					addPriorityFromVerticalPattern(boardRow, boardCol, pattern, matrixRow);
				} else {
					addPriorityFromHorizontalPattern(boardRow, boardCol, pattern, matrixRow);
				}
			}
		}
	}
	
	private boolean isPatternVertical(String pattern) {
		return pattern.startsWith(AIConfig.VERTICAL.get());
	}

	private void addPriorityFromVerticalPattern(int row, int col, String pattern, int matrixRow) {	
		if (doesVerticalPatternStartAtIndex(row, col, pattern)) {
			priorityPerColumn[col] = priorityPerColumn[col].add(priorityPerMatrixRow[matrixRow]);
		}
	}
	
	private void addPriorityFromHorizontalPattern(int row, int col, String pattern, int matrixRow) {	
		for (int gradient = -1; gradient < 2; gradient++) {
			if (doesHorizontalPatternStartAtIndex(row, col, pattern, gradient)) {
				for (int letterNumber = 0; letterNumber < pattern.length(); letterNumber++) {
					addPriorityFromPatternLetter(col, matrixRow, pattern, letterNumber);
				}
			}
		}
	}
	
	private void addPriorityFromPatternLetter(int col, int matrixRow, String pattern, int letterNumber) {
		String letter = pattern.substring(letterNumber, letterNumber + 1);
		if (letter.equals(AIConfig.VALID.get())
				|| (letter.equals(AIConfig.TWO.get()) && pattern.contains(AIConfig.PLAYER.get()))) {
			priorityPerColumn[col + letterNumber] = priorityPerColumn[col + letterNumber].add(priorityPerMatrixRow[matrixRow]);
		} else if (letter.equals(AIConfig.ONE.get())
				|| (letter.equals(AIConfig.TWO.get()) && pattern.contains(AIConfig.OPPONENT.get()))) {
			priorityPerColumn[col + letterNumber] = priorityPerColumn[col + letterNumber].add(priorityPerMatrixRow[matrixRow].multiply(new BigInteger("-1")));
		}
	}

	private boolean doesVerticalPatternStartAtIndex(int row, int col, String pattern)
	{
		int counter = 0;
		try {
			int n = Integer.parseInt(pattern.substring(2, 3));
			for (int i = 0; i < 4; i++) {
				String s = pattern.substring(1, 2);
				if ((board.isIndexEqual(row + i, col, playerNumber) && s.equals(AIConfig.PLAYER.get()) && i < n) 
						|| (board.isIndexEqual(row + i, col, opponentNumber) && s.equals(AIConfig.OPPONENT.get()) && i < n)
						|| (board.isIndexEmpty(row + 1, col) && i >= n)) {
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
	
	private boolean doesHorizontalPatternStartAtIndex(int row, int col, String pattern, int gradient) {
		int counter = 0;
		try {
			for (int letterNumber = 0; letterNumber < pattern.length(); letterNumber++) {
				int patternRow = row + (letterNumber * gradient);
				int patternCol = col + letterNumber;
				
				String letter = pattern.substring(letterNumber, letterNumber + 1);
				if ((board.isIndexEqual(patternRow, patternCol, playerNumber) && letter.equals(AIConfig.PLAYER.get()))
						|| (board.isIndexEqual(patternRow, patternCol, opponentNumber) && letter.equals(AIConfig.OPPONENT.get()))) {
					counter++;
				} else if (board.isIndexEmpty(patternRow, patternCol) 
						&& (letter.equals(AIConfig.ONE.get()) || letter.equals(AIConfig.TWO.get()) 
								|| letter.equals(AIConfig.NO_SCORE.get()) || letter.equals(AIConfig.VALID.get()))) {
					int depth = findDepth(patternRow, patternCol, 0);
					if ((depth == 0 && (letter.equals(AIConfig.VALID.get()) || letter.equals(AIConfig.NO_SCORE.get())))
							|| Integer.toString(depth).equals(letter)) {
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
	
	private int findDepth(int row, int col, int depth) {
		if (row == 0
				|| (board.isIndexEmpty(row, col)
						&& !board.isIndexEmpty(row - 1, col))) {
			return depth;
		} else {
			return findDepth(row - 1, col, depth + 1);
		}
	}
	
	private String calculateReturnMove() {
		BigInteger returnPriority = null;
		ArrayList<Integer> returnMoves = new ArrayList<>();
		for (int col = 0; col < priorityPerColumn.length; col++) {
			if (board.isColumnFull(col)) {
				continue;
			} 
			else if (returnPriority == null || priorityPerColumn[col].compareTo(returnPriority) >= 0) {
				if (returnPriority == null || priorityPerColumn[col].compareTo(returnPriority) == 1) {
					returnPriority = priorityPerColumn[col];
					returnMoves = new ArrayList<>();
				}
				returnMoves.add(col);
			}
		}
		
		if (Config.DEBUG) {
			System.out.println(Arrays.toString(priorityPerColumn));
		}

		int chosenMove = -1;
		int chosenVariance = Config.NO_OF_COLS;
		int middleColumn = Config.NO_OF_COLS / 2;
		
		for (int move : returnMoves) {
			int variance = Math.abs(move - middleColumn);
			if (variance < chosenVariance) {
				chosenMove = move;
				chosenVariance = variance;
			}
		}
		
		return Integer.toString(chosenMove);
	}
}