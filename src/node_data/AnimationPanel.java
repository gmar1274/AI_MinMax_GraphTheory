package node_data;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AnimationPanel extends JPanel {
	private final JTextField textField = new JTextField();
	private JTextField textField_1;
	private final JButton btnNewButton = new JButton("Start");

	/**
	 * Create the panel.
	 */
	public AnimationPanel(JFrame frame) {
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		this.setLayout(null);
		textField.setBounds(frame.getWidth()-400, 0, 350, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		
		textField_1.setColumns(10);
		textField_1.setBounds(frame.getWidth()-400, 25, 350, 20);
		add(textField_1);
		btnNewButton.setBounds(frame.getWidth()-150, frame.getHeight()-100, 120, 50);
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textField_1.setEditable(false);
				textField.setEditable(false);
				begin();
			}
			
		});
		add(btnNewButton);
		this.setVisible(true);
	}
/**
 * Begin program parse textfields
 */
	protected void begin() {
		// TODO Auto-generated method stub
		NGonTable three = new NGonTable(new VertexSet(textField.getText().toCharArray()));
		NGonTable ngon = new NGonTable(new VertexSet(textField_1.getText().toCharArray()));
		System.out.println(three);
		System.out.println(ngon);
		this.solveProblem(three, ngon);
	}
	/**
	 * 
	 * @param three 3 table
	 * @param ngon ngon Table
	 */
	public void solveProblem(NGonTable three, NGonTable ngon){
		
		//do table 3 first 
		int nights = ((three.getVertices().size() + ngon.getVertices().size())-1) /2;
		System.out.println("Total number of nights: "+nights);
		System.out.println(three.getVertices().size()+"-Table:");
		for(int i=0; i<nights; ++i){
			System.out.println("Night "+(i+1)+": ");
		}
		System.out.println(ngon.getVertices().size()+"-Table:");
		for(int i=0; i<nights; ++i){
			System.out.println("Night "+(i+1)+":");
		}
	}

}
