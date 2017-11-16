package GraphTheoryAIDemo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.annotation.Inherited;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;

public class TicTacToe extends JFrame implements IGamePlay, ISpeedyMatrix, IMinMaxConsoleClass {

	public final int PLAYER_ONE_MOVE_VALUE = 1, PLAYER_TWO_MOVE_VALUE = 2;

	public final static String AI = "AI";
	public String PLAYER2 = "Player Two";
	public String PLAYER1 = "Player One";
	static final int ROW = 3;
	private int player1_score = 0;
	private int player2_score = 0;
	private int tie_score = 0;

	private JCheckBox AI_checkbox;

	public enum TURN {
		PLAYER1, PLAYER2
	};// denotes possible turns when checking next turn

	private TURN player_turn;// keeps track of current turn

	private JLabel player_turn_label;// change name on each turn
	private JButton[][] array;// display buttons
	private JButton start_btn;

	private int[] tictactoe_gameboard;

	private PriorityQueue<GameBoardNode> gameboard_states;

	private JPanel cell_panel;
	public JLabel player1_score_label;
	public JLabel player2_score_label;
	private JLabel tie_score_label;
	public JLabel playertwo_label;
	public JLabel playerone_label;
	private JTextPane moveHistoryTP;
	private MinMaxTicTacToe minMaxClass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe frame = new TicTacToe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Inittialize game board and array of tictac
	 */
	public TicTacToe() {

		initGameBoards();
		this.array = new JButton[3][3];
		setMaximumSize(new Dimension(1400, 1400));
		setResizable(false);
		setTitle("TicTacToe - GraphTeoryDemo - Gabriel Martinez");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1500, 735);
		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mnFile.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
			}
		});

		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);

		JMenuItem mntmEditPlayerNames = new JMenuItem("Edit Player Names");
		mntmEditPlayerNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditNamesDialog e = new EditNamesDialog(TicTacToe.this);
				e.setVisible(true);
			}
		});
		mnSettings.add(mntmEditPlayerNames);

		JSeparator separator = new JSeparator();
		mnSettings.add(separator);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 7));

		contentPane.setOpaque(false);
		contentPane.setEnabled(false);
		contentPane.setBackground(new Color(255, 255, 255));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		cell_panel = new JPanel();
		cell_panel
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(128, 128, 128), new Color(192, 192, 192)));
		cell_panel.setBounds(10, 11, 974, 529);
		contentPane.add(cell_panel);

		JButton tt_02 = new JButton("");
		tt_02.setContentAreaFilled(false);
		tt_02.setFont(new Font("Tahoma", Font.PLAIN, 99));
		tt_02.setActionCommand("02");
		tt_02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_02.getActionCommand());
			}
		});

		JButton tt_01 = new JButton("");
		tt_01.setContentAreaFilled(false);
		tt_01.setActionCommand("01");
		tt_01.setFont(new Font("Tahoma", Font.PLAIN, 99));
		tt_01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_01.getActionCommand());
			}
		});
		JButton tt_00 = new JButton("");
		tt_00.setContentAreaFilled(false);
		tt_00.setActionCommand("00");
		tt_00.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeMove(player_turn, tt_00.getActionCommand());
			}
		});
		cell_panel.setLayout(new GridLayout(0, 3, 0, 0));
		tt_00.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_00);
		this.array[0][0] = tt_00;
		cell_panel.add(tt_01);
		this.array[0][1] = tt_01;
		cell_panel.add(tt_02);
		this.array[0][2] = tt_02;

		JButton tt_20 = new JButton("");
		tt_20.setContentAreaFilled(false);
		tt_20.setActionCommand("20");
		tt_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_20.getActionCommand());
			}
		});

		JButton tt_10 = new JButton("");
		tt_10.setContentAreaFilled(false);
		tt_10.setActionCommand("10");
		tt_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_10.getActionCommand());
			}
		});
		tt_10.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_10);

		this.array[1][0] = tt_10;

		JButton tt_12 = new JButton("");
		tt_12.setContentAreaFilled(false);
		tt_12.setActionCommand("12");
		tt_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_12.getActionCommand());
			}
		});

		JButton tt_11 = new JButton("");
		tt_11.setContentAreaFilled(false);
		tt_11.setActionCommand("11");
		tt_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_11.getActionCommand());
			}
		});
		tt_11.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_11);
		this.array[1][1] = tt_11;
		tt_12.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_12);
		this.array[1][2] = tt_12;
		tt_20.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_20);

		this.array[2][0] = tt_20;

		JButton tt_22 = new JButton("");
		tt_22.setContentAreaFilled(false);
		tt_22.setActionCommand("22");
		tt_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_22.getActionCommand());
			}
		});

		JButton tt_21 = new JButton("");
		tt_21.setContentAreaFilled(false);
		tt_21.setActionCommand("21");
		tt_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeMove(player_turn, tt_21.getActionCommand());
			}
		});
		tt_21.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_21);
		this.array[2][1] = tt_21;
		tt_22.setFont(new Font("Tahoma", Font.PLAIN, 99));
		cell_panel.add(tt_22);
		this.array[2][2] = tt_22;

		JPanel panel_bottom_display = new JPanel();
		panel_bottom_display.setBounds(0, 544, 984, 136);
		contentPane.add(panel_bottom_display);
		panel_bottom_display.setLayout(null);

		start_btn = new JButton("Start Game");
		start_btn.setBounds(478, 11, 496, 119);
		panel_bottom_display.add(start_btn);
		start_btn.setForeground(new Color(0, 0, 255));
		start_btn.setFont(new Font("Impact", Font.BOLD, 30));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(0, 0, 984, 136);
		panel_bottom_display.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Tie");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(10, 52, 150, 31);
		panel_1.add(lblNewLabel_1);

		AI_checkbox = new JCheckBox("AI (SINGLE PLAYER)");
		AI_checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (AI_checkbox.isSelected()) {

					playertwo_label.setText(AI);
				} else {
					playertwo_label.setText(PLAYER2);
				}
				PLAYER2 = playertwo_label.getText();
			}
		});
		AI_checkbox.setBounds(216, 102, 253, 28);
		panel_1.add(AI_checkbox);
		AI_checkbox.setForeground(new Color(0, 0, 0));
		AI_checkbox.setBackground(new Color(255, 255, 255));
		AI_checkbox.setFont(new Font("Tahoma", Font.PLAIN, 25));

		playertwo_label = new JLabel(PLAYER2);
		playertwo_label.setBounds(10, 101, 150, 30);
		panel_1.add(playertwo_label);
		playertwo_label.setForeground(new Color(0, 255, 255));
		playertwo_label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		playertwo_label.setHorizontalAlignment(SwingConstants.TRAILING);

		player2_score_label = new JLabel("0");
		player2_score_label.setBounds(170, 99, 36, 30);
		panel_1.add(player2_score_label);
		player2_score_label.setForeground(new Color(0, 255, 255));
		player2_score_label.setFont(new Font("Tahoma", Font.PLAIN, 30));

		playerone_label = new JLabel(PLAYER1);
		playerone_label.setBounds(10, 11, 150, 30);
		panel_1.add(playerone_label);
		playerone_label.setBackground(new Color(0, 0, 0));
		playerone_label.setForeground(new Color(255, 0, 0));
		playerone_label.setHorizontalAlignment(SwingConstants.TRAILING);
		playerone_label.setFont(new Font("Tahoma", Font.PLAIN, 25));

		player1_score_label = new JLabel("0");
		player1_score_label.setBounds(170, 9, 36, 30);
		panel_1.add(player1_score_label);
		player1_score_label.setForeground(new Color(255, 0, 0));
		player1_score_label.setFont(new Font("Tahoma", Font.PLAIN, 30));

		tie_score_label = new JLabel("0");
		tie_score_label.setForeground(new Color(255, 255, 255));
		tie_score_label.setFont(new Font("Tahoma", Font.PLAIN, 30));
		tie_score_label.setBounds(170, 52, 36, 30);
		panel_1.add(tie_score_label);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setForeground(new Color(0, 128, 128));
		panel.setBackground(new Color(255, 255, 240));
		panel.setBounds(216, 11, 253, 84);
		panel_1.add(panel);
		panel.setLayout(null);

		player_turn_label = new JLabel("");
		player_turn_label.setBounds(0, 0, 253, 84);
		panel.add(player_turn_label);
		player_turn_label.setHorizontalAlignment(SwingConstants.CENTER);
		// player_turn_label.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144), 5));
		scrollPane.setBounds(988, 11, 496, 663);
		contentPane.add(scrollPane);

		moveHistoryTP = new JTextPane();
		moveHistoryTP.setEditable(false);
		scrollPane.setViewportView(moveHistoryTP);
		start_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AI_checkbox.setEnabled(false);
				start_btn.setEnabled(false);
				startGame();
			}
		});

	}

	/**
	 * Init data structures
	 */
	private void initGameBoards() {
		this.tictactoe_gameboard = new int[ROW * ROW];
		for (int i = 0; i < ROW * ROW; ++i) {
			this.tictactoe_gameboard[i] = Integer.MIN_VALUE;
		} // all negative
		this.gameboard_states = new PriorityQueue<>();
	}

	/**
	 * 
	 * @param player
	 *            is player
	 * @param location
	 *            is a string "01"...means tictac[0][1]
	 */
	protected void makeMove(TURN player, String location) {
		if (this.start_btn.isEnabled())
			return;// game not started

		/////////// make physical move
		String turn = TURN.PLAYER1.equals(player) ? "X" : "O";
		int row = Integer.parseInt(location.toCharArray()[0] + "");
		int col = Integer.parseInt(location.toCharArray()[1] + "");
		int move = this.convert2DIndex(row, col);
		if (this.tictactoe_gameboard[move] != Integer.MIN_VALUE) {
			System.out.println("Spots taken!");
			return;
		}

		JButton b = this.array[row][col];
		b.setText(turn);
		// b.setEnabled(false);
		b.setForeground(TURN.PLAYER1.equals(player) ? this.playerone_label.getForeground()
				: this.playertwo_label.getForeground());

		this.tictactoe_gameboard[move] = TURN.PLAYER1.equals(player) ? this.PLAYER_ONE_MOVE_VALUE
				: this.PLAYER_TWO_MOVE_VALUE;
		System.out.println("GAMESTATES: " + this.gameboard_states.size());
		this.gameboard_states.add(new GameBoardNode(this.tictactoe_gameboard, player, this.gameboard_states.size()));

		this.displayCurrentBoard(this.moveHistoryTP, this.gameboard_states.peek());
		//////////////////// Analyze move if game is over or not

		if (this.isGameOver(tictactoe_gameboard, player)) {

			return;
		} else {

			this.player_turn = TURN.PLAYER1.equals(player) ? TURN.PLAYER2 : TURN.PLAYER1;

			this.displayPlayerTurn(player_turn);
		}
	}

	/**
	 * Begin game
	 */
	private void startGame() {
		Random rand = new Random();

		if (rand.nextBoolean()) {
			this.player_turn = TURN.PLAYER1;
		} else {
			this.player_turn = TURN.PLAYER2;
		}
		this.gameboard_states
				.add(new GameBoardNode(this.tictactoe_gameboard, this.player_turn, this.gameboard_states.size()));
		this.displayCurrentBoard(this.moveHistoryTP, this.gameboard_states.peek());
		displayPlayerTurn(this.player_turn);

	}

	/**
	 * Will display current player turn.
	 * 
	 * @param player
	 */
	private void displayPlayerTurn(TURN player) {
		String name = TURN.PLAYER1.equals(player) ? PLAYER1 : PLAYER2;

		switch (player) {
		case PLAYER1:
			this.player_turn_label.setText(this.playerone_label.getText());
			this.player_turn_label.setFont(this.playerone_label.getFont());
			this.player_turn_label.setForeground(this.playerone_label.getForeground());

			break;
		case PLAYER2:
			this.player_turn_label.setText(this.playertwo_label.getText());
			this.player_turn_label.setFont(this.playertwo_label.getFont());
			this.player_turn_label.setForeground(this.playertwo_label.getForeground());

			break;
		}
		if (this.AI_checkbox.isSelected() && TURN.PLAYER2.equals(player)) { // game
																			// is
																			// against
																			// AI
																			// then
																			// run
																			// minmax
																			// algorithm
			System.out.println("AI turn! Please wait...");
			makeMoveAI(this.tictactoe_gameboard, player);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isGameOver(int[] board, TURN player) {
		if (this.isThreeInARow(board, player)) {
			System.out.println("GAME OVER");
			JOptionPane.showMessageDialog(null,
					TURN.PLAYER1.equals(player) ? PLAYER1 + " wins!!!" : PLAYER2 + " wins!!!" + "\n\nGAME OVER.");
			switch (player) {
			case PLAYER1:
				this.player1_score += 1;
				break;
			case PLAYER2:
				this.player2_score += 1;
				break;
			}
			this.player1_score_label.setText(this.player1_score + "");
			this.player2_score_label.setText(this.player2_score + "");
			this.restart();
		} else if (this.drawGame(tictactoe_gameboard)) {
			this.tie_score += 1;
			this.tie_score_label.setText(this.tie_score + "");
			JOptionPane.showMessageDialog(null, "It's a draw.");
			this.restart();
		} else {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc} reset the cell buttons and start buttons
	 */
	public void restart() {
		for (Component c : this.cell_panel.getComponents()) {
			JButton b = (JButton) c;
			b.setEnabled(true);
			b.setText("");
		}
		this.start_btn.setEnabled(true);
		this.AI_checkbox.setEnabled(true);
		this.initGameBoards();
	}

	/**
	 * {@inheritDoc}
	 */
	public void displayCurrentBoard(Component C, GameBoardNode node) {
		JTextPane tp = (JTextPane) C;
		// System.out.println("Board: \n" + node.toString());
		String text = tp.getText().length() == 0 ? "" : (tp.getText() + "\n\n");
		tp.setText(text + node.toString() + "\nRound: " + node.getRound() + "\nPlayer: "
				+ getPlayerName(node.getPlayer()));
	}

	private String getPlayerName(TURN player) {
		if (TURN.PLAYER1.equals(player))
			return PLAYER1;
		return PLAYER2;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isThreeInARow(int[] board, TURN player) {
		if (isHorizontalWin(board, player)) {
			return true;
		}
		if (isVerticalWin(board, player)) {
			return true;
		}
		if (isLeftDiagonalWin(board, player)) {
			return true;
		}
		if (isRightDiagonalWin(board, player)) {
			return true;
		}
		return false;
	}

	@Override
	public void makeMoveAI(int[] board, TURN player) {
		this.player_turn = TURN.PLAYER2;
		/*
		 * Random rand = new Random(); int row = rand.nextInt(ROW); int col =
		 * rand.nextInt(ROW);
		 */
		if (this.minMaxClass == null) {
			this.createAIClass();
		}
		System.out.println("arr: "+Arrays.toString(this.tictactoe_gameboard));
		int[] loc = this.minMaxClass.AITurn(board);
		int row = loc[0], col = loc[1];
		int move = (row * ROW) + col;
		int cell = board[move];
		/*
		 * if (cell > 0) {// keep picking move since spot is taken
		 * System.out.println("row,col: " + row + " " + col + "taken...");
		 * this.makeMoveAI(board, player); }
		 */
		System.out.println("AI mad move at: " + row + ", " + col + " => " + move);
		this.array[row][col].doClick();
	}

	/**
	 * {@inheritDoc}
	 */
	public int convert2DIndex(int row, int col) {
		return (row * ROW) + col;
	}

	@Override
	public boolean isLeftDiagonalWin(int[] board, TURN player) {
		int symbol = TURN.PLAYER1.equals(player) ? this.PLAYER_ONE_MOVE_VALUE : this.PLAYER_TWO_MOVE_VALUE; // who
																											// are
																											// we
																											// looking
		// for 1 or 2 => X
		// or O
		int occur = 0;
		int pos = 0;
		for (int i = 0; i < ROW; ++i) {
			int cell = board[pos];
			if (cell == symbol)
				occur += 1;
			pos += 4;
		}
		return occur == 3;
	}

	@Override
	public boolean isRightDiagonalWin(int[] board, TURN player) {
		int symbol = TURN.PLAYER1.equals(player) ? this.PLAYER_ONE_MOVE_VALUE : this.PLAYER_TWO_MOVE_VALUE; // who
																											// are
																											// we
																											// looking
		// for 1 or 2 => X
		// or O
		int occur = 0;
		int pos = 2;
		for (int i = 0; i < ROW; ++i) {
			int cell = board[pos];
			if (cell == symbol)
				occur += 1;
			pos += 2;
		}
		return occur == 3;
	}

	/**
	 * {@inheritDoc} iterates each row modulo ROW size restarts occurrence on
	 * each row..
	 */
	public boolean isHorizontalWin(int[] board, TURN player) {
		int symbol = TURN.PLAYER1.equals(player) ? this.PLAYER_ONE_MOVE_VALUE : this.PLAYER_TWO_MOVE_VALUE; // who
																											// are
																											// we
																											// looking
		// for 1 or 2 => X
		// or O
		int row_occurence = 0;
		for (int i = 0; i < board.length; ++i) {
			int cell = board[i];

			if (cell == symbol) {
				row_occurence += 1;
			}
			if (row_occurence == 3) {
				return true;
			}
			if ((i + 1) % 3 == 0) { // looks at end of row : i+1 => 3,6,9
				row_occurence = 0;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc} 3 chunks of iterations in one loop. cols it starts searchs
	 * each col until last one
	 */
	public boolean isVerticalWin(int[] board, TURN player) {
		int symbol = TURN.PLAYER1.equals(player) ? this.PLAYER_ONE_MOVE_VALUE : this.PLAYER_TWO_MOVE_VALUE; // who
																											// are
																											// we
																											// looking
		// for 1 or 2 => X
		// or O
		int v_occurence = 0;
		int col = 0;
		int counter = 0;
		int c = 1;// first iteration
		for (int i = 0; i < board.length; ++i) {
			counter += 1;
			// System.out.println("i: " + i + " ... " + col + " ... counter: " +
			// counter);
			int cell = board[col];
			col += 3;

			if (cell == symbol) {
				v_occurence += 1;
			}
			if (counter == 3 || col >= 9) {
				counter = 0;
				c += 1;
				if (v_occurence == 3) {
					System.out.println("VERT WIN!! c: " + (c - 1));
					return true;
				} else {
					v_occurence = 0;
				}
				switch (c) {
				case 2:
					col = 1;
					break;
				case 3:
					col = 2;
					break;
				}
			} ////////////////////////// end if counter
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean drawGame(int[] board) {

		for (int i = 0; i < board.length; ++i) {
			if (board[i] == Integer.MIN_VALUE)
				return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createAIClass() {
		this.minMaxClass = new MinMaxTicTacToe(this.tictactoe_gameboard, this.PLAYER_ONE_MOVE_VALUE,
				this.PLAYER_TWO_MOVE_VALUE);
	}
}
