package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Main UI Window, receives user input and communicates with controller.
 */
public class Frame extends JFrame {

	private static final double ZOOM_SPEED = 0.2;

	private InertialSystem system;
	private Controller controller;
	private int graphicsDelay;
	private Timer graphicsTimer;
	private File currentFile;
	private boolean isFullScreen;

	private ViewPortPanel viewPortPanel;

	public Frame(InertialSystem system, Controller controller) {

		// add reference to current system
		this.system = system;
		this.controller = controller;
		currentFile = null;
		isFullScreen = false;

		// basic frame settings
		setTitle("Universe Sandbox TESCO Edition");
		ImageIcon icon = new ImageIcon("elmo.png");
		this.setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		// create panel which contains the simulation
		viewPortPanel = new ViewPortPanel(system);
		add(viewPortPanel);

		// add menu Bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu simulationMenu = new JMenu("Simulation");
		JMenu cameraMenu = new JMenu("Camera");

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			if (currentFile == null)
				JOptionPane.showMessageDialog(null, "No file location specified for current system.", "Error", 0);
			else
				try {
					controller.save(currentFile);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "System could not be saved.", "Error", 0);
				}
			controller.setPhysicsPaused(physicsState);
		});

		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showSaveDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					controller.save(file);
					currentFile = file;
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "System could not be saved.", "Error", 0);
				}
			}
			controller.setPhysicsPaused(physicsState);
		});

		JMenuItem load = new JMenuItem("Load");
		load.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			JFileChooser fileChooser = new JFileChooser();
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					controller.load(file);
					currentFile = file; // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "System could not be loaded.", "Error", 0);
				}
			}
			controller.setPhysicsPaused(physicsState);
		});

		JMenuItem newSystem = new JMenuItem("New");
		newSystem.addActionListener((ActionEvent e) -> {
			controller.newSystem();
		});

		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(load);
		fileMenu.add(newSystem);

		JMenuItem newObject = new JMenuItem("New Object");
		newObject.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new NewObjPosWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem newObjectInOrbit = new JMenuItem("New Object In Orbit");
		newObjectInOrbit.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new NewObjOrbitWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem deleteObject = new JMenuItem("Delete Object");
		deleteObject.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new DeleteObjWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem selectObject = new JMenuItem("Select object");
		selectObject.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new ObjectSelectWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});

		editMenu.add(newObject);
		editMenu.add(newObjectInOrbit);
		editMenu.add(selectObject);
		editMenu.add(deleteObject);

		JMenuItem simulationMode = new JMenuItem("Simulation Mode");
		simulationMode.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			// simulation mode window
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem simulationSpeed = new JMenuItem("Simulation Speed");
		simulationSpeed.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new SimulationSpeedWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem simulationTimeStep = new JMenuItem("Time step length");
		simulationTimeStep.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new TimeStepWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem simulationStepForeward = new JMenuItem("Step foreward");
		simulationStepForeward.addActionListener((ActionEvent e) -> {
			controller.simulationStepForeward();
		});
		JMenuItem simulationStepBack = new JMenuItem("Step back");
		simulationStepBack.addActionListener((ActionEvent e) -> {
			controller.simulationStepBack();
		});
		JMenuItem simulationPause = new JMenuItem("Pause");
		simulationPause.addActionListener((ActionEvent e) -> {
			controller.setPhysicsPaused(true);
		});
		JMenuItem simulationStart = new JMenuItem("Start");
		simulationStart.addActionListener((ActionEvent e) -> {
			controller.setPhysicsPaused(false);
		});
		simulationMenu.add(simulationMode);
		simulationMenu.add(simulationSpeed);
		simulationMenu.add(simulationTimeStep);
		simulationMenu.add(simulationStepForeward);
		simulationMenu.add(simulationStepBack);
		simulationMenu.add(simulationPause);
		simulationMenu.add(simulationStart);

		JMenuItem cameraPosition = new JMenuItem("Position");
		cameraPosition.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new CameraPosWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem cameraZoom = new JMenuItem("Zoom");
		cameraZoom.addActionListener((ActionEvent e) -> {
			boolean physicsState = controller.isPhysicsPaused();
			controller.setPhysicsPaused(true);
			new CameraZoomWindow(this, true);
			controller.setPhysicsPaused(physicsState);
		});
		JMenuItem cameraLock = new JMenuItem("Lock camera");
		cameraLock.addActionListener((ActionEvent e) -> {
			controller.lockCamera();
		});
		JMenuItem cameraFree = new JMenuItem("Free camera");
		cameraFree.addActionListener((ActionEvent e) -> {
			controller.freeCamera();
		});
		cameraMenu.add(cameraPosition);
		cameraMenu.add(cameraZoom);
		cameraMenu.add(cameraLock);
		cameraMenu.add(cameraFree);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(simulationMenu);
		menuBar.add(cameraMenu);
		setJMenuBar(menuBar);

		// timer
		graphicsDelay = 20;
		graphicsTimer = new Timer();
		graphicsTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				update();
			}
		}, 0, graphicsDelay);

		// key shortcuts
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				double moveSpeed = 1 / system.getCamera().getZoom();
				switch (keyCode) {
				case KeyEvent.VK_UP:
					controller
							.setCameraPos(system.getCamera().getPosition().add(new Vector2(0, 10).multiply(moveSpeed)));
					break;
				case KeyEvent.VK_DOWN:
					controller.setCameraPos(
							system.getCamera().getPosition().add(new Vector2(0, -10).multiply(moveSpeed)));
					break;
				case KeyEvent.VK_RIGHT:
					controller
							.setCameraPos(system.getCamera().getPosition().add(new Vector2(10, 0).multiply(moveSpeed)));
					break;
				case KeyEvent.VK_LEFT:
					controller.setCameraPos(
							system.getCamera().getPosition().add(new Vector2(-10, 0).multiply(moveSpeed)));
					break;
				case KeyEvent.VK_A:
					controller.simulationStepForeward();
					break;
				case KeyEvent.VK_D:
					controller.simulationStepBack();
					break;
				case KeyEvent.VK_ESCAPE:
					controller.freeCamera();
					break;
				case KeyEvent.VK_DELETE:
					controller.deleteSelectedObject();
					break;
				case KeyEvent.VK_F11:
					toggleFullScreen();
					break;
				}
			}
		});

		this.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				double currentZoom = system.getCamera().getZoom();
				double newZoom = currentZoom - Math.signum(e.getWheelRotation()) * currentZoom * ZOOM_SPEED;
				controller.setCameraZoom(newZoom);
			}
		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void toggleFullScreen() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		if (isFullScreen) {
			graphicsDevice.setFullScreenWindow(null);
			isFullScreen = false;
		} else {
			graphicsDevice.setFullScreenWindow(this);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			isFullScreen = true;
		}
	}

	/**
	 * Getter for controller.
	 * 
	 * @return Controller.
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Updates display. Calls repaint method on viewport.
	 */
	public void update() {
		viewPortPanel.repaint();
	}

	/**
	 * Getter for system.
	 * 
	 * @return Controller.
	 */
	public InertialSystem getSystem() {
		return system;
	}

	/**
	 * Setter for system.
	 *
	 * @param system	New inertial system.
	 */
	public void setSystem(InertialSystem system) {
		this.system = system;
		this.viewPortPanel.setSystem(system);
	}
}