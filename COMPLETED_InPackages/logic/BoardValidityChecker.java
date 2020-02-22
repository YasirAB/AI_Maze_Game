package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
public class BoardValidityChecker {
  private SurroundingsChecker surch = new SurroundingsChecker();
  private ArrayList<MapNode> open = new ArrayList<MapNode>();
  private ArrayList<MapNode> closed = new ArrayList<MapNode>();
  private Board myMaze;
  private int found;
  private int playerRow;
  private int playerCol;

  public BoardValidityChecker(Board myMaze, int playerRow, int playerCol) {
    this.myMaze = myMaze;
    this.playerRow = playerRow;
    this.playerCol = playerCol;
  }

  public void addToOpen(int i, int j, int row, int col) {
    int newRow = row+i;
    int newCol = col+j;
    if (newRow >= 0 && newRow < myMaze.ROWS && newCol >=0 && newCol < myMaze.COLUMNS){
      if (myMaze.board[newRow][newCol].getPassability() && !closed.contains(myMaze.board[newRow][newCol]) && !open.contains(myMaze.board[newRow][newCol])) {
        if (myMaze.board[newRow][newCol].getFinish() || myMaze.board[newRow][newCol].checkEnemy()) {
          found += 1;
        }
        open.add(myMaze.board[newRow][newCol]);
      }
    }
  }

  public void addToClosed(int row, int col) {
    if (!closed.contains(myMaze.board[row][col])) {
      closed.add(myMaze.board[row][col]);
      open.remove(myMaze.board[row][col]);
      addToOpen(1,0, row, col);
      addToOpen(0,1, row, col);
      addToOpen(-1,0, row, col);
      addToOpen(0,-1, row, col);
    }
  }

  public boolean checkMaze(int expectedPoints) {
    boolean result = false;
    found = 0;
    closed.clear();
    open.clear();
    closed.add(myMaze.board[playerRow][playerCol]);
    addToOpen(1,0, playerRow, playerCol);
    addToOpen(0,1, playerRow, playerCol);
    addToOpen(-1,0, playerRow, playerCol);
    addToOpen(0,-1, playerRow, playerCol);
    while (open.size() > 0 && found < expectedPoints) {
      addToClosed(open.get(0).getRow(), open.get(0).getCol());
    }
    if (found == expectedPoints) {
      result = true;
    }
    return result;
  }
}
