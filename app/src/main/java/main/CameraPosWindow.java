package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * UI window for setting camera position.
 */
public class CameraPosWindow extends JDialog {

	JTextField xField;
	JTextField yField;

	public CameraPosWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "Position", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		add(panel1);
		add(panel2);

		xField = new JTextField(3);
		JLabel coordLabel = new JLabel("Position(x,y):");
		yField = new JTextField(3);
		panel1.add(coordLabel);
		panel1.add(xField);
		panel1.add(yField);

		JButton setLockButton = new JButton("Set");
		setLockButton.addActionListener((ActionEvent e) -> {
			mainWindow.getController().setCameraPos(
					new Vector2(Double.parseDouble(xField.getText()), Double.parseDouble(yField.getText())));
			dispose();
		});
		panel2.add(setLockButton);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				mainWindow.setEnabled(true);
				mainWindow.requestFocus();
			}
		});
		pack();
		setVisible(true);
	}
}