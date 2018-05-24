package connect;

/**
 * enum that manages the AI's priority matrix, as well as defining the representation of each type of move in the matrix.
 * @author Loroseco
 *
 */
public enum ConnectAIConfig {	
	/** AI Symbol in PRIORITY_MATRIX */
	PLAYER("P"),
	/** Opponent Symbol in PRIORITY_MATRIX */
	OPPONENT("Q"),
	/** Valid move in PRIORITY_MATRIX */
	VALID("_"),
	/** Empty space above a valid move in PRIORITY_MATRIX */
	ONE("1"),
	/** Empty space above "ONE" in PRIORITY_MATRIX */
	TWO("2"),
	/** Valid move that is not assigned a priority from this string in PRIORITY_MATRIX */
	NO_SCORE("N"),
	/** Vertical pattern in PRIORITY_MATRIX. 2nd symbol is player symbol and 3rd is the height of the pattern.*/
	VERTICAL("V");
	
	/**
	 * All patterns that the AI detects. the higher rows are higher priorities.
	 * P = AI player
	 * Q = Opponent
	 */
	public static final String[][] PRIORITY_MATRIX = 
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
	
	private String symbol;
	
	ConnectAIConfig(String symbol) {
		this.symbol = symbol;
	}
	
	public String get() {
		return symbol;
	}
}
