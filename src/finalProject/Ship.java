package finalProject;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

// Needs to add update(), and figure out velocity and radians
public class Ship extends ImageView implements Destroyable<Ship>, HitBox {
	String fileName;
	final static double sizeX = 25;
	final static double sizeY = sizeX * 1.5;
	double velocity;
	double angle; // in radians out of 2PI
	/*
	 *             PI/2
	 * 
	 * 
	 *         PI         0 or 2PI
	 * 
	 * 
	 *             3PI/2
	 */
	// When ship respawns it is invincible for a short amount of time
	private int invincible = 0;
	public static int livesLeft = 3;

	public Ship() {
		this("finalProject/defaultShip.png");
	}

	Ship(String fileName) {
		this(fileName, GameMain.sceneSize / 2, (GameMain.sceneSize / 2), 0, 0, false);
	}

	Ship(String fileName, double positionX, double positionY, double velocity, double rotate, boolean isInvincible) {
		super(new Image(fileName, sizeX, sizeY, false, true));

		if (isInvincible) {
			this.invincible = 180;
		}
		this.fileName = fileName;
		positionX = positionX - sizeX / 2;
		positionY = positionY - sizeY / 2;
		this.setX(positionX);
		this.setY(positionY);

		this.velocity = velocity;
		this.setRotate(rotate);
	}

	public void update() {
		double vX = Math.cos((this.getRotate() - 90) * Math.PI / 180) * velocity;
		double vY = Math.sin((this.getRotate() - 90) * Math.PI / 180) * velocity;

		this.setX(this.getX() + vX);
		this.setY(this.getY() + vY);

		if (this.getX() < (0 - sizeX)) {
			this.setX(GameMain.sceneSize);
		} else if (this.getX() > GameMain.sceneSize) {
			this.setX(0 - sizeX);
		}

		if (this.getY() < (0 - sizeY)) {
			this.setY(GameMain.sceneSize);
		} else if (this.getY() > GameMain.sceneSize) {
			this.setY(0 - sizeY);
		}
	}

	public void thrust() {
		thrust(.075);
	}

	public void thrust(double force) {
		if (velocity < 6)
			velocity = velocity + force;
		if (velocity > 6) {
			velocity = 6;
		}
	}

	public void dethrust() {
		dethrust(.05);
	}

	public void dethrust(double force) {
		if (velocity > 0)
			velocity = velocity - force;
		if (velocity < 0)
			velocity = 0;
	}

	public void rotateRight() {
		rotateRight(5);
	}

	public void rotateRight(double rotate) {
		this.setRotate(this.getRotate() + rotate);
	}

	public void rotateLeft() {
		rotateLeft(5);
	}

	public void rotateLeft(double rotate) {
		this.setRotate(this.getRotate() - rotate);
	}

	public boolean isInvincible() {
		if (invincible > 0)
			return true;
		return false;
	}

	public void blink() {
		if (invincible % 15 == 0) {
			if (this.getImage() == null) {
				this.setImage(new Image(fileName, sizeX, sizeY, false, true));
			} else {
				this.setImage(null);
			}
		}
		if (invincible > 0)
			invincible = invincible - 1;
	}

	public Laser shootLaser() {
		double x = this.getX() + sizeX / 2;
		double y = this.getY() + sizeY / 2;
		x = x + Math.cos((this.getRotate() - 90) * Math.PI / 180) * sizeX / 2;
		y = y + Math.sin((this.getRotate() - 90) * Math.PI / 180) * sizeY / 2;
		Laser laser = new Laser(x, y, this.getRotate());
		return laser;
	}

	@Override
	public ArrayList<Ship> destroyed() {
		ArrayList<Ship> list = new ArrayList<Ship>(0);
		if (livesLeft > 1) {
			list.add(new Ship(fileName, GameMain.sceneSize / 2, (GameMain.sceneSize / 2), 0, 0, true));
			livesLeft = livesLeft - 1;
		}
		else
			livesLeft = 0;
		return list;
	}

	public ArrayList<Shape> bounds() {
		// 0
		// / \
		// / \
		// 1-----2

		double[] points = { (this.getX() + sizeX / 2), this.getY(), (this.getX()), this.getY() + sizeY,
				(this.getX() + sizeX), this.getY() + sizeY };
		ArrayList<Shape> list = new ArrayList<Shape>(1);
		list.add(new Polygon(points));
		return (list);
	}

}
