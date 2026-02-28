package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * UI window for creating new object in orbit around another object.
 */
public class NewObjOrbitWindow extends JDialog {

	JTextField nameField;
	JTextField sizeField;
	JTextField inerMassField;
	JTextField gravMassField;
	JComboBox<Color> colorPicker;
	JTextField orbitField;
	JTextField distanceField;
	JTextField angleField;

	public NewObjOrbitWindow(Frame mainWindow, boolean isModal) {
		super(mainWindow, "New Object", isModal);
		mainWindow.setEnabled(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainWindow);
		setResizable(false);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		add(panel6);
		add(panel7);

		nameField = new JTextField(10);
		JLabel nameLabel = new JLabel("Name:");
		panel1.add(nameLabel);
		panel1.add(nameField);

		sizeField = new JTextField(5);
		JLabel sizeLabel = new JLabel("Size:");
		JCheckBox sizeLock = new JCheckBox();
		sizeLock.setToolTipText("size proportionate to mass");
		panel2.add(sizeLabel);
		panel2.add(sizeField);
		panel2.add(sizeLock);

		Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA,
				Color.PINK, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.WHITE, Color.BLACK };
		colorPicker = new JComboBox<Color>(colors);
		JLabel colorLabel = new JLabel("Color:");
		JCheckBox colorLock = new JCheckBox();
		colorLock.setToolTipText("color according to mass proportion");
		panel3.add(colorLabel);
		panel3.add(colorPicker);
		panel3.add(colorLock);

		inerMassField = new JTextField(5);
		JLabel inerMassLabel = new JLabel("Inertial Mass:");
		gravMassField = new JTextField(5);
		JLabel gravMassLabel = new JLabel("Gravitational Mass:");
		JCheckBox massLock = new JCheckBox();
		sizeLock.setToolTipText("Inertal and gravitational mass equal");
		panel4.add(inerMassLabel);
		panel4.add(inerMassField);
		panel4.add(gravMassLabel);
		panel4.add(gravMassField);
		panel4.add(massLock);

		JLabel orbitLabel = new JLabel("Object to orbit:");
		orbitField = new JTextField(5);
		panel5.add(orbitLabel);
		panel5.add(orbitField);

		JLabel distanceLabel = new JLabel("Orbital distance");
		distanceField = new JTextField(5);

		panel6.add(distanceLabel);
		panel6.add(distanceField);

		JButton createButton = new JButton("Create");
		createButton.addActionListener((ActionEvent e) -> {
			String name = nameField.getText();
			double gravMass = Double.parseDouble(gravMassField.getText());
			double inerMass = Double.parseDouble(inerMassField.getText());
			double radius = Double.parseDouble(sizeField.getText());
			String orbitObject = orbitField.getText();
			double orbitRadius = Double.parseDouble(distanceField.getText());
			Color color = (Color) colorPicker.getSelectedItem();
			mainWindow.getController().newObject(name, gravMass, inerMass, radius, orbitObject, orbitRadius, color);
			dispose();
		});
		panel7.add(createButton);

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
