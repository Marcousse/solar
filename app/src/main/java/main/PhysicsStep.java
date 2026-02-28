package main;

import java.util.ArrayList;

/**
 * Interface for classes which advance the simulation.
 */
public interface PhysicsStep {
	public void step(ArrayList<PhysicsObject> objectArray, double millis);
}
