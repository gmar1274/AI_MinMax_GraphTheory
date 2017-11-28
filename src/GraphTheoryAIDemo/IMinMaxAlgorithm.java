package GraphTheoryAIDemo;

import GraphTheoryAIDemo.TicTacToe.TURN;

public interface IMinMaxAlgorithm {
	//int[][] convert1Dto2D(int[] arr);
	int max(int depth,int alpha,int beta);
	int min(int depth,int alpha,int beta);
}
