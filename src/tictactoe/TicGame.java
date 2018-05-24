package tictactoe;

import framework.Game;
import framework.Player;
import framework.Scoreboard;

public class TicGame extends Game{
	
	public TicGame(TicBoard board, Scoreboard score, Player[] player) {
		super(board, score, player);
	}

	@Override
	public boolean makeMove(String move, int playerNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int findWinner() {
		// TODO Auto-generated method stub
		return 0;
	}

}
