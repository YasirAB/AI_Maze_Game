//package logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class PathCreator {
  private SurroundingsChecker surch = new SurroundingsChecker();
  private ArrayList<PathNode> open = new ArrayList<PathNode>();
  private ArrayList<PathNode> closed = new ArrayList<PathNode>();
  private Board myMaze;
  private PathNode[][] nodes;
  private  boolean found = false;
  private int goalRow;
  private int goalColumn;

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
  * getter method for specific node of the board
  * @param i row
  * @param j column
  * @return the specific node with all its information
  */
  public PathNode getIndividualNode(int i ,int j){
	  
	  return this.nodes[i][j];
  }
  /**
   * getter method for full 2D array of nodes
   * @return returns the pathnode 2D array
   */
public PathNode[][] getNodes(){
	  PathNode[][] path = this.nodes;
	  return path;
  }

/**
 * getter for boolean method found
 * @return true or false
 */
public boolean getFound() {
	boolean fd = this.found;
	return fd;
}

/**
 * getter method for the enemies goal column
 * @return the column
 */
public int getGoalColumn() {
	int i = this.goalColumn;
	return i;
}
/**
 * getter method for the enemies goal row
 * @return the row
 */
public int getGoalRow() {
	int i = this.goalRow;
	return i;
}

/**
 * setter method for the enemies goal row
 * @param i the row
 */
public void setGoalRow(int i) {
	 this.goalRow = i;
	
}
/**
 * setter method for the enemies goal column
 * @param i the column
 */
public void setGoalColumn(int i) {
	 this.goalColumn = i;
	
}

/**
 * getter for open array list
 * @return arraylist of unchecked path nodes
 */
public ArrayList<PathNode> getOpen(){
	 ArrayList<PathNode> openPath = this.open;
	 return openPath;
	
}

/**
 * getter for closed array list
 * @return arraylist of checked path nodes
 */
public ArrayList<PathNode> getClosed(){
	 ArrayList<PathNode> closedPath = this.closed;
	 return closedPath;
	
}
  
  
/**
 * setting position of enemy 
 * @param eRow enemy row
 * @param eCol enemy column
 * @param isEnemy true if that is where enemy is
 */
  public void setNodeEnemy(int eRow, int eCol, boolean isEnemy) {
    nodes[eRow][eCol].setEnemy(isEnemy);
  }

  // Maybe could be used to pass player position on to pathing, but doesnt seem very efficient
  /**
   * updating positions all all players
   * @param none
   * @return none
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
        nodes[newRow][newCol].setHValue(surch.manhattanScore(newRow,newCol,goalRow,goalColumn));
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
    nodes[row][col].setHValue(0);
    nodes[row][col].setGValue(0);
    nodes[row][col].setCost();
    closed.add(nodes[row][col]);
    addToOpen(1,0, row, col);
    addToOpen(0,1, row, col);
    addToOpen(-1,0, row, col);
    addToOpen(0,-1, row, col);
    // while loop to keep search going until goal is found
    while (!found) {
      myDepth ++;
      int min = 100;
      int placeholder = 0;
      for(int i = 0; i < open.size(); i++) {
        if (open.get(i).getCost() < min) {
          min = open.get(i).getCost();
          placeholder = i;
        }
      }
      addToClosed(open.get(placeholder).getRow(), open.get(placeholder).getColumn());
    }
    // the creation of the array list to return, places the first move required in index 0
    PathNode currentNode = closed.get(closed.size()-1);
    while (currentNode.getCost() != 0) {
      result.add(0, currentNode);
      // meant to trace back through all the parents to the source
      currentNode = currentNode.returnParent();
    }
    return result;
  }
  /**
   * method for checking if there is a possible path to the player or not
   * @param row enemy row
   * @param col enemy column
   * @param myGoalRow player row final destination
   * @param myGoalColumn player column final destination
   * @return returns true if enemy is able to get to player
   */
  public boolean checkForPath(int row, int col, int myGoalRow, int myGoalColumn) {
    // Initial steps of pathfinding
    found = false;
    ArrayList<MapNode> result = new ArrayList<MapNode>();
    result.clear();
    closed.clear();
    open.clear();
    updateNodes(myGoalRow, myGoalColumn);
    updatePiecePositions();
    int myDepth = 1;
    nodes[row][col].setHValue(0);
    nodes[row][col].setGValue(0);
    nodes[row][col].setCost();
    closed.add(nodes[row][col]);
    addToOpen(1,0, row, col);
    addToOpen(0,1, row, col);
    addToOpen(-1,0, row, col);
    addToOpen(0,-1, row, col);
    // while loop to keep search going until goal is found
    while (!found && open.size() > 0) {
      myDepth ++;
      int min = 100;
      int placeholder = 0;
      for(int i = 0; i < open.size(); i++) {
        if (open.get(i).getCost() < min) {
          min = open.get(i).getCost();
          placeholder = i;
        }
      }
      addToClosed(open.get(placeholder).getRow(), open.get(placeholder).getColumn());
    }
    return found;
  }
}
