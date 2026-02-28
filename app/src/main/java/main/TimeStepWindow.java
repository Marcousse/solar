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
 * UI window for setting simulation time step.
 */
public class TimeStepWindow extends JDialog {

	JTextField timeStepField;

	public TimeStepWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "Time step length", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		add(panel1);
		add(panel2);

		timeStepField = new JTextField(5);
		JLabel timeStepLabel = new JLabel("Time step length:");
		panel1.add(timeStepLabel);
		panel1.add(timeStepField);

		JButton setLockButton = new JButton("Set");
		setLockButton.addActionListener((ActionEvent e) -> {
			mainWindow.getController().setPhysicsTimeStep(Double.parseDouble(timeStepField.getText()));
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