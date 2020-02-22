//package logic;
import java.util.ArrayList;
// Class for checking surroundings of tiles
public class SurroundingsChecker {
  // instance vars #################################
  private String playerPiece = "!";
  
  // constructors ##################################
  //base constructor for no args
  public SurroundingsChecker() {
  }
  //constructor for changing the playerpiece marker
  public SurroundingsChecker(String playerMark) {
    playerPiece = playerMark;
  }
  
  
  // methods #######################################
  /**
   * getter method for player piece
   * @return the character for the player piece
   */
  public String getPlayerPiece() {
		 String player = this.playerPiece;
		 return player;
	  }
  /**
  * determines whether a tile is empty or not
  * @param state the map state being assesed
  * @param row,col the position of the tile being checked
  * @return true if empty false if not empty
  */
  public boolean checkEmpty(Board state, int row, int column) {
    boolean result = false;
    if (state.board[row][column].getPassability()) {
      result = true;
    }
    return result;
  }
  

  public boolean checkEmptyForPlayer(Board state, int row, int column) {
    boolean result = false;
    if (state.board[row][column].getPassability() && !state.board[row][column].checkEnemy()) {
      result = true;
    }
    return result;
  }
  /**
  * uses checkEmpty() to asses the four borders of a given tile and return the empty directions
  * @param state the map state being assesed
  * @param myRow,myCol the position of the tile being checked around
  * @return a list of all the directions in which the tile is bordered by an empty space
  */
  public ArrayList<int[]> checkAround(Board state, int myRow, int myColumn) {
    ArrayList<int[]> directions = new ArrayList<int[]>();
    if (myRow+1 < state.ROWS) {
      if (checkEmpty(state,myRow+1,myColumn)) {directions.add(new int[]{myRow+1,myColumn});}
    }
    if (myRow-1 > -1) {
      if (checkEmpty(state,myRow-1,myColumn)) {directions.add(new int[]{myRow-1,myColumn});}
    }
    if (myColumn+1 < state.COLUMNS) {
      if (checkEmpty(state,myRow,myColumn+1)) {directions.add(new int[]{myRow,myColumn+1});}
    }
    if (myColumn-1 > -1) {
      if (checkEmpty(state,myRow,myColumn-1)) {directions.add(new int[]{myRow,myColumn-1});}
    }
    return directions;
  }

  public ArrayList<int[]> checkAroundPlayer(Board state, int myRow, int myColumn) {
    ArrayList<int[]> directions = new ArrayList<int[]>();
    if (myRow+1 < state.ROWS) {
      if (checkEmptyForPlayer(state,myRow+1,myColumn)) {directions.add(new int[]{myRow+1,myColumn});}
    }
    if (myRow-1 > -1) {
      if (checkEmptyForPlayer(state,myRow-1,myColumn)) {directions.add(new int[]{myRow-1,myColumn});}
    }
    if (myColumn+1 < state.COLUMNS) {
      if (checkEmptyForPlayer(state,myRow,myColumn+1)) {directions.add(new int[]{myRow,myColumn+1});}
    }
    if (myColumn-1 > -1) {
      if (checkEmptyForPlayer(state,myRow,myColumn-1)) {directions.add(new int[]{myRow,myColumn-1});}
    }
    return directions;
  }

  public boolean isInArray(ArrayList<int[]> list, int row, int col) {
    boolean result = false;
    for (int i = 0; i < list.size(); i++) {
      if(list.get(i)[0] == row && list.get(i)[1] == col) {
        result = true;
      }
    }
    return result;
  }

  /**
  * calculates the "manhattan score" of a given tile location and goal.
  * @param myRow,myCol the position of the tile to asses score for.
  * @param goalRow,goalCol the location of the goal of the tile.
  * @return a number representetive of how far from the goal a given tile is.
  */
  public int manhattanScore(int myRow, int myCol, int goalRow, int goalCol) {
    int dr = Math.abs(goalRow - myRow);
    int dc = Math.abs(goalCol - myCol);
    return (dr + dc);
  }
  /**
  * A method only used for testing
  */
  public void printList(ArrayList<String> myList) {
    System.out.print("(");
    for (int i = 0; i < myList.size(); i++) {
      System.out.print(myList.get(i) + ", ");
    }
    System.out.print(")");
  }
}
