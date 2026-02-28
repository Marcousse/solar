package main;

import java.util.ArrayList;

/**
 * Advances simulation by specified time step. Compares every object to every
 * other object to calculate gravitational force, then applies impulse.
 * Calculates collisions. Advances position according to velocity vectors.
 */
public class BruteForce implements PhysicsStep {

	private static final double MOMENTUM_LOSS = 0.9;

	/**
	 * Advances simulation by one time step.
	 */
	public void step(ArrayList<PhysicsObject> objectArray, double timeStep) {
		for (PhysicsObject gravObj1 : objectArray) {
			int index1 = objectArray.indexOf(gravObj1);
			for (PhysicsObject gravObj2 : objectArray.subList(index1 + 1, objectArray.size())) {
				Vector2 pos1 = gravObj1.getPosition();
				Vector2 pos2 = gravObj2.getPosition();
				Vector2 diffVector = pos2.subtract(pos1);
				double distance = diffVector.length();
				double radius1 = gravObj1.getRadius();
				double radius2 = gravObj2.getRadius();
				// collision
				if (distance < (radius1 + radius2)) {

					Vector2 direction = diffVector.normalize();

					gravObj1.setPosition(
							gravObj1.getPosition().add(direction.multiply(-((radius1 + radius2) - distance) * 0.5)));
					gravObj2.setPosition(
							gravObj2.getPosition().add(direction.multiply(((radius1 + radius2) - distance) * 0.5)));

					Vector2 velocity1 = gravObj1.getVelocity();
					Vector2 velocity2 = gravObj2.getVelocity();

					Vector2 parallelVel1 = velocity1.parallelComponent(diffVector);
					Vector2 parallelVel2 = velocity2.parallelComponent(diffVector);

					double parallelSpeed1 = parallelVel1.length();
					double parallelSpeed2 = parallelVel2.length();

					double m1 = gravObj1.getInerMass();
					double m2 = gravObj2.getInerMass();

					// accurate elastic collision doesn't work with y values

					double newParallelSpeed1 = (Math.abs(m1 - m2) / (m1 + m2)) * parallelSpeed1
							+ ((2 * m2) / (m1 + m2)) * parallelSpeed2;
					double newParallelSpeed2 = ((2 * m1) / (m1 + m2)) * parallelSpeed1
							- (Math.abs(m1 - m2) / (m1 + m2)) * parallelSpeed2;

					// gravObj1.addVelocity(parallelVel1.multiply(-2).multiply(1));
					// gravObj2.addVelocity(parallelVel2.multiply(-2).multiply(1));

					gravObj1.addVelocity(direction.multiply(-2 * newParallelSpeed1).multiply(MOMENTUM_LOSS));
					gravObj2.addVelocity(direction.multiply(2 * newParallelSpeed2).multiply(MOMENTUM_LOSS));
				}
			}
		}

		// add force via gravity
		for (int i = 0; i < objectArray.size(); i++) {
			PhysicsObject gravObj1 = objectArray.get(i);
			Vector2 pos1 = gravObj1.getPosition();
			double gravMass1 = gravObj1.getGravMass();
			double radius1 = gravObj1.getRadius();

			for (int j = i + 1; j < objectArray.size(); j++) {
				PhysicsObject gravObj2 = objectArray.get(j);
				Vector2 pos2 = gravObj2.getPosition();
				double gravMass2 = gravObj2.getGravMass();

				Vector2 diffVector = pos2.subtract(pos1);
				double distance = diffVector.length();

				// gravity calculations
				double forceMagnitude = 10 * ((gravMass1 * gravMass2) / (distance * distance));
				Vector2 impulseVector = diffVector.normalize().multiply(forceMagnitude);
				gravObj1.addImpulse(impulseVector.multiply(timeStep));
				gravObj2.addImpulse(impulseVector.multiply(-1).multiply(timeStep));
			}
		}

		// move according to velocity
		for (PhysicsObject currentObj : objectArray) {
			Vector2 currentPos = currentObj.getPosition();
			Vector2 currentVel = currentObj.getVelocity();
			currentObj.setPosition(currentPos.add(currentVel.multiply(timeStep)));
		}
	}
}