package GraphTheoryAIDemo;

import java.util.Arrays;
import java.util.Scanner;

import GraphTheoryAIDemo.TicTacToe.TURN;

/**
 * A Basic MINMAX Algorithm tic tac toe
 * 
 * @author gabe
 *
 */
public class MinMaxTicTacToe implements IMinMaxAlgorithm {

	private final int COMPUTER_WIN = 5000, PLAYER_WIN = -5000, TIE = 1, GAME_NOT_OVER = 0;
	private int PLAYER_MOVE_VALUE = 1, COMPUTER_MOVE_VALUE = 2;
	// private TURN player;
	private int game_board[][] = new int[3][3];
	private int maxdepth = 9;

	public MinMaxTicTacToe(int[] board, int playerone, int playertwo) {
		this.game_board = this.convert1Dto2D(board);
		// this.player = player;
		this.PLAYER_MOVE_VALUE = playerone;
		this.COMPUTER_MOVE_VALUE = playertwo;
	}

	public void updateGameBoard(int[] board) {
		this.game_board = this.convert1Dto2D(board);
	}

	public MinMaxTicTacToe() {

	}

	public static void main(String[] args) {
		MinMaxTicTacToe t = new MinMaxTicTacToe();
		t.startGame();
	}

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

	void printboard() {

		System.out.println(game_board[0][0] + " " + game_board[0][1] + " " + game_board[0][2]);
		System.out.println(+game_board[1][0] + " " + game_board[1][1] + " " + game_board[1][2]);
		System.out.println(+game_board[2][0] + " " + game_board[2][1] + " " + game_board[2][2]);
	}

	void setup() {
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				game_board[row][col] = Integer.MIN_VALUE;
			}
	}

	void playerTurn() {
		System.out.println("Enter your move:  ");
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		if (row > 2 || col > 2 || this.game_board[row][col] > 0) {
			this.playerTurn();
		} else
			game_board[row][col] = PLAYER_MOVE_VALUE;
	}

	int evaluate() {
		return 0;
	}

	private int[][] AITurn() {// update latest board

		int best = -20000, depth = maxdepth, score, ai_row = 0, ai_col = 0;
		for (int row = 0; row < 3; row++) {// naively start at beginning
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {// if spot available
					game_board[row][col] = COMPUTER_MOVE_VALUE; // make move on
																// board
					score = min(depth - 1);
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
		game_board[ai_row][ai_col] = COMPUTER_MOVE_VALUE;
		return new int[ai_row][ai_col];
	}

	public int[] AITurn(int[] board) {// update latest board
		this.game_board = this.convert1Dto2D(board);
		this.printboard();
		int best = -20000, depth = maxdepth, score, ai_row = 0, ai_col = 0;
		for (int row = 0; row < 3; row++) {// naively start at beginning
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {// if spot available
					game_board[row][col] = COMPUTER_MOVE_VALUE; // make move on
																// board
					score = min(depth - 1);
					if (score > best) {
						ai_row = row;
						ai_col = col;
						best = score;
					}
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		System.out.println("AI move is " + ai_row + " " + ai_col);
		game_board[ai_row][ai_col] = COMPUTER_MOVE_VALUE;
		int[] loc = new int[2];
		loc[0] = ai_row;
		loc[1] = ai_col;
		return loc;
	}

	public int min(int depth) {
		int best = 20000, score;
		if (check4winner() != GAME_NOT_OVER)
			return (check4winner());
		if (depth == 0)
			return (evaluate());
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {
					game_board[row][col] = PLAYER_MOVE_VALUE; // make move on
																// board
					score = max(depth - 1);
					if (score < best)
						best = score;
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		return (best);
	}

	public int max(int depth) {
		int best = -20000, score;
		if (check4winner() != GAME_NOT_OVER)
			return (check4winner());
		if (depth == 0)
			return (evaluate());
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE) {
					game_board[row][col] = COMPUTER_MOVE_VALUE; // make move on
																// board
					score = min(depth - 1);
					if (score > best)
						best = score;
					game_board[row][col] = Integer.MIN_VALUE; // undo move
				}
			}
		}
		return (best);
	}

	/**
	 * Hard code winning states
	 * 
	 * @return
	 */
	int check4winner() {
		if ((game_board[0][0] == 1) && (game_board[0][1] == 1) && (game_board[0][2] == 1)
				|| (game_board[1][0] == 1) && (game_board[1][1] == 1) && (game_board[1][2] == 1)
				|| (game_board[2][0] == 1) && (game_board[2][1] == 1) && (game_board[2][2] == 1)
				|| (game_board[0][0] == 1) && (game_board[1][0] == 1) && (game_board[2][0] == 1)
				|| (game_board[0][1] == 1) && (game_board[1][1] == 1) && (game_board[2][1] == 1)
				|| (game_board[0][2] == 1) && (game_board[1][2] == 1) && (game_board[2][2] == 1)
				|| (game_board[0][0] == 1) && (game_board[1][1] == 1) && (game_board[2][2] == 1)
				|| (game_board[0][2] == 1) && (game_board[1][1] == 1) && (game_board[2][0] == 1)) {
			return PLAYER_WIN; // computer wins
		}
		if ((game_board[0][0] == 2) && (game_board[0][1] == 2) && (game_board[0][2] == 2)
				|| (game_board[1][0] == 2) && (game_board[1][1] == 2) && (game_board[1][2] == 2)
				|| (game_board[2][0] == 2) && (game_board[2][1] == 2) && (game_board[2][2] == 2)
				|| (game_board[0][0] == 2) && (game_board[1][0] == 2) && (game_board[2][0] == 2)
				|| (game_board[0][1] == 2) && (game_board[1][1] == 2) && (game_board[2][1] == 2)
				|| (game_board[0][2] == 2) && (game_board[1][2] == 2) && (game_board[2][2] == 2)
				|| (game_board[0][0] == 2) && (game_board[1][1] == 2) && (game_board[2][2] == 2)
				|| (game_board[0][2] == 2) && (game_board[1][1] == 2) && (game_board[2][0] == 2)) {
			return COMPUTER_WIN;// player wins
		}
		// check for incomplete game
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				if (game_board[row][col] == Integer.MIN_VALUE)
					return GAME_NOT_OVER;
			}
		return TIE; // otherwise draw
	}

	void checkGameOver() {
		printboard();
		if (check4winner() == PLAYER_WIN) {
			System.out.println("you win");
		}
		if (check4winner() == COMPUTER_WIN) {
			System.out.println("AI win");
		}
		if (check4winner() == TIE) {
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
			col+=1;
		}
		return array2d;
	}
}
