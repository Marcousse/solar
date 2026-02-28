package main;

import java.io.Serializable;

/**
 * Contains camera settings associated with system object, provides methods for
 * moving camera etc.
 */
public class Camera implements Serializable {

	private static final double MAX_ZOOM = 100.0;
	private static final double MIN_ZOOM = 0.001;

	private static final long serialVersionUID = 1L;

	PhysicsObject lockedObject;
	double zoom;
	Vector2 position;

	/**
	 * Constructor.
	 *
	 * @param lockedObject Object for camera to lock on to.
	 * @param cameraZoom   Initial magnification.
	 * @param cameraPos    Initial position.
	 */
	public Camera(PhysicsObject lockedObject, double cameraZoom, Vector2 cameraPos) {
		this.lockedObject = lockedObject;
		this.zoom = cameraZoom;
		this.position = cameraPos;
	}

	/**
	 * Gets object camera is locked to.
	 *
	 * @return Locked object.
	 */
	public PhysicsObject getLockedObject() {
		return lockedObject;
	}

	/**
	 * Sets object camera is locked to.
	 *
	 * @param lockedObject Object to be locked to.
	 */
	public void setLockedObject(PhysicsObject lockedObject) {
		this.lockedObject = lockedObject;
	}

	/**
	 * Sets position of camera.
	 *
	 * @param position New position vector.
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * Gets position of camera.
	 *
	 * @return Position of camera.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Sets zoom level of camera.
	 *
	 * @param zoom New zoom level. Default is 1
	 */
	public void setZoom(double zoom) {
		if (zoom > MIN_ZOOM)
			if (zoom < MAX_ZOOM)
				this.zoom = zoom;
			else
				this.zoom = MAX_ZOOM;
		else
			this.setZoom(MIN_ZOOM);
	}

	/**
	 * Gets zoom level of camera.
	 *
	 * @return Current zoom level.
	 */
	public double getZoom() {
		return zoom;
	}
}
