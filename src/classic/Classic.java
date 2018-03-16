package classic;

import java.util.ArrayList;
import java.util.Random;
import finalProject.Asteroid;
import finalProject.GameMain;
import finalProject.Laser;
import finalProject.SavedScores;
import finalProject.Score;
import finalProject.Ship;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Needs to add in Score, lives, lasers, UFOs (maybe)
public class Classic {
	static Random rnd = new Random();
	public static ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	public static ArrayList<Ship> ships = new ArrayList<Ship>();
	public static ArrayList<String> input = new ArrayList<String>();
	public static ArrayList<Laser> lasers = new ArrayList<Laser>();
	public static int laserCooldown = 0;
	static Pane pane = new Pane();
	static Scene scene = new Scene(pane, GameMain.sceneSize, GameMain.sceneSize);

	/*
	 * return the scene for classic asteroid
	 */
	public static Scene getScene() {
		ships.add(new Ship());
		pane.getChildren().add(ships.get(0));

		// displays score and lives
		VBox vBox = new VBox();
		HBox scoreBox = new HBox();
		Text scoreTitle = new Text(10, 20, "Score: ");
		Label score = new Label();
		scoreBox.getChildren().addAll(scoreTitle, score);

		HBox livesBox = new HBox();
		Text livesTitle = new Text(10, 20, "Lives: ");
		Label lives = new Label();
		livesBox.getChildren().addAll(livesTitle, lives);
		vBox.getChildren().addAll(scoreBox, livesBox);
		pane.getChildren().add(vBox);

		/*
		 * for(int i = 0; i < asteroids.size(); i++) { Asteroid a = asteroids.get(i);
		 * pane.getChildren().add(a);
		 * 
		 * }
		 */

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();

				if (!input.contains(code))
					input.add(code);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				input.remove(code);
			}
		});

		new AnimationTimer() {

			@Override
			public void handle(long time) {
				// updates score and lives
				score.textProperty().bind(new SimpleIntegerProperty(Score.getScore()).asString());
				lives.textProperty().bind(new SimpleIntegerProperty(Ship.livesLeft).asString());

				if (!ships.isEmpty()) {
					if (ships.get(0).isInvincible())
						ships.get(0).blink();
				}
				if ((input.contains("UP")) || (input.contains("W"))) {
					if (!ships.isEmpty())
						ships.get(0).thrust();
				} else if (!ships.isEmpty())
					ships.get(0).dethrust();
				if (((input.contains("RIGHT")) || (input.contains("D")))
						&& ((!input.contains("LEFT")) || (!input.contains("A")))) {
					if (!ships.isEmpty())
						ships.get(0).rotateRight();
				}
				if (((input.contains("LEFT")) || (input.contains("A")))
						&& ((!input.contains("RIGHT")) || (!input.contains("D")))) {
					if (!ships.isEmpty())
						ships.get(0).rotateLeft();
				}
				// LASER!!!!!!!!!!
				if (laserCooldown > 0)
					laserCooldown = laserCooldown - 1;
				if ((input.contains("SPACE")) && (laserCooldown == 0) && (!ships.isEmpty())) {
					laserCooldown = 20;
					Laser newLaser = ships.get(0).shootLaser();
					lasers.add(newLaser);
					pane.getChildren().add(newLaser);

				}

				
				// Asteroid Spawning
				if (rnd.nextInt(120) < 1) {
					int ra = rnd.nextInt(100);
					if (ra > 70) {
						asteroids.add(new AsteroidLarge());
					} else if (ra > 40) {
						asteroids.add(new AsteroidMedium());
					} else {
						asteroids.add(new AsteroidSmall());
					}
					asteroids.get(asteroids.size()-1).toBack();
					pane.getChildren().add(asteroids.get(asteroids.size() - 1));
				}
				
				// Asteroid Update
				ArrayList<Asteroid> destroyedAsteroids = new ArrayList<Asteroid>();
				for (int i = 0; i < asteroids.size(); i++) {
					Asteroid a = asteroids.get(i);
					// If an Asteroid and the Ship intersects
					if ((!ships.isEmpty()) && (ships.get(0).intersects(a.bounds())) && (!ships.get(0).isInvincible())) {
						pane.getChildren().remove(ships.get(0));
						if (!ships.get(0).isInvincible()) {
							destroyedAsteroids.add(a);

						}
						// else {
						// a.update();
						// }
						ships.addAll(ships.get(0).destroyed());
						ships.remove(0);
						if (ships.isEmpty()) {
							gameOver(Score.getScore());
						} else
							pane.getChildren().add(ships.get(0));
					}
					
					// Asteroid Destroyed by Laser
					if (!lasers.isEmpty()) {
						for (Laser l : lasers) {
							if (l.intersects(a.bounds())) {
								Score.addScore(a.getScore());
								destroyedAsteroids.add(a);
								ArrayList<Asteroid> newAsteroids = a.destroyed();
								if (!newAsteroids.isEmpty()) {
									asteroids.addAll(newAsteroids);
									for (int n = 0; n < newAsteroids.size(); n++) {
										newAsteroids.get(n).toBack();
										pane.getChildren().add(newAsteroids.get(n));
									}
								}
								lasers.remove(l);
								pane.getChildren().remove(l);
								break;
							}
						}
					}
					a.update();
				}
				asteroids.removeAll(destroyedAsteroids);
				pane.getChildren().removeAll(destroyedAsteroids);
				ArrayList<Laser> lasersOffScreen = new ArrayList<Laser>();
				for (Laser l : lasers) {
					if (l.isOffScreen()) {
						lasersOffScreen.add(l);
					} else
						l.update();
				}
				lasers.removeAll(lasersOffScreen);
				pane.getChildren().removeAll(lasersOffScreen);
				if (!ships.isEmpty()) {
					ships.get(0).update();
				}
				else {
					return;
				}
			}
		}.start();
		return scene;
	}
	private static void gameOver(int score) {

		
		SavedScores savedScores = new SavedScores();
		if(savedScores.getHighScore() < Score.getScore()) {
			Text highScore = new Text("NEW HIGH SCORE");
			highScore.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 48));
			highScore.setLayoutX(GameMain.sceneSize/2 - highScore.getLayoutBounds().getWidth()/2);
			highScore.setLayoutY(100);
			
			Text newScore = new Text("You Scored " + Score.getScore());
			newScore.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 48));
			newScore.setLayoutX(GameMain.sceneSize/2 - newScore.getLayoutBounds().getWidth()/2);
			newScore.setLayoutY(175);
			
			TextField textField = new TextField ();
			textField.toFront();
			textField.setText("Enter Name Without Spaces!");
			textField.setLayoutX(GameMain.sceneSize/2 - textField.getLayoutBounds().getWidth()/2);
			textField.setLayoutY(200);
			
			Button btSubmit = new Button("Submit");
			btSubmit.setMinWidth(150);
			btSubmit.setLayoutX(300);
			btSubmit.setLayoutY(350);
			
			btSubmit.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent t){
					for(int i = 0; i < textField.getText().length(); i++) {
						if((textField.getText().charAt(i) == ' '))
							return;
					}
					savedScores.addHighScore(textField.getText() + " " + score);
					System.exit(0);
				}
			});
			
			pane.getChildren().add(newScore);
			pane.getChildren().addAll(textField);
			pane.getChildren().add(highScore);
			pane.getChildren().add(btSubmit);
		}
		else {
			Text title = new Text("GAME OVER");
			
			title.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 48));
			title.setLayoutX(GameMain.sceneSize/2 - title.getLayoutBounds().getWidth()/2);
			title.setLayoutY(100);
			
			Text newScore = new Text("You Scored " + Score.getScore());
			newScore.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 48));
			newScore.setLayoutX(GameMain.sceneSize/2 - newScore.getLayoutBounds().getWidth()/2);
			newScore.setLayoutY(175);
			
			pane.getChildren().add(newScore);
			pane.getChildren().add(title);
		}
		Button btExit = new Button("Exit");
		
		btExit.setMinWidth(150);
		btExit.setLayoutX(300);
		btExit.setLayoutY(400);
		
		btExit.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent t){
				System.exit(0);
			}
		});
		
		pane.getChildren().add(btExit);
	}
}
