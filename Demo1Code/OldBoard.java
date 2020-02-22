import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

/**
 *
 * @author DanielSikstrom
 */
//make a main method to run the program
public class OldBoard {

    public static int rows = 22;
    public static int columns = 22;

    public static String[][] board = new String[22][22];
    public static int finishX, finishY, startX, startY;

    public String[] checkGen(int row, int column) {
      String[] myResults = new String[4];
      if (board[row][column-1] == "#") {
        myResults[0] = "good1";
      } else {
        myResults[0] = "bad1";
      }
      if (board[row-1][column-1] == "#") {
        myResults[1] = "good2";
      } else {
        myResults[1] = "bad2";
      }
      if (board[row-1][column] == "#") {
        myResults[2] = "good3";
      } else {
        myResults[2] = "bad3";
      }
      if (board[row-1][column+1] == "#") {
        myResults[3] = "good4";
      } else {
        myResults[3] = "bad4";
      }
      //printStringList(myResults);
      return myResults;
    }

    public String setTile(int row, int column) {
      String[] mySurroundings = checkGen(row,column);
      String tile;
      if (mySurroundings[0] == "good1" && mySurroundings[1] == "good2" && mySurroundings[2] == "good3" && mySurroundings[3] == "good4") {
        tile = " ";
      } else if (mySurroundings[0] == "bad1" && mySurroundings[1] == "bad2" && mySurroundings[2] == "bad3" && mySurroundings[3] == "bad4") {
        tile = "#";
      } else if (mySurroundings[0] == "bad1" && mySurroundings[1] == "bad2" && mySurroundings[2] == "bad3" && mySurroundings[3] == "good4") {
        tile = "#";
      } else if (mySurroundings[0] == "bad1" && mySurroundings[1] == "good2" && mySurroundings[2] == "bad3" && mySurroundings[3] == "bad4") {
        tile = " ";
      } else if (mySurroundings[0] == "bad1" && mySurroundings[1] == "good2" && mySurroundings[2] == "bad3" && mySurroundings[3] == "good4") {
        tile = " ";
      } else if (mySurroundings[0] == "good1" && mySurroundings[1] == "bad2" && mySurroundings[2] == "good3" && mySurroundings[3] == "good4") {
        tile = "#";
      } else if (mySurroundings[0] == "good1" && mySurroundings[1] == "good2" && mySurroundings[2] == "good3" && mySurroundings[3] == "bad4") {
        tile = " ";
      } else if (mySurroundings[0] == "good1" && mySurroundings[1] == "bad2" && mySurroundings[2] == "good3" && mySurroundings[3] == "bad4") {
        tile = "#";
      } else {
        Random randy = new Random();
        int chance = randy.nextInt(4);
        if (chance == 0) {
          tile = " ";
        } else {
          tile = "#";
        }
      }
      return tile;
    }

    public void generateBoard() {
      finishX = 20;
      finishY = 10;
      startX = 1;
      startY = (int)(Math.random() * 21);
      for (int i = 0; i < rows; i++) {
        board[0][i] = " ";
        board[rows-1][i] = " ";
        board[i][0] = " ";
        board[i][columns-1] = " ";
      }
      for (int i = 1; i < rows - 1; i++) {
        for (int j = 1; j < columns - 1; j++) {
          board[i][j] = "#";
        }
      }

      for (int i = 1; i < rows - 1; i++) {
        for (int j = 1; j < columns - 1; j++) {
          //printStringList(tileState);
          board[i][j] = setTile(i,j);
        }
      }
      board[finishY][finishX] = "X";
    }


    //to see if a space is open use board[column][row].equals("#") or board[column][row].equals(" ")
    //only use once to make the default board
    public static void createBoard() {
        finishX = 9;
        finishY = 11;
        startX = 1;
        startY = 11;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = "#";
                if (j == 1 && (i <= 9 || i >= 13)) {
                    board[i][j] = " ";
                }
                if (j == 4 && ((i >= 5 && i <= 9) || (i >= 13 && i<= 17))) {
                    board[i][j] = " ";
                }
                if (j == 7 && ((i >= 1 && i <= 2) || (i >= 5 && i<= 16) || (i >= 19 && i<= 20))) {
                    board[i][j] = " ";
                }
                if (j == 7 && ((i >= 1 && i <= 2) || (i >= 5 && i<= 16) || (i >= 19 && i<= 20))) {
                    board[i][j] = " ";
                }
                if ((i == 3 || i== 18) && (j >= 10 && j <= 17)) {
                    board[i][j] = " ";
                }
                if ((i == 6 || i == 15) && (j >= 8 && j <= 17)) {
                    board[i][j] = " ";
                }
                if((j >= 11 && j <= 17) && (i >= 10 && i<= 11)){
                    board[i][j] = " ";
                }
                if (i == 0 || j == 0 || i == rows-1 || j == columns-1) {
                    board[i][j] = " ";
                }
                board[finishY][finishX] = "X";
                board[startY][startX] = "O";
            }
        }
    }

    //will print out the new board (must use every time to see changes to the board array
    public static void printBoard() {
        System.out.println(Arrays.deepToString(board).replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(",", ""));
    }
}
