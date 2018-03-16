package finalProject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SavedScores {
	private ArrayList<Integer> savedScores = new ArrayList<Integer>();
	private ArrayList<String> savedNames = new ArrayList<String>(); 
	private String fileName;
	public boolean returnMenu = false;
	public Scene scene;
	
	public SavedScores(){
		 this("src/finalProject/scores.txt");
	}
	
	SavedScores(String fileName){
		this.fileName = fileName;
		File file = new File(fileName);
		try {
			Scanner fin = new Scanner(file);
			
			while(true) {
				if(!fin.hasNext())
					break;
				savedNames.add(fin.next());
				savedScores.add(fin.nextInt());
				if(!fin.hasNext())
					break;
			}
			
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " NotFound");
			System.exit(0);
		}
	}
	
	public int getHighScore() {
		int max = -1;
		for(int s : savedScores) {
			if(s > max)
				max = s;
		}
		return max;
	}
	
	public String getHighScoreName() {
		int index = savedScores.indexOf(getHighScore());
		return (savedNames.get(index));
	}
	public void addHighScore(String nameAndScore) {
		StringBuffer s = new StringBuffer();
		File file = new File(fileName);
		try {
			Scanner fin = new Scanner(file);
			
			while(fin.hasNextLine()) {
				s.append(fin.nextLine() + "\n");
			}
			
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " NotFound");
			System.exit(0);
		}
		
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter(fileName));
			
			output.append(s  + nameAndScore);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Scene getScene() {
		returnMenu = false;
		
		Pane pane = new Pane();
		Text title = new Text("HIGH SCORE");
		Text highScore = new Text(this.toString());
		Button btMainMenu = new Button("Main Menu");
		
		scene = new Scene(pane, GameMain.sceneSize, GameMain.sceneSize);
		
		title.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 48));
		title.setLayoutX(GameMain.sceneSize/2 - title.getLayoutBounds().getWidth()/2);
		title.setLayoutY(100);
		
		highScore.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 48));
		highScore.setLayoutX(GameMain.sceneSize/2 - highScore.getLayoutBounds().getWidth()/2);
		highScore.setLayoutY(300);
		
		btMainMenu.setMinWidth(150);
		btMainMenu.setLayoutX(300);
		btMainMenu.setLayoutY(400);
		
		btMainMenu.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent t){
				GameMain.stage.setScene(new GameMain().mainMenu());
			}
		});
		
		if(returnMenu)
			return(new GameMain().mainMenu());
		pane.getChildren().add(title);
		pane.getChildren().add(highScore);
		pane.getChildren().add(btMainMenu);
		return (scene);
	}
	
	@Override
	public String toString() {
		
		return String.format("%s %s", getHighScoreName(), getHighScore());
	}
}
