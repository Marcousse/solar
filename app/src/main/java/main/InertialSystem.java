package main;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Current state of a system of inertia containing PhysicsObjects and Camera
 * object.
 */
public class InertialSystem implements Serializable {
	private static final long serialVersionUID = 1L;
	ArrayList<PhysicsObject> objectArray;
	Camera camera;
	PhysicsObject selectedObject;

	/**
	 * Constructor. No objects, creates camera at default position (0,0).
	 */
	public InertialSystem() {
		objectArray = new ArrayList<PhysicsObject>();
		camera = new Camera(null, 1, new Vector2(0, 0));
		selectedObject = null;
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
	public void addObject(String name, double gravMass, double inerMass, double radius, Vector2 position,
			Vector2 velocity, Color color) {
		objectArray.add(new PhysicsObject(this, name, gravMass, inerMass, radius, position, velocity, color));
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
	public void addObject(String name, double gravMass, double inerMass, double radius, PhysicsObject orbitObject,
			double orbitRadius, Color color) {
		if (orbitObject != null) {
			Vector2 orbitObjectVelocity = orbitObject.getVelocity();
			Vector2 orbitCenter = orbitObject.getPosition();
			double centerMass = orbitObject.getGravMass();
			double centripetalAcceleration = 10 * centerMass / (orbitRadius * orbitRadius);
			double orbitalSpeed = Math.sqrt(centripetalAcceleration * orbitRadius);

			Vector2 velocity = new Vector2(0, orbitalSpeed).add(orbitObjectVelocity);

			Vector2 position = new Vector2(orbitCenter.x + orbitRadius, orbitCenter.y);

			objectArray.add(new PhysicsObject(this, name, gravMass, inerMass, radius, position, velocity, color));
		}
	}

	/**
	 * Setter for objectArray.
	 *
	 * @param objectArray New array of PhysicsObjects.
	 */
	public void setObjectArray(ArrayList<PhysicsObject> objectArray) {
		this.objectArray = objectArray;
	}

	/**
	 * Getter for camera.
	 *
	 * @return Camera object associated with system.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Sets camera zoom.
	 *
	 * @param zoom New camera zoom.
	 */
	public void setCameraZoom(double zoom) {
		camera.setZoom(zoom);
	}

	/**
	 * Sets position of camera.
	 *
	 * @param position New position.
	 */
	public void setCameraPos(Vector2 position) {
		camera.setPosition(position);
	}

	/**
	 * Finds and returns PhysicsObject based on name.
	 *
	 * @param objectName Object name.
	 * @return Found object. Null if no object is found.
	 */
	public PhysicsObject findObject(String objectName) {
		for (PhysicsObject object : objectArray) {
			if (object.getName().equals(objectName)) {
				return object;
			}
		}
		return null;
	}

	/**
	 * Deletes a specific physics object.
	 *
	 * @param object Object to be deleted.
	 */
	public void deleteObject(PhysicsObject object) {
		if (object == selectedObject) {
			camera.setLockedObject(null);
			selectedObject = null;
		}
		objectArray.remove(object);
	}

	/**
	 * Deletes selected object.
	 */
	public void deleteSelectedObject() {
		objectArray.remove(selectedObject);
		camera.setLockedObject(null);
		selectedObject = null;
	}

	/**
	 * Sets selected physics object.
	 *
	 * @param object The object to be selected.
	 */
	public void setSelectedObject(PhysicsObject object) {
		selectedObject = object;
		camera.setLockedObject(object);
	}

	/**
	 * Gets selected object.
	 *
	 * @return The currently selected object.
	 */
	public PhysicsObject getSelectedObject() {
		return selectedObject;
	}

	/**
	 * Sets whether camera is locked to selected object.
	 *
	 * @param locked True for locked, false for free camera.
	 */
	public void setCameraLocked(boolean locked) {
		if (locked)
			camera.setLockedObject(selectedObject);
		else
			camera.setLockedObject(null);
	}

	/**
	 * Gets objectArray.
	 *
	 * @return Array of objects.
	 */
	public ArrayList<PhysicsObject> getObjectArray() {
		return objectArray;
	}
}
