package anika;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HighscoreList extends JDialog {

	private static final long serialVersionUID = 24842794925957890L;

	public HighscoreList(JFrame parent, List<Highscore> scores) {
		super();
		this.setLayout(null);
		this.setSize(300, 400);
		this.setResizable(false);
		this.setTitle("Highscores");
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		JTextArea panel = new JTextArea();
		panel.setEditable(false);
		panel.setFocusable(false);
		panel.setBackground(this.getBackground());
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (Highscore hs : scores) {
			sb.append(i++ + ". " + hs.toString() + "\n");
		}
		panel.setText(sb.toString());
		JScrollPane scroller = new JScrollPane(panel);
		scroller.setBounds(0, 0, 300, 300);
		this.getContentPane().add(scroller);
		JButton ok = new JButton("OK");
		ok.setBounds(100, 325, 100, 50);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		add(ok);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
