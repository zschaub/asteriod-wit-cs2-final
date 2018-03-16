package finalProject;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Laser extends ImageView implements HitBox {

	static int sizeX = 1;
	static int sizeY = 10;
	double velocity = 10;
	double angle;
	String fileName;

	public Laser(double positionX, double positionY, double rotate) {
		this("/finalProject/defaultLaser.png", positionX, positionY, rotate);
	}

	public Laser(String fileName, double positionX, double positionY, double rotate) {
		super(new Image(fileName, sizeX, sizeY, false, true));

		this.fileName = fileName;
		positionX = positionX - sizeX / 2;
		positionY = positionY - sizeY / 2;
		this.setX(positionX);
		this.setY(positionY);

		this.setRotate(rotate);
	}

	public void update() {
		double vX = Math.cos((this.getRotate() - 90) * Math.PI / 180) * velocity;
		double vY = Math.sin((this.getRotate() - 90) * Math.PI / 180) * velocity;

		this.setX(this.getX() + vX);
		this.setY(this.getY() + vY);

	}

	public boolean isOffScreen() {
		if ((this.getX() < (0 - sizeX)) || (this.getX() > GameMain.sceneSize)) {
			return true;
		}

		if ((this.getY() < (0 - sizeY)) || (this.getY() > GameMain.sceneSize)) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Shape> bounds() {
		Rectangle shape = new Rectangle(this.getX(), this.getY(), sizeX, sizeY);
		shape.setRotate(this.getRotate());
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(shape);
		return shapes;
	}

}
