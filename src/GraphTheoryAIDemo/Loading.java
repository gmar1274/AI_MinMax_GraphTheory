package GraphTheoryAIDemo;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;

import GraphTheoryAIDemo.Loading.TIME;
import GraphTheoryAIDemo.MinMaxTicTacToe.DIFFICULTY;
import GraphTheoryAIDemo.TicTacToe.TURN;

public class Loading extends JDialog {
	public enum TIME {
		S, MS, NS
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loading dialog = new Loading();
					dialog.setModal(true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String elapsedTime;
	private JLabel timeLabel;
	private JButton closeBtn;
	private MinMaxTicTacToe minMaxClass;
	private int[][] board;
	private TIME time;

	public Loading() {

	}

	/**
	 * Create the dialog.
	 * 
	 * @param player
	 * @param board
	 * @param minMaxClass
	 */
	public Loading(int[][] board, TURN player, MinMaxTicTacToe minMaxClass) {
		this.minMaxClass = minMaxClass;
		this.board = board;
		getContentPane().setBackground(new Color(255, 255, 255));
		setUndecorated(true);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(Color.BLACK);
		setAlwaysOnTop(false);
		setBounds(100, 100, 700, 70);
		getContentPane().setLayout(new BorderLayout(0, 0));

		timeLabel = new JLabel("Elapsed time: ");
		timeLabel.setVisible(false);
		timeLabel.setFont(new Font("Sitka Subheading", Font.PLAIN, 18));
		getContentPane().add(timeLabel, BorderLayout.WEST);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setMaximumSize(new Dimension(200, 9));
		progressBar.setPreferredSize(new Dimension(146, 9));
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setElapsedTime(null,TIME.NS);
			}
		});
		progressBar.setForeground(new Color(152, 251, 152));
		progressBar.setIndeterminate(true);
		getContentPane().add(progressBar, BorderLayout.CENTER);

		closeBtn = new JButton("Close");
		getContentPane().add(closeBtn, BorderLayout.EAST);
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeBtn.setVisible(false);
		closeBtn.setEnabled(false);
		closeBtn.setForeground(Color.RED);
		closeBtn.setFont(new Font("Sitka Heading", Font.PLAIN, 12));

		JLabel lblGeneratingMove = new JLabel("Generating move...");
		getContentPane().add(lblGeneratingMove, BorderLayout.NORTH);
		lblGeneratingMove.setBackground(Color.BLACK);
		lblGeneratingMove.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblGeneratingMove.setHorizontalAlignment(SwingConstants.CENTER);
		this.setLocationRelativeTo(null);
	}

	public void setElapsedTime(AIMOVE move , TIME cUnit) {
		if(move==null)return;
		double time = this.convertToUnits(move.getTime_ns(), cUnit);
		DecimalFormat df = new DecimalFormat("#0.0####");
		String unit = this.getUnit(cUnit);
		this.timeLabel.setVisible(true);
		this.elapsedTime = df.format(time) + " " + unit;
		this.timeLabel.setText("Time elapsed: " + this.elapsedTime);
		this.closeBtn.setEnabled(true);
		this.closeBtn.setVisible(true);
	}

	public String getElapsedTime() {
		return this.elapsedTime;
	}

	/**
	 * 
	 * @param ab
	 *            - alpha beta flag
	 * @return
	 */
	public AIMOVE makeMoveAI(boolean ab, String action_command_time, DIFFICULTY diff) {
		this.time = this.convertToTIME(action_command_time);
		AIMOVE loc;
		if (ab) {
			 loc = this.minMaxClass.AITurn(this.board,diff);
			 
			this.setElapsedTime(loc,time);
			return loc;
		} else {
			loc = this.minMaxClass.AITurn(this.board, ab,diff);
			this.setElapsedTime(loc,time);
	
			return loc;
		}
	}

	private double convertToUnits(double total, TIME time) {
		this.time = time;
		double et = total;//in ns
		switch (time) {
		case S:
			et = total / Math.pow(10, 9);
			break;
		case MS:
			et = total / Math.pow(10, 3);
			break;
		case NS:
			break;
		}
		return et;
	}

	private TIME convertToTIME(String ac) {
		TIME time = null;
		switch (ac) {
		case "S":
			time = TIME.S;
			break;
		case "MS":
			time = TIME.MS;
			break;
		case "NS":
			time = TIME.NS;
			break;
		}
		return time;
	}

	private String getUnit(TIME time) {
		switch (time) {
		case S:
			return "s";

		case MS:
			return "ms";

		case NS:
			return "ns";
		}
		return null;
	}

}
