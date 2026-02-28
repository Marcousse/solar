package main;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Controls interactions between classes. Receives user input from Frame and
 * calls appropriate methods in system and physics. Physics and graphics updates
 * bypass Controller class.
 */
public class Controller {
	Physics physics;
	InertialSystem system;
	Frame frame;

	/**
	 * Constructer. References to new InertialSystem, Physics, and Frame object.
	 */
	public Controller() {
		this.system = new InertialSystem();
		this.physics = new Physics(system);
		this.frame = new Frame(system, this);
	}

	/**
	 * Saves current system to file through FileManager.
	 *
	 * @param file File to save system to.
	 */
	public void save(File file) throws FileNotFoundException, IOException {
		FileManager.save(system, file);
	}

	/**
	 * Loads system from file through FileManager.
	 *
	 * @param file File containing saved system.
	 */
	public void load(File file) throws FileNotFoundException, ClassNotFoundException, IOException {
		InertialSystem newSystem = (InertialSystem) FileManager.load(file);
		this.system = new InertialSystem();
		this.physics = new Physics(newSystem);
		frame.dispose();
		this.frame = new Frame(newSystem, this);
	}

	/**
	 * Creates new empty system.
	 */
	public void newSystem() {
		physics.terminateSimulation();
		this.system = new InertialSystem();
		this.physics = new Physics(system);
		frame.dispose();
		this.frame = new Frame(system, this);
	}

	/**
	 * Adds new object to system orbiting another object.
	 *
	 * @param name        Object name.
	 * @param gravMass    Object gravitational mass.
	 * @param inerMass    Object inertail mass.
	 * @param radius      Object radius.
	 * @param orbitObject Name of the object around which new object will orbit.
	 * @param orbitRadius Orbital radius of new object.
	 * @param color       Object color.
	 */
	public void newObject(String name, double gravMass, double inerMass, double radius, String orbitObject,
			double orbitRadius, Color color) {
		system.addObject(name, gravMass, inerMass, radius, system.findObject(orbitObject), orbitRadius, color);
	}

	/**
	 * Adds new object to system with specified position and velocity vector.
	 *
	 * @param name     Object name.
	 * @param gravMass Object gravitational mass.
	 * @param inerMass Object intertial mass.
	 * @param radius   Object radius.
	 * @param position Object position.
	 * @param velocity Object velocity.
	 * @param color    Object color.
	 */
	public void newObject(String name, double gravMass, double inerMass, double radius, Vector2 position,
			Vector2 velocity, Color color) {
		system.addObject(name, gravMass, inerMass, radius, position, velocity, color);
	}

	/**
	 * Deletes object with specified name from system.
	 *
	 * @param objectName Object name
	 */
	public void deleteObject(String objectName) {
		PhysicsObject object = system.findObject(objectName);
		system.deleteObject(object);
	}
	
	/**
	 * Deletes selected object.
	 */
	public void deleteSelectedObject() {
		system.deleteSelectedObject();
	}

	/**
	 * Pauses or starts simulation.
	 *
	 * @param paused	True for pause, false for unpause.
	 */
	public void setPhysicsPaused(boolean paused) {
		physics.setPaused(paused);
	}

    /**
     * Returns whether simulation is paused.
     *
     * @return			True if paused, false if running.
     */
	public boolean isPhysicsPaused() {
		return physics.isPaused();
	}

    /**
     * Sets simulation time step.
     *
     * @param timeStep	Time step duration in milliseconds.
     */
	public void setPhysicsTimeStep(double timeStep) {
		physics.setTimeStep(timeStep);
	}

	/**
     * Advances simulation by one step.
     */
	public void simulationStepForeward() {
		physics.update();
	}

	/**
     * Steps back in simulation.
     */
	public void simulationStepBack() {
		physics.rollBack();
	}

    /**
     * Sets selected object.
     *
     * @param object	Object to be selected.
     */
	public void setCameraLock(PhysicsObject object) {
		system.setSelectedObject(object);
	}

    /**
     * Sets camera position.
     *
     * @param position The new position for the camera.
     */
	public void setCameraPos(Vector2 position) {
		system.setCameraPos(position);
	}

    /**
     * Sets camera magnification.
     *
     * @param zoom		Magnification level. Default is 1. The greater the more zoomed out.
     */
	public void setCameraZoom(double zoom) {
		system.setCameraZoom(zoom);
	}

	/**
     * Locks camera.
     */
	public void lockCamera() {
		system.setCameraLocked(true);
	}

	/**
     * Free camera.
     */
	public void freeCamera() {
		system.setCameraLocked(false);
	}

    /**
     * Sets simulation speed.
     *
     * @param speed		Simulation speed.
     */
	public void setSimulationSpeed(double speed) {
		physics.setSpeed(speed);
	}
}
