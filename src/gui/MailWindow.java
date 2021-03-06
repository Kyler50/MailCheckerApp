package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class MailWindow extends JFrame {
	
	private static final long serialVersionUID = -4832689964557632067L;
	private ArrayList<String> emailText;
	JPanel panel = new JPanel();
	public MailWindow(ArrayList<String> emailText){
		this.emailText = emailText;
		initUI();
	}
	private void initUI() {

		panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		final JTextArea textBox = new JTextArea(40, 50);
		textBox.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane (textBox);
		scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		panel.add(scroll);

		for(int i = 0; i < emailText.size(); i++){
			textBox.setText(textBox.getText()+emailText.get(i)+"\n");
		}

		add(panel);
		pack();
		setTitle("Mail Checker");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}

