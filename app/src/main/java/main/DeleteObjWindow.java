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
 * UI window for deleting objects from system.
 */
public class DeleteObjWindow extends JDialog {
	JTextField textField;
	JLabel label;
	JCheckBox deleteCurrent;

	public DeleteObjWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "Delete Object", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		add(panel1);
		add(panel2);

		label = new JLabel("Object name: ");
		textField = new JTextField(5);
		deleteCurrent = new JCheckBox();
		panel1.add(label);
		panel1.add(textField);
		panel1.add(deleteCurrent);

		JButton button = new JButton("Delete");
		panel2.add(button);
		button.addActionListener((ActionEvent e) -> {
			if (deleteCurrent.isSelected())
				mainWindow.getController().deleteSelectedObject();
			else
				mainWindow.getController().deleteObject(textField.getText());
			dispose();
		});

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