package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LogicRunner {
  private Board myMaze;
  private Player player;
  private SeekerEnemy[] enemies;
  private SeekerEnemy enemy1;
  private SeekerEnemy enemy2;
  private SeekerEnemy enemy3;
  private GameState gameState;
  private SurroundingsChecker surch;
  private BoardValidityChecker validityCheck;


  /**
  * Constructor for LogicRunner sets up the game board and ensures the given board is solvable
  * Currently has no paramaters but potentially could.
  */
  public LogicRunner() {
    myMaze = new Board(20,25);
    //myMaze.genNodeBoard(); //Alternate way of creating maze
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

		
		myMaze.createNodeBoard();
		player = new Player(myMaze, Integer.parseInt(sf.nextLine()), Integer.parseInt(sf.nextLine()));
		enemy1 = new SeekerEnemy(myMaze, 1.4, Integer.parseInt(sf.nextLine()), Integer.parseInt(sf.nextLine()));
		enemy2 = new SeekerEnemy(myMaze, 1.4, Integer.parseInt(sf.nextLine()),Integer.parseInt(sf.nextLine()));
		enemy3 = new SeekerEnemy(myMaze, 1.4, Integer.parseInt(sf.nextLine()),Integer.parseInt(sf.nextLine()));
		sf.close();
	} catch (FileNotFoundException e) {
		myMaze.randomBoard();
		myMaze.createNodeBoard();
		player = new Player(myMaze, 1, 1);
		enemy1 = new SeekerEnemy(myMaze, 1.0, 1, 20);
		enemy2 = new SeekerEnemy(myMaze, 1.0, myMaze.getRows() - 2, 20);
		enemy3 = new SeekerEnemy(myMaze, 1.0, (int) (myMaze.getRows() / 2), myMaze.getColumns() - 2);
	}
    enemies = new SeekerEnemy[]{enemy1, enemy2, enemy3};
    validityCheck = new BoardValidityChecker(myMaze, player.getRow(), player.getCol());
    gameState = new GameState();
    surch = new SurroundingsChecker();
    int timesReloaded = 0;
    while (!validityCheck.checkMaze(4)) {
      timesReloaded ++;
      myMaze = new Board(30,50);
      //myMaze.genNodeBoard(); //Alternate way of creating maze
      myMaze.emptyBoard();
      myMaze.randomBoard();
      myMaze.createNodeBoard();
      player = new Player(myMaze, 1, 1);
      enemy1 = new SeekerEnemy(myMaze, 1.0, 1, 20);
      enemy2 = new SeekerEnemy(myMaze, 1.0, myMaze.getRows() - 2, 20);
      enemy3 = new SeekerEnemy(myMaze, 1.0, (int)(myMaze.getRows()/2), myMaze.getColumns() - 2);
      enemies = new SeekerEnemy[]{enemy1, enemy2, enemy3};
      validityCheck = new BoardValidityChecker(myMaze, player.getRow(), player.getCol());
    }
    System.out.println("Times Reloaded: " + timesReloaded);
  }
  
  public void saveBoard() {
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
	    }
		 catch (IOException e1) {
			
		}
  }

  public Board getBoard() {
    return myMaze;
  }

  public SeekerEnemy[] getEnemies() {
    return enemies;
  }

  public Player getPlayer() {
    return player;
  }
}
