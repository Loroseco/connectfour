/*
 * AI
 * 
 * Version 2.2
 */
package computer;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import client.Player;

import java.math.BigInteger;

/**
 * Player class to be used by AI
 * @author Loroseco
 *
 */
public class AI extends Player {
	
	private BigInteger[] priorityRatings;
	
	/* priorityMatrix is all patterns the AI detects. the higher rows are higher priorities.
	 * O	Player symbol
	 * X	Opponent symbol		NOTE: O in priorities is always the player symbol, and X is always the opponent.
	 * _	Valid move
	 * 1	Empty space above a valid move
	 * 2	Empty space above "1"
	 * N	Valid move that is not assigned a priority from this string
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
	public AI(String symbol) {
		super(symbol);
		BigInteger[] priorityRatings = new BigInteger[priorityMatrix.length];
		for (int p = 0; p < priorityMatrix.length; p++) {
			priorityRatings[p] = new BigInteger("10").pow(priorityMatrix.length - (p + 1));
		}
		this.priorityRatings = priorityRatings;
	}
	
	@Override
	public String getMove(String[][] board) {
		MoveFetcher moveFetcher = new MoveFetcher(symbol, board, priorityMatrix, priorityRatings);
		return moveFetcher.getMove();
	}
}