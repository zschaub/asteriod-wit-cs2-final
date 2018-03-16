package classic;

import java.util.ArrayList;

import finalProject.Asteroid;

public class AsteroidMedium extends Asteroid {
	public static final int DEFAULT_SIZE = 75;

	AsteroidMedium() {
		super("finalProject/defaultAsteroid.png", DEFAULT_SIZE);
		size = DEFAULT_SIZE;
	}

	AsteroidMedium(String fileName, double size) {
		super(fileName, size);
	}

	AsteroidMedium(String fileName, double size, double positionX, double positionY, double velocity, double angle) {
		super(fileName, size, positionX, positionY, velocity, angle);
		this.size = size;
	}

	AsteroidMedium(String fileName, double positionX, double positionY, double velocity, double angle) {
		super(fileName, DEFAULT_SIZE, positionX, positionY, velocity, angle);
	}

	@Override
	public ArrayList<Asteroid> destroyed() {
		ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>(2);

		asteroids.add(new AsteroidSmall(fileName, this.getX(), this.getY(), velocity, angle + Math.PI / 4));
		asteroids.add(new AsteroidSmall(fileName, this.getX(), this.getY(), velocity, angle - Math.PI / 4));
		return asteroids;
	}

	@Override
	public int getScore() {
		return 50;
	}

}
