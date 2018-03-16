package finalProject;


import classic.Classic;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMain extends Application{
	public static int sceneSize = 720;
	public static Stage stage;
	private final int btWidth = 150;
	public static Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Scene scene = mainMenu();
		primaryStage.setTitle("Asteroids");
		//primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	//Needs to look nice and figure out what other options we can add to Main Menu
	public Scene mainMenu(){
		
		Pane pane = new Pane();
		Button btClassic = new Button("Classic Asteroid");
		Button highScore = new Button("Highscores");
		Text title = new Text("A S T E R O I D S");
		ImageView Laser = new ImageView(new Image(getClass().getResourceAsStream("defaultLaser.png")));
		ImageView Asteroid = new ImageView(new Image(getClass().getResourceAsStream("defaultAsteroid.png")));
		ImageView Asteroid1 = new ImageView(new Image(getClass().getResourceAsStream("defaultAsteroid.png")));
		ImageView Ship = new ImageView(new Image(getClass().getResourceAsStream("defaultShip.png")));
		
		title.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 32));
		title.setLayoutX(GameMain.sceneSize/2 - title.getLayoutBounds().getWidth()/2);
		title.setLayoutY(100);
		
		btClassic.setMinWidth(btWidth);
		btClassic.setLayoutX(300);
		btClassic.setLayoutY(250);

		highScore.setMinWidth(btWidth);
		highScore.setLayoutX(300);
		highScore.setLayoutY(300);

		Laser.setLayoutX(100);
		Laser.setLayoutY(200);

		Asteroid.setFitWidth(68);
		Asteroid.setFitHeight(68);
		Asteroid.setLayoutX(100);
		Asteroid.setLayoutY(106);
		Asteroid1.setFitWidth(68);
		Asteroid1.setFitHeight(68);
		Asteroid1.setLayoutX(130);
		Asteroid1.setLayoutY(120);

		Ship.setFitWidth(37);
		Ship.setFitHeight(70);
		Ship.setLayoutX(90);
		Ship.setLayoutY(500);

		btClassic.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent t){
				stage.setScene(Classic.getScene());
			}
		});

		highScore.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				SavedScores scores = new SavedScores();
				stage.setScene(scores.getScene());
				
			}

		});

		pane.getChildren().addAll(btClassic);
		pane.getChildren().add(highScore);
		pane.getChildren().add(title);
		pane.getChildren().add(Laser);
		pane.getChildren().add(Asteroid);
		pane.getChildren().add(Asteroid1);
		pane.getChildren().add(Ship);
		scene = new Scene(pane, sceneSize, sceneSize);
		return scene;
	}
	public static void main(String[] args) {
		launch(args);
	}

}
