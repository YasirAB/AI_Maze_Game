package logic;

import java.util.Arrays;
import java.lang.Math;

/**
 *
 * @author DanielSikstrom
 */
//make a main method to run the program
public class AltMazeGenerator {

    public int ROWS = 30;
    public int COLUMNS = 30;

    private double prefOpen = 14.0;
    private double smallPrefOpen = 3.0;
    private double noPref = 1.5;

    public MapNode[][] board;
    private int finishX, finishY;

    public AltMazeGenerator(int rows, int cols) {
      ROWS = rows;
      COLUMNS = cols;
      board = new MapNode[ROWS][COLUMNS];
      for (int x = 0; x < ROWS; x++) {
        for (int y = 0; y < COLUMNS; y++) {
          board[x][y] = new MapNode(x,y);
        }
      }
    }

    public int getRows(){
      return ROWS;
    }
    public int getColumns() {
      return COLUMNS;
    }

    public boolean[] checkGen(int row, int column) {
      boolean[] myResults = new boolean[4];
      if (board[row][column-1].getPassability()) {
        myResults[0] = true;
      } else {
        myResults[0] = false;
      }
      if (board[row-1][column-1].getPassability()) {
        myResults[1] = true;
      } else {
        myResults[1] = false;
      }
      if (board[row-1][column].getPassability()) {
        myResults[2] = true;
      } else {
        myResults[2] = false;
      }
      if (board[row-1][column+1].getPassability()) {
        myResults[3] = true;
      } else {
        myResults[3] = false;
      }
      //printStringList(myResults);
      return myResults;
    }

    public boolean setTile(int row, int column) {
      boolean[] mySurroundings = checkGen(row,column);
      boolean tilePassability = false;
      if (mySurroundings[0] == true && mySurroundings[1] == true && mySurroundings[2] == true && mySurroundings[3] == true) {
        tilePassability = false;
      } else if (mySurroundings[0] == false && mySurroundings[1] == false && mySurroundings[2] == false && mySurroundings[3] == false) {
        tilePassability = true;
      } else if (mySurroundings[0] == false && mySurroundings[1] == false && mySurroundings[2] == false && mySurroundings[3] == true) {
        tilePassability = true;
      } else if (mySurroundings[0] == false && mySurroundings[1] == true && mySurroundings[2] == false && mySurroundings[3] == false) {
        tilePassability = false;
      } else if (mySurroundings[0] == false && mySurroundings[1] == true && mySurroundings[2] == false && mySurroundings[3] == true) {
        tilePassability = false;
      } else if (mySurroundings[0] == true && mySurroundings[1] == false && mySurroundings[2] == true && mySurroundings[3] == true) {
        tilePassability = true;
      } else if (mySurroundings[0] == true && mySurroundings[1] == true && mySurroundings[2] == true && mySurroundings[3] == false) {
        tilePassability = false;
      } else if (mySurroundings[0] == true && mySurroundings[1] == false && mySurroundings[2] == true && mySurroundings[3] == false) {
        tilePassability = true;
      }

      else if (mySurroundings[0] == true && mySurroundings[1] == false && mySurroundings[2] == false && mySurroundings[3] == false) {//new starts on this line
        double chance = Math.random() * smallPrefOpen;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == true && mySurroundings[1] == false && mySurroundings[2] == false && mySurroundings[3] == true) {
        double chance = Math.random() * prefOpen;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == true && mySurroundings[1] == true && mySurroundings[2] == false && mySurroundings[3] == false) {
        double chance = Math.random() * prefOpen;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == true && mySurroundings[1] == true && mySurroundings[2] == false && mySurroundings[3] == true) {
        double chance = Math.random() * smallPrefOpen;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == false && mySurroundings[1] == true && mySurroundings[2] == true && mySurroundings[3] == true) {
        double chance = Math.random() * noPref;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == false && mySurroundings[1] == false && mySurroundings[2] == true && mySurroundings[3] == true) {
        double chance = Math.random() * noPref;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == false && mySurroundings[1] == false && mySurroundings[2] == true && mySurroundings[3] == false) {
        double chance = Math.random() * prefOpen;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      } else if (mySurroundings[0] == false && mySurroundings[1] == true && mySurroundings[2] == true && mySurroundings[3] == false) {
        double chance = Math.random() * smallPrefOpen;
        if (chance < 1) {
          tilePassability = false;
        } else {
          tilePassability = true;
        }
      }
      return tilePassability;
    }

    public void generateBoard() {
      for (int x = 0; x < ROWS; x++) {
        for (int y = 0; y < COLUMNS; y++) {
          board[x][y].setPassability(true);
        }
      }
      finishX = COLUMNS-2;
      finishY = 18;
      for (int i = 0; i < ROWS; i++) {
        board[i][0].setPassability(false);
        board[i][COLUMNS-1].setPassability(false);
      }
      for (int i = 0; i < COLUMNS; i++) {
        board[0][i].setPassability(false);
        board[ROWS-1][i].setPassability(false);
      }

      for (int i = 1; i < ROWS - 1; i++) {
        for (int j = 1; j < COLUMNS - 1; j++) {
          //printStringList(tileState);
          board[i][j].setPassability(setTile(i,j));
        }
      }
      board[finishY][finishX].setFinish();
      board[finishY][finishX].setPassability(true);
      board[finishY+1][finishX].setPassability(true);
      board[finishY-1][finishX].setPassability(true);
    }

    //will print out the new board (must use every time to see changes to the board array
    public void printBoard() {
        String[][] printableBoard = new String[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
          for (int j = 0; j < COLUMNS; j++) {
            if (board[i][j].getPassability()) {
              printableBoard[i][j] = "#";
            } else {
              printableBoard[i][j] = " ";
            }
            if (board[i][j].checkPlayer()) {
              printableBoard[i][j] = "!";
            }
            if (board[i][j].checkEnemy()) {
              printableBoard[i][j] = "@";
            }
          }
        }
        //printableBoard[finishX][finishY] = "X";
        //System.out.println(Arrays.deepToString(printableBoard).re  place("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(",", ""));
        for (int i = 0; i < ROWS; i++) {
          for (int j = 0; j < COLUMNS; j++) {
            System.out.print(printableBoard[i][j] + " ");
          }
          System.out.println();
        }
    }
}

// TO DO on this file: remove printBoard and put in another class
