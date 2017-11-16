package GraphTheoryAIDemo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JColorChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import say.swing.JFontChooser;

public class EditNamesDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField player1tf;
	private JTextField player2tf;
	private JColorChooser colorChooser;
	private JFontChooser fontChooser;



	/**
	 * Create the dialog.
	 */
	public EditNamesDialog(TicTacToe game) {
		
		setBounds(100, 100, 503, 539);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 481, 266);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 493, 505);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel player2label = new JLabel("Player Two");
		player2label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				player2label.setForeground(colorChooser.getColor());
				player2label.setFont(fontChooser.getSelectedFont());
			}
		});
		player2label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		player2label.setHorizontalAlignment(SwingConstants.TRAILING);
		player2label.setBounds(137, 50, 145, 31);
		panel.add(player2label);
		
		JLabel player1label = new JLabel("Player One");
		player1label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				player1label.setForeground(colorChooser.getColor());
				player1label.setFont(fontChooser.getSelectedFont());
			}
		});
		player1label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		player1label.setHorizontalAlignment(SwingConstants.TRAILING);
		player1label.setBounds(137, 22, 145, 31);
		panel.add(player1label);
		
		player1tf = new JTextField();
		player1tf.setText("Player One");
		player1tf.setBounds(292, 28, 191, 20);
		panel.add(player1tf);
		player1tf.setColumns(10);
		
		player2tf = new JTextField();
		player2tf.setText("Player Two");
		player2tf.setColumns(10);
		player2tf.setBounds(292, 61, 191, 20);
		panel.add(player2tf);
		
		 colorChooser = new JColorChooser();
		colorChooser.setBounds(0, 92, 486, 174);
		panel.add(colorChooser);
		
		JButton btnNewButton = new JButton("Save Changes");
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.playerone_label.setText(player1tf.getText());
				game.playertwo_label.setText(player2tf.getText());
				game.playerone_label.setForeground(player1label.getForeground());
				game.playertwo_label.setForeground(player2label.getForeground());
				game.player1_score_label.setForeground(player1label.getForeground());
				game.player2_score_label.setForeground(player2label.getForeground());
				game.playerone_label.setFont(player1label.getFont());
				game.playertwo_label.setFont(player2label.getFont());
				game.player1_score_label.setFont(player1label.getFont());
				game.player2_score_label.setFont(player2label.getFont());
				
				dispose();
			}
		});
		btnNewButton.setBounds(10, 11, 117, 72);
		panel.add(btnNewButton);
		
		 fontChooser = new JFontChooser();
		fontChooser.setBounds(0, 266, 481, 223);
		getContentPane().add(fontChooser);
		this.setTitle("Edit Player Names");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setModal(true);
	}
}
