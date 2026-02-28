package main;

import java.awt.Color;
import java.io.Serializable;

/**
 * Represents a physics object with properties of mass, position, velocity etc.
 */
public class PhysicsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private InertialSystem system;
	private String name;
	private double gravMass;
	private double inerMass;
	private double radius;
	private Vector2 position;
	private Vector2 velocity;
	private Color color;

	/**
	 * Constructs PhysicsObject.
	 *
	 * @param system   InertialSystem to which the object belongs.
	 * @param name     Name of object.
	 * @param mass     Mass of object.
	 * @param radius   Radius of object.
	 * @param position Position in space.
	 * @param velocity Velocity vector.
	 * @param color    Color used to represent object.
	 */
	public PhysicsObject(InertialSystem system, String name, double mass, double radius, Vector2 position,
			Vector2 velocity, Color color) {
		this.system = system;
		this.gravMass = mass;
		this.inerMass = mass;
		this.radius = radius;
		this.position = position;
		this.velocity = velocity;
		this.color = color;
		this.name = name;
	}

	/**
	 * Constructs PhysicsObject.
	 *
	 * @param system   InertialSystem to which the object belongs.
	 * @param name     Name of object.
	 * @param gravMass Gravitational mass.
	 * @param inerMass Inertial mass.
	 * @param radius   Radius of object.
	 * @param position Position in space.
	 * @param velocity Velocity vector.
	 * @param color    Color used to represent object.
	 */
	public PhysicsObject(InertialSystem system, String name, double gravMass, double inerMass, double radius,
			Vector2 position, Vector2 velocity, Color color) {
		this.system = system;
		this.gravMass = gravMass;
		this.inerMass = inerMass;
		this.radius = radius;
		this.position = position;
		this.velocity = velocity;
		this.color = color;
		this.name = name;
	}

	/**
	 * Getter for system.
	 *
	 * @return InertialSystem to which the object belongs.
	 */
	public InertialSystem getSystem() {
		return system;
	}

	/**
	 * Getter for color.
	 *
	 * @return Color of object.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter for radius.
	 *
	 * @return Radius of object.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Getter for gravitational mass.
	 *
	 * @return Gravitational mass of object.
	 */
	public double getGravMass() {
		return gravMass;
	}

	/**
	 * Getter for inertial mass.
	 *
	 * @return Inertial mass of object.
	 */
	public double getInerMass() {
		return inerMass;
	}

	/**
	 * Getter for position of object.
	 *
	 * @return Position of object.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Getter for position.
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

    /**
     * Getter for velocity.
     *
     * @return Velocity of object.
     */
	public Vector2 getVelocity() {
		return velocity;
	}

    /**
     * Getter for name.
     *
     * @return Name of object.
     */
	public String getName() {
		return name;
	}

    /**
     * Applies impulse vector to object.
     *
     * @param impulse Impulse to apply.
     */
	public void addImpulse(Vector2 impulse) {
		this.velocity = velocity.add(impulse.multiply(1 / inerMass));
	}

    /**
     * Adds velocity vector to object's velocity.
     *
     * @param velocity Velocity vector to add.
     */
	public void addVelocity(Vector2 velocity) {
		this.velocity = this.velocity.add(velocity);
	}
}
