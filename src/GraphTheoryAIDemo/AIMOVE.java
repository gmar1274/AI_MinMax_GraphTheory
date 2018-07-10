package GraphTheoryAIDemo;

public class AIMOVE {
	private int[] loc;
	private double time_ns;

	public AIMOVE(int[] loc, double time_elapsed) {
		this.loc = loc;
		this.time_ns = time_elapsed;
	}

	/**
	 * @return the loc
	 */
	public int[] getLoc() {
		return loc;
	}

	/**
	 * @param loc
	 *            the loc to set
	 */
	public void setLoc(int[] loc) {
		this.loc = loc;
	}

	/**
	 * @return the time_ns
	 */
	public double getTime_ns() {
		return time_ns;
	}

	/**
	 * @param time_ns
	 *            the time_ns to set
	 */
	public void setTime_ns(double time_ns) {
		this.time_ns = time_ns;
	}
	public int getRow(){
		return this.loc[0];
	}
	public int getColumn(){
		return this.loc[1];
	}
	public String toString(){
		return "["+this.getRow()+"]["+this.getColumn()+"]";
	}
}