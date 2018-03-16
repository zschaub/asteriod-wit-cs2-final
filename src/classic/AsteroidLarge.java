package classic;

import java.util.ArrayList;

import finalProject.Asteroid;

public class AsteroidLarge extends Asteroid {
	public static final int DEFAULT_SIZE = 112;

	AsteroidLarge() {
		super("finalProject/defaultAsteroid.png", DEFAULT_SIZE);
		size = DEFAULT_SIZE;
	}

	AsteroidLarge(String fileName, double size) {
		super(fileName, size);
	}

	AsteroidLarge(String fileName, double size, double positionX, double positionY, double velocity, double angle) {
		super(fileName, size, positionX, positionY, velocity, angle);
		this.size = size;
	}

	AsteroidLarge(String fileName, double positionX, double positionY, double velocity, double angle) {
		super(fileName, DEFAULT_SIZE, positionX, positionY, velocity, angle);
	}

	@Override
	public ArrayList<Asteroid> destroyed() {
		ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>(2);

		asteroids.add(new AsteroidMedium(fileName, this.getX(), this.getY(), velocity, angle + Math.PI / 4));
		asteroids.add(new AsteroidMedium(fileName, this.getX(), this.getY(), velocity, angle - Math.PI / 4));
		return asteroids;
	}

	@Override
	public int getScore() {
		return 25;
	}

}
