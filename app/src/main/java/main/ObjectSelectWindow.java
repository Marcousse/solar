package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * UI window for selecting physics object.
 */
public class ObjectSelectWindow extends JDialog {

	JTextField objectField;

	public ObjectSelectWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "Select Object", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		add(panel1);
		add(panel2);

		objectField = new JTextField(10);
		JLabel objectLabel = new JLabel("Name:");
		panel1.add(objectLabel);
		panel1.add(objectField);

		JButton setLockButton = new JButton("Select");
		setLockButton.addActionListener((ActionEvent e) -> {
			PhysicsObject object = mainWindow.getSystem().findObject(objectField.getText());
			if (object != null)
				mainWindow.getController().setCameraLock(object);
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