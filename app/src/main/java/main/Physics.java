package main;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages simulation, updates system at a specified rate.
 */
public class Physics {

	private static final int DEFAULT_DURATION = 10;
	private static final double DEFAULT_TIMESTEP = 0.01;

	private InertialSystem system;
	private boolean isPaused;
	private PhysicsStep simulationMode;
	private double timeStep;
	private Timer physicsTimer;

	/**
	 * Constructor.
	 *
	 * @param system InertialSystem object to update.
	 */
	public Physics(InertialSystem system) {
		this.system = system;
		isPaused = true;
		simulationMode = new BruteForce();
		timeStep = DEFAULT_TIMESTEP;
		physicsTimer = new Timer();
		physicsTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (!isPaused) {
					update();
				}
			}
		}, 0, 10);
	}

	/**
	 * Updates system by one step. Called by timer. Can be called through Frame by
	 * user.
	 */
	public void update() {
		simulationMode.step(system.getObjectArray(), timeStep);
	}

	/**
	 * Steps back one step. Called through Frame by user.
	 */
	public void rollBack() {
		// cannot rewind collisions
		simulationMode.step(system.getObjectArray(), -timeStep);
	}

	/**
	 * Pauses or resumes the simulation.
	 *
	 * @param isPaused True for pause, false for resume.
	 */
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	/**
	 * Returns whether simulation is paused.
	 *
	 * @return True if simulation is paused, false otherwise.
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * Stops timer permanently.
	 */
	public void terminateSimulation() {
		physicsTimer.cancel();
	}

	/**
	 * Sets simulation speed.
	 *
	 * @param speed Simulation speed.
	 */
	public void setSpeed(double speed) {
		physicsTimer.cancel();
		physicsTimer = new Timer();
		physicsTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (!isPaused) {
					update();
				}
			}
		}, 0, (int) (DEFAULT_DURATION / speed));
	}

	/**
	 * Sets simulation time step.
	 *
	 * @param timeStep Time step duration in milliseconds.
	 */
	public void setTimeStep(double timeStep) {
		this.timeStep = timeStep;
	}

	// Matrix of field strengths
	/*
	 * public Matrix<Vector2> gravField = new Matrix<Vector2>(10, 10);
	 * 
	 * public int cellSize = 10; public double GRAV = 1;
	 * 
	 * public void clearField() { int fieldColumnCount = gravField.getColumns(); int
	 * fieldRowCount = gravField.getRows();
	 * 
	 * for (int c = 0; c < fieldColumnCount; c++) { for (int r = 0; r <
	 * fieldRowCount; r++) { gravField.set(c, r, new Vector2(0, 0)); } } }
	 * 
	 * public void addToField(ArrayList<PhysicsObject> objectArray) { // adds the
	 * gravitational field strength of the object array to field
	 * 
	 * int fieldColumnCount = gravField.getColumns(); int fieldRowCount =
	 * gravField.getRows(); double halfCellSize = (double) cellSize / 2;
	 * 
	 * for (int i = 0; i < objectArray.size(); i++) { PhysicsObject currentObject =
	 * objectArray.get(i); double gravMass = currentObject.getGravMass(); Vector2
	 * objectPosition = currentObject.getPosition(); for (int c = 0; c <
	 * fieldColumnCount; c++) { // iterate through field for (int r = 0; r <
	 * fieldRowCount; r++) { Vector2 cellPosition = new Vector2(halfCellSize + c *
	 * cellSize, halfCellSize + r * cellSize); Vector2 CellToObject =
	 * cellPosition.subtract(objectPosition); double distance =
	 * objectPosition.length(); double accMagnitude = GRAV * (gravMass /
	 * Math.pow(distance, 2)); Vector2 accVector =
	 * CellToObject.normalize().multiply(accMagnitude); gravField.set(c, r,
	 * accVector); // set matrix cell to acceleration vector } }
	 * 
	 * } }
	 * 
	 * public void addToField(PhysicsObject object) { // same but single object }
	 * 
	 * public void removeFromField(ArrayList<PhysicsObject> objectArray) { //
	 * subtracts the same shit. 0 at least, can be negative (needed for negative //
	 * gravitational mass) }
	 * 
	 * public void removeFromField(PhysicsObject object) { // same but single object
	 * }
	 * 
	 * public void addImpulse(ArrayList<PhysicsObject> objectArray) { for (int i =
	 * 0; i < objectArray.size(); i++) { // calculate Vector 2 Vector2 impulseVector
	 * = new Vector2(0, 0); // just a random vector for now
	 * objectArray.get(i).addImpulse(impulseVector); } }
	 */
}
