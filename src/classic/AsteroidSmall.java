package classic;

import java.util.ArrayList;

import finalProject.Asteroid;

public class AsteroidSmall extends Asteroid {
	public final static int DEFAULT_SIZE = 50;

	AsteroidSmall() {
		super("finalProject/defaultAsteroid.png", DEFAULT_SIZE);
		size = DEFAULT_SIZE;
	}

	AsteroidSmall(String fileName, double size) {
		super(fileName, size);
	}

	AsteroidSmall(String fileName, double size, double positionX, double positionY, double velocity, double angle) {
		super(fileName, size, positionX, positionY, velocity, angle);
	}

	AsteroidSmall(String fileName, double positionX, double positionY, double velocity, double angle) {
		super(fileName, DEFAULT_SIZE, positionX, positionY, velocity, angle);
	}

	// AsteroidSmall does not return anything when destroyed
	@Override
	public ArrayList<Asteroid> destroyed() {
		return (new ArrayList<Asteroid>(0));
	}

	@Override
	public int getScore() {
		return 75;
	}

}
