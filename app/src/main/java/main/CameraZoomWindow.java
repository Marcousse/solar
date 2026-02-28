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
 * UI window for setting camera magnification.
 */
public class CameraZoomWindow extends JDialog {

	JTextField zoomField;

	public CameraZoomWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "Zoom", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		add(panel1);
		add(panel2);

		zoomField = new JTextField(10);
		JLabel zoomLabel = new JLabel("Zoom:");
		panel1.add(zoomLabel);
		panel1.add(zoomField);

		JButton setLockButton = new JButton("Set");
		setLockButton.addActionListener((ActionEvent e) -> {
			mainWindow.getController().setCameraZoom(Double.parseDouble(zoomField.getText()));
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