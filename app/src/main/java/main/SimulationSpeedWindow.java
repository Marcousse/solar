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
 * UI window for setting simulation speed.
 */
public class SimulationSpeedWindow extends JDialog {

	JTextField speedField;

	public SimulationSpeedWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "Simulation speed", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		add(panel1);
		add(panel2);

		speedField = new JTextField(10);
		JLabel speedLabel = new JLabel("Speed:");
		panel1.add(speedLabel);
		panel1.add(speedField);

		JButton setLockButton = new JButton("Set");
		setLockButton.addActionListener((ActionEvent e) -> {
			mainWindow.getController().setSimulationSpeed(Double.parseDouble(speedField.getText()));
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