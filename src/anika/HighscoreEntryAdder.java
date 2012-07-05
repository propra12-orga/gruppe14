package anika;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HighscoreEntryAdder extends JDialog {

	private static final long serialVersionUID = 24842794925957890L;

	List<Highscore> scores;

	Player p;

	JTextField name;

	public HighscoreEntryAdder(Player p, List<Highscore> scores) {
		super();
		this.scores = scores;
		this.p = p;
		this.setLayout(null);
		this.setSize(400, 190);
		this.setResizable(false);
		this.setTitle("Highscore - Name eingeben");
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		name = new JTextField();
		JLabel l1 = new JLabel("Bitte Namen für Spieler " + p.getId()
				+ " eingeben:");
		JLabel l2 = new JLabel(
				"(Nur Buchstaben oder Zahlen, maximal 25 Zeichen");
		l1.setBounds(10, 10, 390, 30);
		l2.setBounds(10, 40, 390, 30);
		name.setBounds(10, 70, 290, 30);
		name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				addEntry();
			}
		});

		JButton ok = new JButton("OK");
		ok.setBounds(150, 110, 100, 50);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				addEntry();
			}
		});
		add(l1);
		add(l2);
		add(name);
		add(ok);
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	}

	/**
	 * Checks whether die name is okay, and disposes of the window if it is. If
	 * not, the text field will be colored red.
	 */
	public void addEntry() {
		if (name.getText().matches("[a-zA-Z0-9]{1,25}")) {
			scores.add(new Highscore(p.getScore(), name.getText(), true));
			this.dispose();
		} else
			name.setBackground(Color.RED);
	}
}
