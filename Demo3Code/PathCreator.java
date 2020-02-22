//package logic;

/**
* This class constructs a path for the AI to follow to the player using A star
* algorithm.
**/

// import statements
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PathCreator {

  // instance variables
  protected SurroundingsChecker surch = new SurroundingsChecker();
  protected ArrayList<PathNode> open = new ArrayList<PathNode>();
  protected ArrayList<PathNode> closed = new ArrayList<PathNode>();
  protected Board myMaze;
  protected PathNode[][] nodes;
  public  boolean found = false;
  protected int goalRow;
  protected int goalColumn;

  /**
  * The only constructor available. Initializes the nodes which allow the pathing to work.
  * @param state the Maze which will be pathfound on.
  */
  public PathCreator(Board state) {
    myMaze = state;

    nodes = new PathNode[myMaze.ROWS][myMaze.COLUMNS];
    for (int x = 0; x < myMaze.ROWS; x++) {
      for (int y = 0; y < myMaze.COLUMNS; y++) {
        nodes[x][y] = new PathNode(myMaze.board[x][y]);
      }
    }
  }

  /**
  * Sets the node value of an enemy at the provided position
  * @param eRow: the row which the enemy is being erased from or moved to
  * @param eCol: The column which enemy is being erased from or moved to
  * @param isEnemy: true if enemy is on the node else false
  **/
  public void setNodeEnemy(int eRow, int eCol, boolean isEnemy) {
    nodes[eRow][eCol].setEnemy(isEnemy);
  }

  /**Updates the array of pathNodes with current position of the enemy and player
  * on the Board
  */
  public void updatePiecePositions() {
    for (int x = 0; x < myMaze.ROWS; x++) {
      for (int y = 0; y < myMaze.COLUMNS; y++) {
        nodes[x][y].setEnemy(myMaze.board[x][y].checkEnemy());
      }
    }
  }

  /**
  * updates the goal tile of the class, called by findpath to ensure goal is always correct
  * @param newGoalRow,newGoalColumn the new position of the paths end goal
  */
  public void updateNodes(int newGoalRow, int newGoalColumn) {
    nodes[goalRow][goalColumn].makeNotGoal();
    goalRow = newGoalRow;
    goalColumn = newGoalColumn;
    nodes[goalRow][goalColumn].makeGoal();
  }

  /**
  * adds a Node to the open list to be considered as the next closed node.
  * @param i,j i is the offset in the row and j is for the offset in column, together they cover the four directions of movement possible.
  * @param row,col the position of the potential parent to the new open list node.
  * @return none
  */
  public void addToOpen(int i, int j, int row, int col) {
    int newRow = row+i;
    int newCol = col+j;
    if (newRow >= 0 && newRow < myMaze.ROWS && newCol >=0 && newCol < myMaze.COLUMNS){
      if (nodes[newRow][newCol].getGoal()) {
        found = true;
        //System.out.println("found: " + row + ", " + col);
      }
      if (nodes[newRow][newCol].getPassability() && !closed.contains(nodes[newRow][newCol]) && !open.contains(nodes[newRow][newCol])) {
        //System.out.println("adding to open, row: " + newRow + " column: " + newCol);
        nodes[newRow][newCol].setParent(nodes[row][col]);
        nodes[newRow][newCol].hValue = surch.manhattanScore(newRow,newCol,goalRow,goalColumn);
        nodes[newRow][newCol].setCost();
        open.add(nodes[newRow][newCol]);
        //printOpen();
      }
    }

  }

  /**
  * adds a Node to the closed list indicating that it has already been searched around.
  * @param row,col the position of the new closed node
  * @return none
  */
  public void addToClosed(int row, int col) {
    if (!closed.contains(nodes[row][col])) {
      //System.out.println("adding to closed, row: " + row + " column: " + col);
      closed.add(nodes[row][col]);
      //myMaze.board[row][col] = "+";
      open.remove(nodes[row][col]);
      //printOpen();
      addToOpen(1,0, row, col);
      addToOpen(0,1, row, col);
      addToOpen(-1,0, row, col);
      addToOpen(0,-1, row, col);
    }
  }

  /**
  * uses an A star algorithm approach to determine the "best" path to the goal space (the player)
  * @param row,col the starting row and column for the pathfind (the AI location)
  * @param myGoalRow,myGoalColumn the location of the goal for the pathfinding
  * @return a useable list of spaces along the best path with index 0 being the first step required
  */
  public ArrayList<MapNode> findPath(int row, int col, int myGoalRow, int myGoalColumn) {
    // Initial steps of pathfinding
    found = false;
    ArrayList<MapNode> result = new ArrayList<MapNode>();
    result.clear();
    closed.clear();
    open.clear();
    updateNodes(myGoalRow, myGoalColumn);
    updatePiecePositions();
    int myDepth = 1;
    nodes[row][col].hValue = 0;
    nodes[row][col].gValue = 0;
    nodes[row][col].setCost();
    closed.add(nodes[row][col]);
    addToOpen(1,0, row, col);
    addToOpen(0,1, row, col);
    addToOpen(-1,0, row, col);
    addToOpen(0,-1, row, col);
    // while loop to keep search going until goal is found
    while (!found) {
      myDepth ++;
      int min = 100000;
      int placeholder = 0;
      for(int i = 0; i < open.size(); i++) {
        if (open.get(i).getCost() < min) {
          min = open.get(i).getCost();
          placeholder = i;
        }
      }
      addToClosed(open.get(placeholder).row, open.get(placeholder).column);
    }
    // the creation of the array list to return, places the first move required in index 0
    PathNode currentNode = closed.get(closed.size()-1);
    while (currentNode.getCost() != 0) {

      result.add(0, currentNode);
      // meant to trace back through all the parents to the source
      currentNode = currentNode.parent;
    }
    return result;
  }
}
