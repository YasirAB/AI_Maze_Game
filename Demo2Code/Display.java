package View;
// Import statements
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Game.GameState;
import Game.PathCreator;
import Game.Player;
import Game.SeekerEnemy;
import Game.SurroundingsChecker;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Display extends Application {
	// javafx method required to run the graphics

	Board myMaze;
	Player player;
	SeekerEnemy enemy1;
	SeekerEnemy enemy2;
	SeekerEnemy enemy3;
	StackPane root;
	GridPane base;
	GridPane pieceView;
	Scene scene;
	GameState gameState;
	PieceTracker myTracker;
	SurroundingsChecker surch;
	PathCreator pathCheck;
	Button saveButton = new Button("Save");
	Button quitButton = new Button("Quit");
	final Stage savePopup = new Stage();

	public void makeNewLevel(Stage primaryStage) {
		// all instance variables and objects required to run program
		myMaze = new Board(25, 40);
		myMaze.emptyBoard();
		try {
			File saveFile = new File("save.txt");
			Scanner sf = new Scanner(saveFile);
			for (int x = 0; x < myMaze.getRows() && sf.hasNextLine(); x++) {
				for (int y = 0; y < myMaze.getColumns(); y++) { 
					myMaze.stringBoard[x][y] = sf.nextLine();
					if(myMaze.stringBoard[x][y].equals("!")) {
						myMaze.stringBoard[x][y] = "#";	
					}
			}}

			myMaze.stringBoard[Integer.parseInt(sf.nextLine())][Integer.parseInt(sf.nextLine())] = "!";
			
			myMaze.createNodeBoard();
			player = new Player(myMaze, myMaze.playerStartRow, myMaze.playerStartCol);
			enemy1 = new SeekerEnemy(myMaze, 1.4, Integer.parseInt(sf.nextLine()), Integer.parseInt(sf.nextLine()));
			enemy2 = new SeekerEnemy(myMaze, 1.4, Integer.parseInt(sf.nextLine()),Integer.parseInt(sf.nextLine()));
			enemy3 = new SeekerEnemy(myMaze, 1.4, Integer.parseInt(sf.nextLine()),Integer.parseInt(sf.nextLine()));
			sf.close();
		} catch (FileNotFoundException e) {
			myMaze.randomBoard();
			myMaze.createNodeBoard();
			player = new Player(myMaze, myMaze.playerStartRow, myMaze.playerStartCol);
			enemy1 = new SeekerEnemy(myMaze, 1.4, 1, 20);
			enemy2 = new SeekerEnemy(myMaze, 1.4, myMaze.getRows() - 2, 20);
			enemy3 = new SeekerEnemy(myMaze, 1.4, (int) (myMaze.getRows() / 2), myMaze.getColumns() - 2);
		}
		pathCheck = new PathCreator(myMaze);
		// myMaze.createNodeBoard();
		
		gameState = new GameState();
		myTracker = new PieceTracker(myMaze);
		surch = new SurroundingsChecker();

		// creation of root and gridpanes for displaying graphics
		root = new StackPane();
		base = new DrawBase(myMaze).drawBase();
		pieceView = new GridPane();

		// creating the initial view of all pieces on maze
		myTracker.updateTracker(myMaze);
		for (int x = 0; x < myMaze.getRows(); x++) {
			for (int y = 0; y < myMaze.getColumns(); y++) {
				pieceView.add(myTracker.tracker[x][y], y, x);
			}
		}
		// adding to the root so gridpanes are displayed
		root.getChildren().add(base);
		root.getChildren().add(pieceView);
		scene = new Scene(root);

		// setting stage variables
		primaryStage.setTitle("Maze Game");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void start(Stage primaryStage) {
		makeNewLevel(primaryStage);

		// setting the on key pressed for the scene to be an event handler object
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			boolean running = true;

			@Override
			public void handle(KeyEvent event) {
				// halts the running if the player is stuck
				if (surch.checkAroundPlayer(myMaze, player.getRow(), player.getCol()).size() == 0) {
					running = false;
					Label loseText = new Label("You Lose!");
					loseText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 100));
					base.add(loseText, 0, 0);
				}
				// gets key input from the player if the player has not won or lost yet
				if (running) {
					if (event.getCode() == KeyCode.UP
							&& surch.checkEmpty(myMaze, player.getRow() - 1, player.getCol())) {
						player.moveUp();
						handleEnemyMoves();
						refreshView();
					} else if (event.getCode() == KeyCode.DOWN
							&& surch.checkEmpty(myMaze, player.getRow() + 1, player.getCol())) {
						player.moveDown();
						handleEnemyMoves();
						refreshView();
					} else if (event.getCode() == KeyCode.RIGHT
							&& surch.checkEmpty(myMaze, player.getRow(), player.getCol() + 1)) {
						player.moveRight();
						handleEnemyMoves();
						refreshView();
					} else if (event.getCode() == KeyCode.LEFT
							&& surch.checkEmpty(myMaze, player.getRow(), player.getCol() - 1)) {
						player.moveLeft();
						handleEnemyMoves();
						refreshView();
					} else if (event.getCode() == KeyCode.ESCAPE) {
						savePopup.setResizable(false);
						HBox dialogBox = new HBox(20);
						dialogBox.getChildren().add(saveButton);
						dialogBox.getChildren().add(quitButton);
						Scene dialogScene = new Scene(dialogBox);
						savePopup.setScene(dialogScene);
						savePopup.show();
					}
				}
				
				
				saveButton.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
						FileWriter fw;
						try {
							fw = new FileWriter("save.txt");
							PrintWriter output = new PrintWriter(fw);
							for (int x = 0; x < myMaze.getRows(); x++) {
								for (int y = 0; y < myMaze.getColumns(); y++) {
									output.println(myMaze.stringBoard[x][y]);
								}
							}
							output.println(player.getRow());
							output.println(player.getCol());
							
							output.println(enemy1.getRow());
							output.println(enemy1.getCol());

							output.println(enemy2.getRow());
							output.println(enemy2.getCol());
							
							output.println(enemy3.getRow());
							output.println(enemy3.getCol());
							output.close();
							fw.close();
					    	savePopup.close();
					    }
						 catch (IOException e1) {
							
						}
				    }	
				});
				quitButton.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				    	savePopup.close();
				    	primaryStage.close();
				    }	
				});

				
				event.consume();
			}
			
			/**
			 * handles all enemy moves and checks for game win or lose
			 * 
			 * @param none
			 */
			public void handleEnemyMoves() {
				if (gameState.checkWin(myMaze)) {
					running = true;
					root.getChildren().clear();
					pieceView.getChildren().clear();
					Image img = new Image("Images/black.png", primaryStage.getWidth(), primaryStage.getHeight(), false,
							false);
					// image from https://gifer.com/en/1BEg
					Image win = new Image("Images/win.gif", primaryStage.getWidth(), primaryStage.getHeight() - 100,
							true, false);
					root.getChildren().add(new ImageView(img));

					root.getChildren().add(new ImageView(win));
					File saveTxt = new File("save.txt");
					saveTxt.delete();

				} else {
					enemy1.takeTurn(player.getRow(), player.getCol());
					enemy2.takeTurn(player.getRow(), player.getCol());
					enemy3.takeTurn(player.getRow(), player.getCol());
					if (gameState.checkLose(myMaze)) {
						running = false;
						// base.getChildren().clear();
						root.getChildren().clear();
						pieceView.getChildren().clear();
						Image img = new Image("Images/black.png", primaryStage.getWidth(), primaryStage.getHeight(),
								false, false);
						Image skull = new Image("Images/gameOver.gif", primaryStage.getWidth(),
								primaryStage.getHeight() - 100, true, false);
						root.getChildren().add(new ImageView(img));

						root.getChildren().add(new ImageView(skull));
						File saveTxt = new File("save.txt");
						saveTxt.delete();
					}
				}
			}

			/**
			 * refreshes the pieceview gridpane so player and enemy positions are updated
			 * 
			 * @param none
			 */
			public void refreshView() {
				if (running) {
					myTracker.updateTracker(myMaze);
					pieceView.getChildren().clear();
					for (int x = 0; x < myMaze.getRows(); x++) {
						for (int y = 0; y < myMaze.getColumns(); y++) {
							pieceView.add(myTracker.tracker[x][y], y, x);
						}
					}
				}
			}
		});


	}

	public static void main(String[] args) {
		launch(args);
	}
}
