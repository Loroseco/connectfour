package connect;
 
import java.util.ArrayList;

import framework.Config;
import framework.Player;

/**
 * Player class to be used by AI
 * @author Loroseco
 *
 */
public class ConnectAI extends Player {
	
	private final int opponentNumber;
	private final ConnectBoard board;
	private int[][] priorityPerColumn;
	
	public ConnectAI(int playerNumber, ConnectBoard board) {
		super(playerNumber);
		this.board = board;
		this.opponentNumber = playerNumber == 0 ? 1 : 0;
	}
	
	@Override
	public String getMove() {
		resetPriorityPerColumn();
		
		for (int boardRow = 0; boardRow < Config.ROW_NUMBER_CONNECT; boardRow++) {
			for (int boardCol = 0; boardCol < Config.COL_NUMBER_CONNECT; boardCol++) {
				addPriorityFromBoardIndex(boardRow, boardCol);
			}
		}
		
		return getCalculatedReturnMove();
	}
	
	private void resetPriorityPerColumn() {
		priorityPerColumn = new int[Config.COL_NUMBER_CONNECT][ConnectAIConfig.PRIORITY_MATRIX.length];
		for (int col = 0; col < priorityPerColumn.length; col++) {
			for (int priority = 0; priority < priorityPerColumn[col].length; priority++) {
				priorityPerColumn[col][priority] = 0;
			}
		}
	}
	
	private void addPriorityFromBoardIndex(int boardRow, int boardCol) {
		for (int matrixRow = 0; matrixRow < ConnectAIConfig.PRIORITY_MATRIX.length; matrixRow++) {
			for (int matrixCol = 0; matrixCol < ConnectAIConfig.PRIORITY_MATRIX[matrixRow].length; matrixCol++) {
				String pattern = ConnectAIConfig.PRIORITY_MATRIX[matrixRow][matrixCol];

				if (isPatternVertical(pattern)) {
					addPriorityFromVerticalPattern(boardRow, boardCol, pattern, matrixRow);
				} else {
					addPriorityFromHorizontalPattern(boardRow, boardCol, pattern, matrixRow);
				}
			}
		}
	}
	
	private boolean isPatternVertical(String pattern) {
		return pattern.startsWith(ConnectAIConfig.VERTICAL.get());
	}

	private void addPriorityFromVerticalPattern(int row, int col, String pattern, int matrixRow) {	
		if (doesVerticalPatternStartAtIndex(row, col, pattern)) {
			priorityPerColumn[col][matrixRow]++;
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
		if (letter.equals(ConnectAIConfig.VALID.get())
				|| (letter.equals(ConnectAIConfig.TWO.get()) && pattern.contains(ConnectAIConfig.PLAYER.get()))) {
			priorityPerColumn[col + letterNumber][matrixRow]++;
		} else if (letter.equals(ConnectAIConfig.ONE.get())
				|| (letter.equals(ConnectAIConfig.TWO.get()) && pattern.contains(ConnectAIConfig.OPPONENT.get()))) {
			priorityPerColumn[col + letterNumber][matrixRow]--;
		}
	}

	private boolean doesVerticalPatternStartAtIndex(int row, int col, String pattern)
	{
		int counter = 0;
		try {
			int n = Integer.parseInt(pattern.substring(2, 3));
			for (int i = 0; i < 4; i++) {
				String s = pattern.substring(1, 2);
				if ((board.isIndexEqual(row + i, col, playerNumber) && s.equals(ConnectAIConfig.PLAYER.get()) && i < n) 
						|| (board.isIndexEqual(row + i, col, opponentNumber) && s.equals(ConnectAIConfig.OPPONENT.get()) && i < n)
						|| (board.isIndexEmpty(row + i, col) && i >= n)) {
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
				if ((board.isIndexEqual(patternRow, patternCol, playerNumber) && letter.equals(ConnectAIConfig.PLAYER.get()))
						|| (board.isIndexEqual(patternRow, patternCol, opponentNumber) && letter.equals(ConnectAIConfig.OPPONENT.get()))) {
					counter++;
				} else if (board.isIndexEmpty(patternRow, patternCol) 
						&& (letter.equals(ConnectAIConfig.ONE.get()) || letter.equals(ConnectAIConfig.TWO.get()) 
								|| letter.equals(ConnectAIConfig.NO_SCORE.get()) || letter.equals(ConnectAIConfig.VALID.get()))) {
					int depth = findDepth(patternRow, patternCol, 0);
					if ((depth == 0 && (letter.equals(ConnectAIConfig.VALID.get()) || letter.equals(ConnectAIConfig.NO_SCORE.get())))
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
	
	private String getCalculatedReturnMove() {	
		ArrayList<Integer> returnMoves = getHighestPriorityMoves();
		debug();
		int chosenMove = getClosestMoveToMiddleColumn(returnMoves);
		return Integer.toString(chosenMove);
	}

	private ArrayList<Integer> getHighestPriorityMoves() {
		int[] returnPriority = null;
		ArrayList<Integer> returnMoves = new ArrayList<>();
		
		for (int col = 0; col < priorityPerColumn.length; col++) {
			if (board.isColumnFull(col)) {
				continue;
			} else if (returnPriority == null || isEqualOrHigherPriority(priorityPerColumn[col], returnPriority)) {
				if (returnPriority == null || !priorityPerColumn[col].equals(returnPriority)) {
					returnPriority = priorityPerColumn[col];
					returnMoves = new ArrayList<>();
				}
				returnMoves.add(col);
			}
			
		}
		return returnMoves;
	}
	
	private void debug() {
		if (Config.DEBUG) {
			for (int[] col : priorityPerColumn) {
				for (int priority : col) {
					System.out.print(priority);
				}
				System.out.println();
			}
		}
	}
	
	private int getClosestMoveToMiddleColumn(ArrayList<Integer> returnMoves) {
		int chosenMove = -1;
		int chosenVariance = Config.COL_NUMBER_CONNECT;
		int middleColumn = Config.COL_NUMBER_CONNECT / 2;
		
		for (int move : returnMoves) {
			int variance = Math.abs(move - middleColumn);
			if (variance < chosenVariance) {
				chosenMove = move;
				chosenVariance = variance;
			}
		}
		return chosenMove;
	}
	
	private boolean isEqualOrHigherPriority(int[] firstArray, int[] secondArray) {
		for (int priority = 0; priority < firstArray.length; priority++) {
			if (firstArray[priority] > secondArray[priority]) {
				return true;
			}
			if (firstArray[priority] < secondArray[priority]) {
				return false;
			}
		}
		return true;
	}
}