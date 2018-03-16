package finalProject;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/*
 * Asteroid Sizes going from smallest to largest
 * Small
 * Medium
 * Large
*/

public abstract class Asteroid extends ImageView implements Destroyable<Asteroid>, HitBox {

	protected double size;
	protected double velocity;
	protected double angle; // in radians out of 2PI
	/*
	 * PI/2
	 * 
	 * 
	 * PI 0 or 2PI
	 * 
	 * 
	 * 3PI/2
	 */
	Random rnd = new Random();
	protected String fileName;

	protected Asteroid() {
		this("finalProject/defaultAsteroid.png", 0);
	}

	// Constructor to make Asteroid with image of fileName and size of size, Random
	// Position and Velocity
	protected Asteroid(String fileName, double size) {
		super(new Image(fileName, size, size, false, true));
		this.size = size;
		this.fileName = fileName;
		rndPosition();
		rndVelocity();
	}

	// Contructor to make Asteroid with image of fileName, size, position in both x
	// and y, velocity and angle of velocity
	protected Asteroid(String fileName, double size, double positionX, double positionY, double velocity,
			double angle) {
		super(new Image(fileName, size, size, false, true));
		this.size = size;
		this.fileName = fileName;
		this.setX(positionX);
		this.setY(positionY);

		this.velocity = velocity;
		this.angle = angle;
	}

	// Sets Asteroid to random position along the edge of the screen
	private void rndPosition() {
		int xy = rnd.nextInt(1);
		int side = rnd.nextInt(1);

		if (xy == 0) {
			this.setX(rnd.nextInt((int) (GameMain.sceneSize)));
			if (side == 0) {
				this.setY(0 - size);
			} else
				this.setY((int) (GameMain.sceneSize));
		} else {
			this.setY(rnd.nextInt((int) (GameMain.sceneSize)));
			if (side == 0) {
				this.setX(0 - size);
			} else
				this.setX((int) (GameMain.sceneSize));
		}
	}

	// Sets a random Velocity and Angle
	private void rndVelocity() {
		velocity = rnd.nextDouble() + 1;
		angle = rnd.nextDouble() * Math.PI * 2;
	}

	// updates position of Asteroid
	public void update() {

		double vX = Math.cos(angle) * velocity;
		double vY = Math.sin(angle) * velocity;

		this.setX(this.getX() + vX);
		this.setY(this.getY() + vY);

		if (this.getX() < (0 - size)) {
			this.setX(GameMain.sceneSize);
		} else if (this.getX() > GameMain.sceneSize) {
			this.setX(0 - size);
		}

		if (this.getY() < (0 - size)) {
			this.setY(GameMain.sceneSize);
		} else if (this.getY() > GameMain.sceneSize) {
			this.setY(0 - size);
		}
	}

	public abstract int getScore();

	// What the asteroid turns into if destroyed
	public abstract ArrayList<Asteroid> destroyed();

	// What is the bounds and shape of the asteroid
	public ArrayList<Shape> bounds() {
		ArrayList<Shape> boundList = new ArrayList<Shape>(1);
		boundList.add(new Circle(this.getX() + size / 2, this.getY() + size / 2, size / 2));
		return boundList;
	}
}
