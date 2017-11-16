package GraphTheoryAIDemo;

import GraphTheoryAIDemo.TicTacToe.TURN;

/**
 * 
 * @author gabe This class will represent a GameBoard State for the min max
 *         algorithm
 */
public class GameBoardNode implements ISpeedyMatrix, Comparable {
	private int[] board;// board -1 is available move, 0,1 are player
	private TURN player;// current player move
	private int rank;// heuristic
	// private int round;
	private int id;

	public GameBoardNode(int[] board, TURN player, int round) {
		this.board = board;
		this.player = player;
		this.id = round;// System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	public int convert2DIndex(int row, int col) {
		return (row * TicTacToe.ROW) + col;
	}

	/**
	 * @return the board
	 */
	public int[] getBoard() {
		return board;
	}

	/**
	 * @return the player
	 */
	public TURN getPlayer() {
		return player;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	public int getRound() {
		return this.id;
	}

	public String toString() {
		String string = "[";
		for (int i = 0; i < this.board.length; ++i) {

			if (i > 0 && (i) % TicTacToe.ROW == 0) {
				string += "]\n[";
			}
			switch (this.board[i]) {
			case Integer.MIN_VALUE:
				string += "-";
				break;
			case 1:
				string += "X";
				break;
			case 2:
				string += "O";
				break;
			}// end switch
			string += " ";
		} // end for
		return string + "]";
	}

	public long getID() {
		return this.id;
	}

	@Override
	public int compareTo(Object o) {
		GameBoardNode g = (GameBoardNode) o;
		if (this.id < g.getID())
			return 1;
		else if (this.id > g.getID())
			return -1;
		return 0;
	}
}