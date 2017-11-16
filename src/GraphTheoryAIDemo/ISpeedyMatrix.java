package GraphTheoryAIDemo;

public interface ISpeedyMatrix {
	/**
	 * 
	 * Converts 2D index to 1D index for traditional 3x3 tic tac toe board
	 * @param row
	 * @param col
	 * @return
	 */
	int convert2DIndex(int row,int col);

}
