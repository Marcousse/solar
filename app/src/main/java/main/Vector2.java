package main;

import java.io.Serializable;

/**
 * 2D vector
 */
public class Vector2 implements Serializable {
	private static final long serialVersionUID = 1L;
	public double x;
	public double y;

    /**
     * Constructor. x and y initialised to 0
     */
	public Vector2() {
		this.x = 0.0;
		this.y = 0.0;
	}

    /**
     * Constructor. Specific x and y according to parameters.
     *
     * @param x
     * @param y
     */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

    /**
     * Compares two vectors, returns true if their x and y values are equal.
     *
     * @param v Vector2 object to compare.
     * @return True if vectors are equal.
     */
	public boolean equals(Vector2 v) {
		if (this.x == v.x && this.y == v.y)
			return true;
		else
			return false;
	}

    /**
     * Adds x and y values, returns new vector.
     *
     * @param v Vector2 object to add.
     * @return New Vector2 object.
     */
	public Vector2 add(Vector2 v) {
		return new Vector2(this.x + v.x, this.y + v.y);
	}
	
    /**
     * Subtracts x and y values, returns new vector.
     *
     * @param v Vector2 object to subtract.
     * @return New Vector2 object.
     */
	public Vector2 subtract(Vector2 v) {
		return new Vector2(this.x - v.x, this.y - v.y);
	}

    /**
     * Scalar multiplication.
     *
     * @param d Scalar value to multiply by.
     * @return New Vector2 object.
     */
	public Vector2 multiply(double d) {
		return new Vector2(this.x * d, this.y * d);
	}

    /**
     * Dot product of two vectors.
     *
     * @param v Another Vector2 object.
     * @return Dot product value.
     */
	public double dotProduct(Vector2 v) {
		return (this.x * v.x) + (this.y * v.y);
	}

    /**
     * Calculates length of vector.
     *
     * @return Length/magnitude of vector.
     */
	public double length() {
		return (double) Math.sqrt(x * x + y * y);
	}

    /**
     * Normalize vector.
     *
     * @return Normalized vector of length 1, or zero vector if vecor has length 0.
     */
	public Vector2 normalize() {
		double length = this.length();
		if (length == 0)
			return new Vector2(0, 0);
		else
			return new Vector2(this.x / length, this.y / length);
	}

    /**
     * Calculates parallel component with respect to vector passed as parameter.
     *
     * @param v2 Another Vector2 object.
     * @return Parallel component of vector.
     */
	public Vector2 parallelComponent(Vector2 v2) {
		return v2.multiply((this.dotProduct(v2)) / (Math.pow(v2.length(), 2)));
	}

    /**
     * String representing vcetor.
     *
     * @return A string representation of the vector.
     */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
