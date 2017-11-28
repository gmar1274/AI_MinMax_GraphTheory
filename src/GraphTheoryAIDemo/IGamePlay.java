package GraphTheoryAIDemo;

import java.awt.Component;

import GraphTheoryAIDemo.TicTacToe.TURN;

public interface IGamePlay {
	/**
	 * Determines if 3 in a kind
	 * @param board
	 * @return
	 */
	int isGameOver(int[][] board, TURN player);
	/**
	 * methods to refresh game
	 */
	void restart();
	/**
	 * Display current board state - rank - turn
	 * @param node
	 */
	void displayCurrentBoard(Component c,GameBoardNode node,String time);
	/**
	 * Checks if tic tac toe winner
	 * @param board
	 * @param player
	 * @return
	 */
	boolean isThreeInARow(int[][] board, TURN player);
	/**
	 * 
	 * @param board
	 * @param player
	 */
	void makeMoveAI(int[][] board, TURN player);
	//boolean isLeftDiagonalWin(int[][] board,TURN player);
	//boolean isRightDiagonalWin(int[][] board,TURN player);
//	boolean isHorizontalWin(int[][]board,TURN player);
	//boolean isVerticalWin(int[][] board, TURN player);
	/**
	 * if no spaces contain minvalue
	 * @param board
	 * @return
	 */
	boolean drawGame(int[][] board);
}
