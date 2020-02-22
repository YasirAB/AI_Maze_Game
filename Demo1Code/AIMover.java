import java.util.Random;
import java.util.ArrayList;
import java.lang.*;

// Class to handle moving the ai around.
public class AIMover {

  // Instance Variables #######################################################
  private ArrayList<String> directions = new ArrayList<String>();
  private Random rand = new Random();
  public Board oState;
  public int myRow;
  public int myColumn;
  private String lastPos;
  private SurroundingsChecker surch = new SurroundingsChecker("X");
  private PathingHandler path;
  private ArrayList<MapNode> pathToUse = new ArrayList<MapNode>();

  // Constructors #############################################################
  /**
  * The only constructor available
  * @param myMaze,row,column the maze the ai will live in and its starting position
  */
  public AIMover(Board myMaze, int row, int column) {
    oState = myMaze;
    myRow = row;
    myColumn = column;
    oState.board[myRow][myColumn] = "@";
    path = new PathingHandler(oState);
    path.makeNodeEnemy(myRow,myColumn);
  }

  public AIMover(Board myMaze, int row, int column, PathingHandler sharedPath) {
    oState = myMaze;
    myRow = row;
    myColumn = column;
    oState.board[myRow][myColumn] = "@";
    path = sharedPath;
    path.makeNodeEnemy(myRow,myColumn);
  }

  public void updatePath(int playerRow, int playerColumn) {
    pathToUse.clear();
    ////System.out.println("updating... playerpos = " + playerRow + ", " + playerColumn);
    ////System.out.println("mypos = " + myRow + ", " + myColumn);
    pathToUse = path.findPath(myRow, myColumn, playerRow, playerColumn);
  }

  // Methods ##################################################################
  /**
  * for changing a given postion to a direction for the ai to move.
  * @param row,col position being moved to, it's important that this position is adjacent to the ai's current position.
  * @return a string which dictates the direction for the ai to move.
  */
  public String convertToDirection(int row, int col) {
    String result = "up";
    if (row == myRow+1) {
      result = "down";
    } else if (row == myRow-1) {
      result = "up";
    } else if (col == myColumn+1) {
      result = "right";
    } else if (col == myColumn-1) {
      result = "left";
    }
    return result;
  }

  /**
  * for moving the ai, might not be the best way to do it.
  * @param moveDirection the direction for the AI to move in.
  * @return none
  */
  public void moveAI(String moveDirection) {
    path.makeNodeNotEnemy(myRow,myColumn);
    oState.board[myRow][myColumn] = "#";
    if (moveDirection == "up") {
      myRow -= 1;
    } else if (moveDirection == "down") {
          myRow += 1;
    } else if (moveDirection == "right") {
          myColumn += 1;
    } else if (moveDirection == "left") {
          myColumn -= 1;
    }
    oState.board[myRow][myColumn] = "@";
    path.makeNodeEnemy(myRow,myColumn);
  }

  // moves the ai in a random direction.
  public void randomMove() {
    int moveIndex;
    directions = surch.checkAround(oState, myRow, myColumn);
    //////System.out.println(directions);
    moveIndex = rand.nextInt(directions.size());
    if (!pathToUse.get(0).isEnemy) {
      moveAI(directions.get(moveIndex));
    }
    moveIndex = 0;
  }

  /**
  * moves the ai in the general direction of the player, prone to getting stuck in loop when player is stationary.
  * @param playerRow,playerColumn position of the player.
  * @return none.
  */
  public void hearingMove(int playerRow, int playerColumn) { //BROKEN
    String moveDirection = "";
    boolean stuck = false;
    directions = surch.checkAround(oState, myRow, myColumn);
    if (directions.contains(lastPos)){
      directions.remove(lastPos);
    }
    int vertDif = myRow - playerRow;
    int horizDif = myColumn - playerColumn;
    if (Math.abs(vertDif) >= Math.abs(horizDif) && (directions.contains("down") || directions.contains("up"))) {
      if (vertDif < 0 && directions.contains("down")) {
        moveDirection = "down";
        lastPos = "up";
      } else {
        moveDirection = "up";
        lastPos = "down";
      }
    } else if ((directions.contains("left") && horizDif > 0) || (directions.contains("right") && horizDif < 0)){
      if (horizDif < 0 && directions.contains("right")) {
        moveDirection = "right";
        lastPos = "left";
      } else {
        moveDirection = "left";
        lastPos = "right";
      }
    } else {
      if (vertDif < 0 && directions.contains("down")) {
        moveDirection = "down";
        lastPos = "up";
      } else if (directions.contains("up")) {
        moveDirection = "up";
        lastPos = "down";
      } else {
        stuck = true;
      }
    }
    if (!stuck) {
      moveAI(moveDirection);
    } else {
      randomMove();
      lastPos = "";
    }

  }
  /**
  * uses a given path to move the ai towards the player as fast as possible.
  * @param pathtouse the path for the ai to travel down.
  * @param playerRow,playerColumn position of the player, only used for final move to "capture" player.
  * @return none.
  */
  public void usePath(int playerRow, int playerColumn) {
    updatePath(playerRow, playerColumn);
    if (pathToUse.size() > 0) {
      ////System.out.println("path size: " + pathToUse.size());
      MapNode current = pathToUse.get(0);
      String moveDirection = convertToDirection(current.row, current.column);
      if (!pathToUse.get(0).isEnemy) {
        moveAI(moveDirection);
      }
    } else {
      String moveDirection = convertToDirection(playerRow, playerColumn);
      moveAI(moveDirection);
    }

  }
  //end #######################################################################
}
