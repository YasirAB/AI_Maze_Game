//package logic;
import java.util.ArrayList;
import javafx.scene.image.*;

//subclass of parent which handles intelligent enemy movement and random enemy movement
//as well as placing enemy on board

public class SeekerEnemy extends Piece{
  private double speed;
  private double focus;
  private PathCreator myPath;

 /**
  * constructor for enemy calls on parents to place enemy on board at the correct place
  * sets speed and focus based on level which must be either 1 or 2
  * creates a new pathCreator for intelligent enemy movement based on board set boolean of initial enemy to true
  * @param board current board
  * @param level difficulty between 1 and 2
  * @param startRow starting row of enemy
  * @param startCol starting column of enemy
  */

  /**
   * method checks if moving up is valid and if it is will move enemy up in board
   */
  public void moveUp() {
    if (maze.board[row-1][col].getPassability()) {
      row --;
    }
  }
  /**
   * method checks if moving down is valid
   *  and if it is will move piece to that placement on the board
   */
  public void moveDown() {
    if (maze.board[row+1][col].getPassability()) {
      row ++;
    }
  }
   /**
    * method checks if moving right is valid
    * if it is will move piece to that placement on the board
    */

  public void moveRight() {
    if (maze.board[row][col+1].getPassability()) {
      col ++;
    }
  }
  /**
   * method checks if moving left is valid
   * and if it is will move piece that to placement on the board
   */
  public void moveLeft() {
    if (maze.board[row][col-1].getPassability()) {
      col --;
    }
  }


  public SeekerEnemy(Board board, double level, int startRow, int startCol) {
    super(board,startRow,startCol);
    speed = 0.55 - (0.05 * level);
    focus = 0.8 + (0.01 * level);
    myPath =  new PathCreator(maze);
    maze.board[row][col].setEnemy(true);
    maze.board[row][col].setPassability(true);
  }
   /**
    * method for handling all directions takes in the enemy desired next location
    * it first checks that the location is not occupied by another enemy if it is not then setEnemy will be true for its new
    * position and the parent class will be called to run the correct move function
    * @param nextRow row the enemy wants to move to
    * @param nextCol column enemy wants to move to
    */
  public void moveToTile(int nextRow, int nextCol) {
    if (nextRow == row+1 && !maze.board[nextRow][nextCol].checkEnemy()) {
      maze.board[row][col].setEnemy(false);
      moveDown();
      maze.board[row][col].setEnemy(true);
    } else if (nextRow == row-1 && !maze.board[nextRow][nextCol].checkEnemy()) {
      maze.board[row][col].setEnemy(false);
      moveUp();
      maze.board[row][col].setEnemy(true);
    } else if (nextCol == col+1 && !maze.board[nextRow][nextCol].checkEnemy()) {
      maze.board[row][col].setEnemy(false);
      moveRight();
      maze.board[row][col].setEnemy(true);
    } else if (nextCol == col-1 && !maze.board[nextRow][nextCol].checkEnemy()) {
      maze.board[row][col].setEnemy(false);
      moveLeft();
      maze.board[row][col].setEnemy(true);
    }
  }
 /**
  * Intelligent movement method for enemy takes in the players current position
  * uses the the path creator class to store the shortest possible paths to the player move by move
  * if a possible path is found it will make sure there is no enemy in its way then make the first move toward the player
  * if there is no move it will call the randomMove function
  * @param playerRow player's current row
  * @param playerColumn player's current column
  */
  public void seekMove(int playerRow, int playerColumn) {
    ArrayList<MapNode> pathToUse = myPath.findPath(row, col, playerRow, playerColumn);
    if (pathToUse.size() > 0) {
      MapNode current = pathToUse.get(0);
      if (!current.checkEnemy()) {
        moveToTile(current.row, current.column);
      } else {
        randomMove();
      }
    } else {
      moveToTile(playerRow, playerColumn);
    }
  }
/**
 * random movement method for enemy
 * first makes array of possible direction it can move then randomly pick one of the possible directions and
 * call on moveToTile to make the move
 */
  public void randomMove() {
    ArrayList<int[]> directions = surch.checkAround(maze, row, col);
    int moveIndex = (int)(Math.random() * directions.size());
    moveToTile(directions.get(moveIndex)[0], directions.get(moveIndex)[1]);
  }

  /**
   * method based on speed on focus to determine what kid of move the enemy will do
   * uses Math.random to compare to speed and focus
   * the higher the level is the faster the enemy will get to the player
   * if the speed random number is high enough the enemy will get an extra seekMove
   * @param playerRow  player current row
   * @param playerColumn player current column
   */
  public void takeTurn(int playerRow, int playerColumn) {
    double speedRand = Math.random();
    if (speedRand < speed) {
      randomMove();
      //System.out.println("Random move");
    } else if (speedRand > speed + 0.45) {
      seekMove(playerRow, playerColumn);
      //System.out.println("Crit move!");
    }
    if (Math.random() < focus) {
      seekMove(playerRow, playerColumn);
      //System.out.println("Seek move");
    }
  }

}
