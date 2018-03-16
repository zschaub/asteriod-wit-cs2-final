package finalProject;

import java.util.ArrayList;

import javafx.scene.shape.Shape;

public interface HitBox {
	// Returns Bounds of Object in the form of an ArrayList containing Shapes
	abstract public ArrayList<Shape> bounds();

	// Checks if the Object and the other shape had intersecting bounds
	public default boolean intersects(ArrayList<Shape> shape) {
		return HitBox.intersects(this.bounds(), shape);
	}

	public static boolean intersects(ArrayList<Shape> shape1, ArrayList<Shape> shape2) {
		boolean doesIntersect = false;

		for (Shape thisShape : shape1) {
			for (Shape otherShape : shape2) {
				if (thisShape.getBoundsInParent().intersects(otherShape.getBoundsInParent())) {
					doesIntersect = true;
				}
			}
		}
		return doesIntersect;
	}
}
