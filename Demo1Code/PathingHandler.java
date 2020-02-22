import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
public class PathingHandler {
  private SurroundingsChecker surch = new SurroundingsChecker();
  private ArrayList<MapNode> open = new ArrayList<MapNode>();
  private ArrayList<MapNode> closed = new ArrayList<MapNode>();
  private Board myMaze;
  private MapNode[][] nodes;
  public  boolean found = false;
  private int playerRow;
  private int playerColumn;

  /**
  * The only constructor available. Initializes the nodes which allow the pathing to work.
  * @param state the Maze which will be pathfound on.
  */
  public PathingHandler(Board state) {
    myMaze = state;

    nodes = new MapNode[myMaze.rows][myMaze.columns];
    for (int x = 0; x < myMaze.rows; x++) {
      for (int y = 0; y < myMaze.columns; y++) {
        if (myMaze.board[x][y] == " " || myMaze.board[x][y] == "X") {
          nodes[x][y] = new MapNode(x,y);
          nodes[x][y].impassible = true;
        } else if (myMaze.board[x][y] == "!") {
          nodes[x][y] = new MapNode(x,y);
          nodes[x][y].isPlayer = true;
          playerRow = x;
          playerColumn = y;
        } else {
          nodes[x][y] = new MapNode(x,y);
        }
      }
    }
  }

  public void updateNodes(int newPlayerRow, int newPlayerColumn) {
    nodes[playerRow][playerColumn].isPlayer = false;
    playerRow = newPlayerRow;
    playerColumn = newPlayerColumn;
    nodes[playerRow][playerColumn].isPlayer = true;
  }

  public void makeNodeImpassible(int row, int col) {
    nodes[row][col].impassible = true;
  }
  public void makeNodePassible(int row, int col) {
    nodes[row][col].impassible = false;
  }
  public void makeNodeEnemy(int row, int col) {
    nodes[row][col].isEnemy = true;
  }
  public void makeNodeNotEnemy(int row, int col) {
    nodes[row][col].isEnemy = false;
  }


  /**
  * Prints the closed list, used for testing purposes only
  */
  public void printClosed() {
    System.out.println("closed:");
    for (int i = 0; i < closed.size(); i++) {
      System.out.println("row: " + closed.get(i).row + " column: " + closed.get(i).column);
    }
  }

  /**
  * Prints the open list, used for testing purposes only
  */
  public void printOpen() {
    System.out.println("open:");
    for (int i = 0; i < open.size(); i++) {
      System.out.println("row: " + open.get(i).row + " column: " + open.get(i).column);
    }
  }

  /**
  * adds a Node to the open list to be considered as the next closed node.
  * @param i,j i is the offset in the row and j is for the offset in column, together they cover the four directions of movement possible.
  * @param row,col the position of the potential parent to the new open list node.
  * @param goalRow,goalCol the location of the goal for the pathfinding.
  * @return none
  */
  public void addToOpen(int i, int j, int row, int col, int goalRow, int goalCol) {
    int newRow = row+i;
    int newCol = col+j;
    if (nodes[newRow][newCol].isPlayer) {
      found = true;
      //System.out.println("found: " + row + ", " + col);
    }
    if (!nodes[newRow][newCol].impassible && !closed.contains(nodes[newRow][newCol]) && !open.contains(nodes[newRow][newCol])) {
      //System.out.println("adding to open, row: " + newRow + " column: " + newCol);
      nodes[newRow][newCol].setParent(nodes[row][col]);
      nodes[newRow][newCol].hValue = surch.manhattanScore(newRow,newCol,goalRow,goalCol);
      nodes[newRow][newCol].setCost();
      open.add(nodes[newRow][newCol]);
      //printOpen();
    }
  }

  /**
  * adds a Node to the closed list indicating that it has already been searched around.
  * @param row,col the position of the new closed node
  * @param goalRow,goalCol the location of the goal for the pathfinding
  * @return none
  */
  public void addToClosed(int row, int col, int goalRow, int goalCol) {
    if (!closed.contains(nodes[row][col])) {
      //System.out.println("adding to closed, row: " + row + " column: " + col);
      closed.add(nodes[row][col]);
      //myMaze.board[row][col] = "+";
      open.remove(nodes[row][col]);
      //printOpen();
      addToOpen(1,0, row, col, goalRow, goalCol);
      addToOpen(0,1, row, col, goalRow, goalCol);
      addToOpen(-1,0, row, col, goalRow, goalCol);
      addToOpen(0,-1, row, col, goalRow, goalCol);
    }
  }

  /**
  * uses an A star algorithm approach to determine the "best" path to the goal space (the player)
  * @param row,col the starting row and column for the pathfind (the AI location)
  * @param goalRow,goalCol the location of the goal for the pathfinding
  * @return a useable list of spaces along the best path with index 0 being the first step required
  */
  public ArrayList<MapNode> findPath(int row, int col, int goalRow, int goalCol) {
    ArrayList<MapNode> result = new ArrayList<MapNode>();
    result.clear();
    found = false;
    closed.clear();
    open.clear();
    updateNodes(goalRow, goalCol);
    int myDepth = 1;
    nodes[row][col].hValue = 0;
    nodes[row][col].gValue = 0;
    nodes[row][col].setCost();
    closed.add(nodes[row][col]);
    //printOpen();
    addToOpen(1,0, row, col, goalRow, goalCol);
    addToOpen(0,1, row, col, goalRow, goalCol);
    addToOpen(-1,0, row, col, goalRow, goalCol);
    addToOpen(0,-1, row, col, goalRow, goalCol);
    //printOpen();
    while (!found) {
      //printClosed();
      //printOpen();
      //System.out.println("running");
      myDepth ++;
      int min = 100;
      int placeholder = 0;
      for(int i = 0; i < open.size(); i++) {
        if (open.get(i).getCost() < min) {
          //System.out.println("min: " + min);
          min = open.get(i).getCost();
          placeholder = i;
        }
      }
      //System.out.println("adding to closed: " + open.get(placeholder).row + ", " + open.get(placeholder).column);
      addToClosed(open.get(placeholder).row, open.get(placeholder).column, goalRow, goalCol);
    }
    MapNode currentNode = closed.get(closed.size()-1);
    while (currentNode.cost != 0) {
      result.add(0, currentNode);
      currentNode = currentNode.parent;
    }
    return result;
  }
}
