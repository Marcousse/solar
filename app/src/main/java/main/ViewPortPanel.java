package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Panel for rendering simulation.
 */
class ViewPortPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 400;

	private InertialSystem system;

	/**
	 * Constructor.
	 *
	 * @param system InertialSystem associated with Frame.
	 */
	public ViewPortPanel(InertialSystem system) {
		this.system = system;
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Render objects, names etc.
	 *
	 * @param g Graphics object.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		ArrayList<PhysicsObject> objectArray = system.getObjectArray();
		Camera camera = system.getCamera();
		double zoom = camera.getZoom();
		Vector2 cameraPosition;
		if (system.getCamera().getLockedObject() != null) {
			cameraPosition = camera.getLockedObject().getPosition();
		} else {
			cameraPosition = camera.getPosition();
		}
		for (PhysicsObject currentObj : objectArray) {

			// calculate position within viewport
			Vector2 currentObjPos = currentObj.getPosition();
			double radius = currentObj.getRadius();

			Vector2 relativePos = new Vector2((currentObjPos.x - cameraPosition.x) * zoom,
					(currentObjPos.y - cameraPosition.y) * zoom);

			Vector2 finalPos = new Vector2(relativePos.x + this.getWidth() * 0.5,
					this.getHeight() * 0.5 - relativePos.y);

			// draw object
			g2D.setColor(currentObj.getColor());
			g2D.fillOval((int) (finalPos.x - radius * zoom), (int) (finalPos.y - radius * zoom),
					(int) (radius * 2 * zoom), (int) (radius * 2 * zoom));

			// draw object name
			g2D.setColor(Color.WHITE);
			g2D.drawString(currentObj.getName(), (int) (finalPos.x), (int) (finalPos.y - radius * zoom));

			// give selected object white border
			if (currentObj == system.getSelectedObject()) {
				g2D.setColor(Color.WHITE);
				g2D.drawOval((int) ((finalPos.x - radius * zoom)), (int) ((finalPos.y - radius * zoom)),
						(int) (radius * 2 * zoom), (int) (radius * 2 * zoom));
			}
		}
	}

	/**
	 * Sets InertialSystem for panel to display.
	 *
	 * @param system New InertialSystem.
	 */
	public void setSystem(InertialSystem system) {
		this.system = system;
	}
}