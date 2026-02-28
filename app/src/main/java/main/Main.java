package main;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Entry point, instantiates controller.
 */
public class Main {
	public static void main(String args[]) {
		Controller controller = new Controller();
		controller.newObject("Sun", 99860026, 99860026, 6963, new Vector2(0, 0), new Vector2(0, 0), Color.ORANGE);
		controller.newObject("GILGAMESH CONSUMER OF REALITY", 99860026, 99860026, 6963, new Vector2(-780000, 57000), new Vector2(244, 0), Color.RED);
		//controller.newObject("Singularity", 999999999, 999999999, 0, new Vector2(-780000, 57000), new Vector2(244, 0), Color.BLACK);
		controller.newObject("Mercury", 17, 17, 24, "Sun", 57000, Color.ORANGE);
		controller.newObject("Venus", 244, 244, 60, "Sun", 108000, Color.YELLOW);
		controller.newObject("Earth", 300, 300, 63, "Sun", 149000, Color.BLUE);
		controller.newObject("Moon", 1, 1, 2, "Earth", 100, Color.GRAY); //not to scale
		controller.newObject("Mars", 32, 32, 33, "Sun", 228000, Color.RED);
		controller.newObject("Phobos", 1, 1, 2, "Mars", 100, Color.GRAY); //not to scale
		controller.newObject("Jupiter", 95317, 95317, 699, "Sun", 780000, Color.ORANGE);
		controller.newObject("Saturn", 28539, 28539, 582, "Sun", 1437000, Color.ORANGE);
		controller.newObject("Neptune", 5142, 5142, 246, "Sun", 2871000, Color.BLUE);
		controller.newObject("Uranus", 4358, 4358, 256, "Sun", 4530000, Color.BLUE);
	}
}
