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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

import GraphTheoryAIDemo.Loading.TIME;
import GraphTheoryAIDemo.MinMaxTicTacToe.DIFFICULTY;

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
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicTacToe extends JFrame implements IGamePlay, IMinMaxConsoleClass, ActionListener {

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

	private int[][] tictactoe_gameboard;

	private PriorityQueue<GameBoardNode> gameboard_states;

	private JPanel cell_panel;
	public JLabel player1_score_label;
	public JLabel player2_score_label;
	private JLabel tie_score_label;
	public JLabel playertwo_label;
	public JLabel playerone_label;
	private JTextPane moveHistoryTP;
	private MinMaxTicTacToe minMaxClass;
	private Timer timer;

	private JCheckBox chkBox_AI_TIME;

	private String AI_TIME;
	private JCheckBox chckbxAlphabeta;
	private  ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel date_label;
	private  ButtonGroup buttonGroup_1 = new ButtonGroup();
	private DIFFICULTY difficulty;

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
		timer = new Timer(100, this);
		timer.start();
		initGameBoards();
		this.array = new JButton[3][3];
		setMaximumSize(new Dimension(1400, 1400));
		setResizable(false);
		setTitle("TicTacToe - GraphTeoryDemo - Gabriel Martinez");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1352, 735);
		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmRestart = new JMenuItem("Restart");
		mntmRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
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
		});
		mnFile.add(mntmRestart);
		
		JSeparator separator_3 = new JSeparator();
		mnFile.add(separator_3);

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

		chkBox_AI_TIME = new JCheckBox("Show AI elapsed time");
		chkBox_AI_TIME.setSelected(true);
		mnSettings.add(chkBox_AI_TIME);

		JSeparator separator_1 = new JSeparator();
		mnSettings.add(separator_1);

		chckbxAlphabeta = new JCheckBox("Alpha-Beta");
		mnSettings.add(chckbxAlphabeta);

		JSeparator separator_2 = new JSeparator();
		mnSettings.add(separator_2);

		JMenu mnTimeUnit = new JMenu("Time unit");
		mnSettings.add(mnTimeUnit);

		JCheckBox chckbxSeconds = new JCheckBox("Seconds (s)");
		chckbxSeconds.setSelected(true);
		chckbxSeconds.setActionCommand("S");
		buttonGroup.add(chckbxSeconds);
		mnTimeUnit.add(chckbxSeconds);

		JCheckBox chckbxMillisecondsms = new JCheckBox("Milliseconds (ms)");
		chckbxMillisecondsms.setActionCommand("MS");
		buttonGroup.add(chckbxMillisecondsms);
		mnTimeUnit.add(chckbxMillisecondsms);

		JCheckBox chckbxNanosecondsns = new JCheckBox("Nanoseconds (ns)");
		chckbxNanosecondsns.setActionCommand("NS");
		buttonGroup.add(chckbxNanosecondsns);
		mnTimeUnit.add(chckbxNanosecondsns);
		
		JSeparator separator_4 = new JSeparator();
		mnSettings.add(separator_4);
		
		JMenu mnDifficulty = new JMenu("Difficulty");
		mnSettings.add(mnDifficulty);
		
		JCheckBox chckbxEasy = new JCheckBox("Easy");
		chckbxEasy.setActionCommand("Easy");
		chckbxEasy.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty = DIFFICULTY.EASY;
			}
		});
		buttonGroup_1.add(chckbxEasy);
		mnDifficulty.add(chckbxEasy);
		
		JCheckBox chckbxIntermediate = new JCheckBox("Intermediate");
		chckbxIntermediate.setActionCommand("Intermediate");
		chckbxIntermediate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				difficulty = DIFFICULTY.INTERMEDIATE;
			}
		});
		buttonGroup_1.add(chckbxIntermediate);
		mnDifficulty.add(chckbxIntermediate);
		
		JCheckBox chckbxHard = new JCheckBox("Hard");
		buttonGroup_1.add(chckbxHard);
		chckbxHard.setText("Hard");
		chckbxHard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				difficulty = DIFFICULTY.HARD;
			}
		});
		chckbxHard.setSelected(true);
		mnDifficulty.add(chckbxHard);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 7));

		contentPane.setOpaque(false);
		contentPane.setEnabled(false);
		contentPane.setBackground(new Color(255, 255, 255));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		cell_panel = new JPanel();
		cell_panel.setBackground(Color.WHITE);
		cell_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(128, 128, 128), new Color(192, 192, 192)));
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
		tt_00.setForeground(Color.WHITE);
		tt_00.setBackground(Color.BLUE);
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
		panel_bottom_display.setBounds(10, 544, 1474, 130);
		contentPane.add(panel_bottom_display);
		panel_bottom_display.setLayout(null);

		start_btn = new JButton("Start Game");
		start_btn.setBounds(478, 11, 496, 108);
		panel_bottom_display.add(start_btn);
		start_btn.setForeground(new Color(0, 0, 255));
		start_btn.setFont(new Font("Impact", Font.BOLD, 30));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(0, 0, 1484, 136);
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
		AI_checkbox.setBounds(216, 102, 253, 23);
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

		date_label = new JLabel("");
		date_label.setFont(new Font("Tahoma", Font.ITALIC, 20));
		date_label.setHorizontalAlignment(SwingConstants.CENTER);
		date_label.setForeground(new Color(0, 153, 204));
		date_label.setBounds(1020, 52, 286, 31);
		panel_1.add(date_label);

		JLabel lblGabrielMartinezntictactoe = new JLabel("Gabriel Martinez | MinMax | Tic-Tac-Toe");
		lblGabrielMartinezntictactoe.setHorizontalTextPosition(SwingConstants.CENTER);
		lblGabrielMartinezntictactoe.setHorizontalAlignment(SwingConstants.CENTER);
		lblGabrielMartinezntictactoe.setForeground(new Color(0, 153, 204));
		lblGabrielMartinezntictactoe.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblGabrielMartinezntictactoe.setBounds(1020, 102, 304, 23);
		panel_1.add(lblGabrielMartinezntictactoe);

		JLabel lblAiDemo = new JLabel("AI Demo");
		lblAiDemo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAiDemo.setForeground(new Color(0, 153, 204));
		lblAiDemo.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblAiDemo.setBounds(977, 9, 347, 31);
		panel_1.add(lblAiDemo);
		// player_turn_label.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144), 5));
		scrollPane.setBounds(988, 11, 348, 529);
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
		this.difficulty = DIFFICULTY.HARD;
		//System.out.println(this.buttonGroup_1.getSelection().getActionCommand());
		//this.difficulty = setDifficulty(this.buttonGroup_1.getSelection().getActionCommand());
	}
	private DIFFICULTY setDifficulty(String diff) {
		if(diff.equalsIgnoreCase(DIFFICULTY.EASY.toString().toLowerCase())){
		return DIFFICULTY.EASY;
		}else if (diff.equalsIgnoreCase(DIFFICULTY.INTERMEDIATE.toString().toLowerCase())){
			return DIFFICULTY.INTERMEDIATE;
		}else{
			return DIFFICULTY.HARD;
		}
	}

	/**
	 * Init data structures
	 */
	private void initGameBoards() {
		this.tictactoe_gameboard = null;
		this.gameboard_states = null;
		this.tictactoe_gameboard = new int[ROW][ROW];
		for (int i = 0; i < ROW; ++i) {
			for (int j = 0; j < ROW; ++j) {
				this.tictactoe_gameboard[i][j] = Integer.MIN_VALUE;
			}
		} // all negative
		this.gameboard_states = new PriorityQueue<>();
		this.createAIClass();
	}

	/**
	 * 
	 * @param player
	 *            is player
	 * @param location
	 *            is a string "01"...means tictac[0][1]
	 */
	protected void makeMove(TURN player, String location) {
		if (this.start_btn.isEnabled()) return;// game not started

		/////////// make physical move
		String turn = TURN.PLAYER1.equals(player) ? "X" : "O";
		int row = Integer.parseInt(location.toCharArray()[0] + "");
		int col = Integer.parseInt(location.toCharArray()[1] + "");
		//System.out.println("BOARD:: ");
		//this.gameboard_states.peek().printBoard();
		if (this.tictactoe_gameboard[row][col] != Integer.MIN_VALUE) {
			System.out.println("Spots taken!");
			return;
		}

		JButton b = this.array[row][col];
		b.setText(turn);
		// b.setEnabled(false);
		b.setForeground(TURN.PLAYER1.equals(player) ? this.playerone_label.getForeground() : this.playertwo_label.getForeground());
		this.tictactoe_gameboard[row][col] = TURN.PLAYER1.equals(player) ? this.PLAYER_ONE_MOVE_VALUE : this.PLAYER_TWO_MOVE_VALUE;
		System.out.println("GAMESTATES: " + this.gameboard_states.size());

		this.gameboard_states.add(new GameBoardNode(this.tictactoe_gameboard, player, this.gameboard_states.size()));
		if (TURN.PLAYER1.equals(player)) {//only display if PLAYER 1
			this.displayCurrentBoard(this.moveHistoryTP, this.gameboard_states.peek(), "");
		} else {
			this.displayCurrentBoard(this.moveHistoryTP, this.gameboard_states.peek(), AI_TIME == null ? "" : AI_TIME);
		}

		//////////////////// Analyze move if game is over or not
		int isOver = this.isGameOver(tictactoe_gameboard, player);
		if (isOver != this.minMaxClass.GAME_NOT_OVER) {
			displayWinner(isOver);
			return;
		} else {
			this.player_turn = TURN.PLAYER1.equals(player) ? TURN.PLAYER2 : TURN.PLAYER1;
			this.displayPlayerTurn(player_turn);//label
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
		this.gameboard_states.add(new GameBoardNode(this.tictactoe_gameboard, this.player_turn, this.gameboard_states.size()));
		this.displayCurrentBoard(this.moveHistoryTP, this.gameboard_states.peek(), "");
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
			makeMoveAI(this.tictactoe_gameboard, player,this.difficulty);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int isGameOver(int[][] board, TURN player) {
		int winner = this.minMaxClass.check4Winner();
		if (winner == this.minMaxClass.PLAYER_WIN) {
			return this.minMaxClass.PLAYER_WIN;
		} else if (winner == this.minMaxClass.COMPUTER_WIN) {
			return this.minMaxClass.COMPUTER_WIN;
		} else if (winner == this.minMaxClass.TIE) { return this.minMaxClass.TIE; }
		return this.minMaxClass.GAME_NOT_OVER;
	}

	public void displayWinner(int winner) {
		if (winner == this.minMaxClass.TIE) {
			this.tie_score += 1;
			this.tie_score_label.setText(this.tie_score + "");
			JOptionPane.showMessageDialog(null, "It's a draw.");
			this.restart();
			return;
		} else if (winner == this.minMaxClass.PLAYER_WIN) {
			System.out.println("GAME OVER");
			JOptionPane.showMessageDialog(null, this.playerone_label.getText() + " wins!!!" + "\n\nGAME OVER.");
		} else if (winner == this.minMaxClass.COMPUTER_WIN) {
			System.out.println("GAME OVER");
			JOptionPane.showMessageDialog(null, this.playertwo_label.getText() + " wins!!!" + "\n\nGAME OVER.");
		}
		updateAndRestart(winner);
	}

	private void updateAndRestart(int winner) {
		int cw = this.minMaxClass.COMPUTER_WIN;
		int p = this.minMaxClass.PLAYER_WIN;
		if (winner == p) {
			this.player1_score += 1;
		} else if (winner == cw) {
			this.player2_score += 1;
		}
		this.player1_score_label.setText(this.player1_score + "");
		this.player2_score_label.setText(this.player2_score + "");
		this.restart();
	}

	/**
	 * {@inheritDoc} reset the cell buttons and start buttons
	 */
	public void restart() {
		/*if (t != null && t.isRunning()) t.stop();
		else if (t == null) t = new Timer(100, this);*/
		
		for (Component c : this.cell_panel.getComponents()) {
			JButton b = (JButton) c;
			b.setEnabled(true);
			b.setText("");
			//b.setBackground(Color.);
		}
		this.start_btn.setEnabled(true);
		this.AI_checkbox.setEnabled(true);
		this.initGameBoards();
	}

	/**
	 * {@inheritDoc}
	 */
	public void displayCurrentBoard(Component C, GameBoardNode node, String time) {
		JTextPane tp = (JTextPane) C;
		// System.out.println("Board: \n" + node.toString());
		String text = tp.getText().length() == 0 ? "" : (tp.getText() + "\n\n");
		tp.setText(text + node.toString() + "\nRound: " + node.getRound() + "\nPlayer: " + getPlayerName(node.getPlayer()) + (time.length() == 0 ? "" : "\nTime: " + time));
	}

	private String getPlayerName(TURN player) {
		if (TURN.PLAYER1.equals(player)) return PLAYER1;
		return PLAYER2;
	}

	/**
	 * Uses a method from MinMaxTicTacToe class generates a move.
	 */
	@Override
	public void makeMoveAI(int[][] board, TURN player, DIFFICULTY diff) {
		AIMOVE loc;
		this.player_turn = TURN.PLAYER2;
		boolean isTime = chkBox_AI_TIME.isSelected();
		if (isTime && this.chckbxAlphabeta.isSelected()) {
			loc = alphaBeta(board, player, true, true);
		} else if (isTime && !this.chckbxAlphabeta.isSelected()) {///just min max
			loc = alphaBeta(board, player, false, true);
		} else if (this.chckbxAlphabeta.isSelected()) {
			loc = alphaBeta(board, player, true, false);
		} else {
			loc = alphaBeta(board, player, false, false);
		}
		int row=loc.getRow();
		int col = loc.getColumn();
		this.tictactoe_gameboard[row][col]=Integer.MIN_VALUE;
		System.out.println("AI made move at: " + loc.toString());
		this.array[row][col].doClick();
	}

	/**
	 * Helper method SEE makeMoveAI
	 * 
	 * @param board
	 * @param player
	 */
	private AIMOVE alphaBeta(int[][] board, TURN player, boolean ab, boolean timer) {
		AIMOVE loc;
		if (timer) {
			Loading loading = new Loading(board, player, this.minMaxClass);
			new Thread(new Runnable() {
				@Override
				public void run() {
					loading.setVisible(true);
				}
			}).start();
			if (ab) {
				loc = loading.makeMoveAI(true, this.buttonGroup.getSelection().getActionCommand(), difficulty);
				AI_TIME = loading.getElapsedTime();
				return loc;
			} else {
				loc = loading.makeMoveAI(false, this.buttonGroup.getSelection().getActionCommand(),difficulty);
				AI_TIME = loading.getElapsedTime();
				return loc;
			}

		} else {//no timer
			if (ab) {
				loc = this.minMaxClass.AITurn(board,this.difficulty);
			} else {
				loc = this.minMaxClass.AITurn(board, false,this.difficulty);
			}
		}
		return loc;
		/*
		 * int row = loc[0], col = loc[1]; int move = (row * ROW) + col; int cell = board[move];
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean drawGame(int[][] board) {
		return this.minMaxClass.check4Winner() == this.minMaxClass.TIE;
	}

	/**
	 * {@inheritDoc}
	 */
	public void createAIClass() {
		this.minMaxClass = new MinMaxTicTacToe(this.tictactoe_gameboard, this.PLAYER_ONE_MOVE_VALUE, this.PLAYER_TWO_MOVE_VALUE);
	}

	/*private void glowButtons(String btn, String btn2, String btn3) {
		SwingUtilities.invokeLater(new Runnable() {
			int glow = 0;
			JButton b = array[btn.toCharArray()[0]][btn.toCharArray()[1]];
			JButton b2 = array[btn2.toCharArray()[0]][btn2.toCharArray()[1]];
			JButton b3 = array[btn3.toCharArray()[0]][btn3.toCharArray()[1]];

			@Override
			public void run() {
				timer = new Timer(100, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {

					}
				});//
				timer.start();
			}
		});
	}
*/
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd, YY h:mm:ss a");
		this.date_label.setText(sdf.format(new Date()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isThreeInARow(int[][] board, TURN player) {
		if (TURN.PLAYER1.equals(player)) return this.minMaxClass.check4Winner() == this.minMaxClass.PLAYER_WIN;
		if (TURN.PLAYER2.equals(player)) return this.minMaxClass.check4Winner() == this.minMaxClass.COMPUTER_WIN;
		return false;
	}
}
