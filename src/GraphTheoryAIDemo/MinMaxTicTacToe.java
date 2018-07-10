package GraphTheoryAIDemo;

import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

import GraphTheoryAIDemo.TicTacToe.TURN;

/**
 * A Basic MINMAX Algorithm with alpha beta pruning tic tac toe Main class. Can play against computer console or GUI. Uses 1D to 2D [][] int
 * 
 * @author gabe
 *
 */
public class MinMaxTicTacToe implements IMinMaxAlgorithm {
	public static enum DIFFICULTY{EASY, INTERMEDIATE, HARD}
	public final int COMPUTER_WIN = 400, PLAYER_WIN = -400, TIE = 1, GAME_NOT_OVER = 0;// arbritary
	// values
	private int PLAYER_MOVE_SYMBOL = 1, COMPUTER_MOVE_SYMBOL = 2; // denote a value on the board
	private int BEST_SCORE = 400;//absolute value
	private int game_board[][] = new int[3][3]; // game board
	private int maxdepth = 9;// number of max moves - known

	/**
	 * 
	 * @param board
	 *            1D array of game board
	 * @param playerone
	 *            player one symbol for game board
	 * @param playertwo
	 *            player two symbol for game board
	 */
	public MinMaxTicTacToe(int[][] board, int playerone, int playertwo) {
		//this.game_board = this.convert1Dto2D(board);
		this.game_board = board;
		this.PLAYER_MOVE_SYMBOL = playerone;
		this.COMPUTER_MOVE_SYMBOL = playertwo;
	}

	/**
	 * refresh this class game board from interactive class
	 * 
	 * @param board
	 */
	public void updateGameBoard(int[] board) {
		this.game_board = this.convert1Dto2D(board);
	}

	public MinMaxTicTacToe() {

	}

	/**
	 * Console AI game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MinMaxTicTacToe t = new MinMaxTicTacToe();
		t.startGame();
	}

	/**
	 * Start game for Console app.
	 */
	private void startGame() {
		setup();
		printboard();
		for (;;) {
			playerTurn();// player
			checkGameOver();
			AITurn();// AI
			checkGameOver();
		}
	}

	/**
	 * Debug.. print 2D game board
	 */
	void printboard() {

		System.out.println(game_board[0][0] + " " + game_board[0][1] + " " + game_board[0][2]);
		System.out.println(+game_board[1][0] + " " + game_board[1][1] + " " + game_board[1][2]);
		System.out.println(+game_board[2][0] + " " + game_board[2][1] + " " + game_board[2][2]);
	}

	/**
	 * init 2D game board
	 */
	void setup() {
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				game_board[row][col] = Integer.MIN_VALUE;
			}
	}

	/**
	 * Scanner get user move
	 */
	void playerTurn() {
		System.out.println("Enter your move:  ");
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		if (row > 2 || col > 2 || this.game_board[row][col] > 0) {
			this.playerTurn();
		} else game_board[row][col] = PLAYER_MOVE_SYMBOL;
	}

	/**
	 * AI move using minmax with alpha beta pruning. Updates game board in GUI class. From given game state: (Maximizer on AI) - generate all possible moves (child) (branching
	 * factor) - apply minmax on child node
	 * 
	 * @param board
	 *            - intial game state. {@value} location of move as int[]: array[0]=row, array[1]=column.
	 * @return AIMOVE - int[]location and elapsed_time in nano seconds
	 * 
	 */
	public AIMOVE AITurn(int[][] board, DIFFICULTY diff) {
		long start = System.nanoTime();
		this.game_board = board;
		int depth = getMaxDepth(diff), score, ai_row = 0, ai_col = 0;
		System.out.println("depth: "+depth);
		int alpha = -this.BEST_SCORE, beta = this.BEST_SCORE;// assume alpha is worst move similarly beta best move(*Remember trying to maximize alpha and minimize beta)
		int best = -this.BEST_SCORE;//AI wants positive
		for (int row = 0; row < 3; row++) {// naively start at beginning spot
			for (int col = 0; col < 3; col++) {// and look for an available next move
				if (game_board[row][col] == Integer.MIN_VALUE) {// if spot available *Recall (-infinity<==>empty) otherwise spot is taken.
					game_board[row][col] = COMPUTER_MOVE_SYMBOL; // make move on board and generate child node
					score = min(depth - 1, alpha, beta);// recursively (Depth first search). Traverse the tree for best movea
					if (score > best) {
						ai_row = row;
						ai_col = col;
						best = score;
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
					if (alpha >= beta) {//prune condition stop exploring child nodes
						alertAB(ai_row, ai_col);
						game_board[ai_row][ai_col] = COMPUTER_MOVE_SYMBOL;
						int[] loc = new int[] { ai_row, ai_col };
						long elapsed_time = System.nanoTime() - start;
						return new AIMOVE(loc, elapsed_time);
					}
				}
			}
		}
		System.out.println("AI move is " + ai_row + " " + ai_col);
		game_board[ai_row][ai_col] = COMPUTER_MOVE_SYMBOL;
		int[] loc = new int[] { ai_row, ai_col };
		long elapsed_time = System.nanoTime() - start;
		return new AIMOVE(loc, elapsed_time);
	}

	private void alertAB(int ai_row, int ai_col) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "Alpha-Beta pruning!! [" + ai_row + "][" + ai_col + "]");
			}
		}).start();
	}

	/**
	 * Minimizer function using alpha-beta pruning
	 * 
	 * @return score - which is the heuristic value
	 */
	public int min(int depth, int alpha, int beta) {
		int best = 20000;//assume worst case
		int score;
		int utility_value = check4Winner();
		if (utility_value != GAME_NOT_OVER) return utility_value;
		if (depth == 0) return 0;//heuristic value
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {
					game_board[row][col] = PLAYER_MOVE_SYMBOL; // make move
					score = max(depth - 1, alpha, beta);
					if (score < best) {//update best
						best = score;
						beta = best;//update beta
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
					if (beta <= alpha) { return best; }
				}
			}
		}
		return best;
	}

	/**
	 * Maximizer function using alpha beta pruning.
	 * 
	 * @return score - heuristic value
	 */
	public int max(int depth, int alpha, int beta) {
		int best = -20000;//assumed to be worst
		int score;
		int utility_value = check4Winner();
		if (utility_value != GAME_NOT_OVER) return utility_value;
		if (depth == 0) return this.evaluate();//heuristic value
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {//if empty space
					game_board[row][col] = COMPUTER_MOVE_SYMBOL; // make move on (new game state aka child node)
					score = min(depth - 1, alpha, beta);
					if (score > best) {//update best
						best = score;
						alpha = best;//update alpha
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
					if (alpha >= beta) { return best; } //prune
				}
			}
		}
		return best;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////MINMAX w/o alpha-beta pruning//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * MinMax algorithm no a-b.
	 * 
	 * @param board
	 * @param alpha_beta
	 *            -on or off will only perform naive minmax - off
	 * @return loc- best location int array [], array[0]=row , array[1]=col
	 */
	public AIMOVE AITurn(int[][] board, boolean alpha_beta_false, DIFFICULTY diff) {
		long start = System.nanoTime();
		this.game_board = board;
		this.printboard();//debug
		int depth = getMaxDepth(diff), score, ai_row = 0, ai_col = 0, best = -BEST_SCORE;
		for (int row = 0; row < 3; row++) {// naively start at beginning spot
			for (int col = 0; col < 3; col++) {// and look for an available next move
				if (game_board[row][col] == Integer.MIN_VALUE) {// if spot available *Recall (-infinity<==>empty) otherwise spot is taken.
					game_board[row][col] = COMPUTER_MOVE_SYMBOL; // make move on board and generate child node
					score = min(depth - 1);// recursively (Depth first search). Traverse the tree for best movea
					if (score > best) {//best) {// make the move! Max the state
						ai_row = row;
						ai_col = col;
						best = score;//best = score; //update Max score (Best move)
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		System.out.println("MINMAX w/o a-b. AI move is [row][col]: [" + ai_row + "][" + ai_col + "]");
		game_board[ai_row][ai_col] = COMPUTER_MOVE_SYMBOL;
		long elapsed_time = System.nanoTime() - start;
		int[] loc = new int[] { ai_row, ai_col };

		return new AIMOVE(loc, elapsed_time);
	}

	private int getMaxDepth(DIFFICULTY diff) {
		if(diff.equals(DIFFICULTY.EASY)){
			return 1;
		}else if(diff.equals(DIFFICULTY.INTERMEDIATE)){
			return 2;
		}else{
			return this.maxdepth;
		}
	}

	/**
	 * Minimizer function 
	 * @return score - terminal state
	 */
	public int min(int depth) {
		int best = BEST_SCORE, score;//assume worst case
		int utility_value = check4Winner();
		if (utility_value != GAME_NOT_OVER) return utility_value;
		if (depth == 0) return this.evaluate();//heuristic value
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {
					game_board[row][col] = PLAYER_MOVE_SYMBOL; // make move on board
					score = max(depth - 1);
					if (score < best) {//score is Minimum(max(), beta)
						best = score;
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		return best;
	}
	/**
	 * Maximizer function using alpha beta pruning.
	 * 
	 * @return score - heuristic value
	 */
	public int max(int depth) {
		int best = -BEST_SCORE;
		int score;//Integer.MIN_VALUE;//assume worst case
		int utility_value = check4Winner();
		if (utility_value != GAME_NOT_OVER) return utility_value;
		if (depth == 0) return this.evaluate();//heuristic value
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {//if empty space
					game_board[row][col] = COMPUTER_MOVE_SYMBOL; // make move on (new game state aka child node)
					score = min(depth - 1);
					if (score > -BEST_SCORE) {
						best = score;
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		return (best);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The heuristic evaluation function for the current board
	 * 
	 * @Return +200, +20, +2 for EACH 3-, 2-, 1-in-a-line for computer. -200, -20, -2 for EACH 3-, 2-, 1-in-a-line for opponent. 1 otherwise
	 */
	private int evaluate() {
		int score = 0;
		// Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
		score += evaluateLine(0, 0, 0, 1, 0, 2); // row 0
		score += evaluateLine(1, 0, 1, 1, 1, 2); // row 1
		score += evaluateLine(2, 0, 2, 1, 2, 2); // row 2
		score += evaluateLine(0, 0, 1, 0, 2, 0); // col 0
		score += evaluateLine(0, 1, 1, 1, 2, 1); // col 1
		score += evaluateLine(0, 2, 1, 2, 2, 2); // col 2
		score += evaluateLine(0, 0, 1, 1, 2, 2); // diagonal
		score += evaluateLine(0, 2, 1, 1, 2, 0); // alternate diagonal
		return score;
	}

	/**
	 * The heuristic evaluation function for the given line of 3 game_board
	 * 
	 * @Return +200, +20, +2 for 3-, 2-, 1-in-a-line for computer. -200, -20, -2 for 3-, 2-, 1-in-a-line for opponent. 1 otherwise
	 */
	private int evaluateLine(int cell1_row, int cell1_col, int cell2_row, int cell2_col, int cell3_row, int cell3_col) {
		int score = 0;
		// First cell
		if (game_board[cell1_row][cell1_col] == this.COMPUTER_MOVE_SYMBOL) {
			score = 2;
		} else if (game_board[cell1_row][cell1_col] == this.PLAYER_MOVE_SYMBOL) {
			score = -2;
		} else {
			score = 1;
		}
		// Second cell
		if (game_board[cell2_row][cell2_col] == this.COMPUTER_MOVE_SYMBOL) {
			if (score == 2) {
				score = 20;
			} else if (score == -2) {
				return 1;
			} else {
				score = 2;
			}
		} else if (game_board[cell2_row][cell2_col] == this.PLAYER_MOVE_SYMBOL) {
			if (score == -2) {//prev was player
				score = -20;
			} else if (score == 2) {
				return 1;
			} else {
				return -2;
			}
		}
		// Third cell
		if (game_board[cell3_row][cell3_col] == this.COMPUTER_MOVE_SYMBOL) {
			if (score > 2) { // cell1 and/or cell2 is AI
				score *= 20;
			} else if (score < 1) { // cell1 and/or cell2 is player
				return 1;
			} else { // cell1 and cell2 are empty
				score = 2;
			}
		} else if (game_board[cell3_row][cell3_col] == this.PLAYER_MOVE_SYMBOL) {
			if (score < 1) { // cell and/or cell2 is player
				score *= 20;
			} else if (score > 2) { // cell1 and/or cell2 is AI
				return 1;
			} else { // cell1 and cell2 are empty
				score = -2;
			}
		}
		return score;
	}

	/**
	 * Hard code winning states
	 * 
	 * @return PLAYER_WIN, COMPUTER_WIN, GAME_NOT_OVER, TIE
	 */
	int check4Winner() {
		if ((game_board[0][0] == 1) && (game_board[0][1] == 1) && (game_board[0][2] == 1) || (game_board[1][0] == 1) && (game_board[1][1] == 1) && (game_board[1][2] == 1)
				|| (game_board[2][0] == 1) && (game_board[2][1] == 1) && (game_board[2][2] == 1) || (game_board[0][0] == 1) && (game_board[1][0] == 1) && (game_board[2][0] == 1)
				|| (game_board[0][1] == 1) && (game_board[1][1] == 1) && (game_board[2][1] == 1) || (game_board[0][2] == 1) && (game_board[1][2] == 1) && (game_board[2][2] == 1)
				|| (game_board[0][0] == 1) && (game_board[1][1] == 1) && (game_board[2][2] == 1)
				|| (game_board[0][2] == 1) && (game_board[1][1] == 1) && (game_board[2][0] == 1)) { return PLAYER_WIN; // computer wins
		}
		if ((game_board[0][0] == 2) && (game_board[0][1] == 2) && (game_board[0][2] == 2) || (game_board[1][0] == 2) && (game_board[1][1] == 2) && (game_board[1][2] == 2)
				|| (game_board[2][0] == 2) && (game_board[2][1] == 2) && (game_board[2][2] == 2) || (game_board[0][0] == 2) && (game_board[1][0] == 2) && (game_board[2][0] == 2)
				|| (game_board[0][1] == 2) && (game_board[1][1] == 2) && (game_board[2][1] == 2) || (game_board[0][2] == 2) && (game_board[1][2] == 2) && (game_board[2][2] == 2)
				|| (game_board[0][0] == 2) && (game_board[1][1] == 2) && (game_board[2][2] == 2)
				|| (game_board[0][2] == 2) && (game_board[1][1] == 2) && (game_board[2][0] == 2)) { return COMPUTER_WIN;// player wins
		}
		// check for incomplete game
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) return GAME_NOT_OVER;
			}
		return TIE; // otherwise draw
	}

	/**
	 * Console check.
	 */
	void checkGameOver() {
		printboard();
		if (check4Winner() == PLAYER_WIN) {
			System.out.println("you win");
		}
		if (check4Winner() == COMPUTER_WIN) {
			System.out.println("AI win");
		}
		if (check4Winner() == TIE) {
			System.out.println("draw");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int[][] convert1Dto2D(int[] arr) {
		int[][] array2d = new int[3][3];
		int row = 0, col = 0;
		// System.out.println(Arrays.toString(arr) +"
		// \n"+Arrays.toString(array2d) );
		for (int i = 0; i < arr.length; ++i) {
			if (col > 2) {
				row += 1;
				col = 0;
			}
			array2d[row][col] = arr[i];
			col += 1;
		}
		return array2d;
	}

	/**
	 * Console AI move
	 * 
	 * @return
	 */
	private int[] AITurn() {// update latest board
		int best = -20000, depth = maxdepth, score, ai_row = 0, ai_col = 0;//THIS IS HUGE...the starting best value...
		for (int row = 0; row < 3; row++) {// naively start at beginning
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {// if spot
					// available
					game_board[row][col] = COMPUTER_MOVE_SYMBOL; // make move on
					// board
					score = min(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
					if (score > best) {
						ai_row = row;
						ai_col = col;
						best = score;
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		System.out.println("my move is " + ai_row + " " + ai_col);
		game_board[ai_row][ai_col] = COMPUTER_MOVE_SYMBOL;
		return new int[] { ai_row, ai_col };
	}

	/**
	 * Could be bug but if any of the cases then return true.
	 * 
	 * @param game_state
	 * @return
	 */
	private boolean isTerminalNode(int game_state) {
		switch (game_state) {
		case PLAYER_WIN:
		case COMPUTER_WIN:
		case TIE:
			return true;
		}
		return false;
	}

	
}
