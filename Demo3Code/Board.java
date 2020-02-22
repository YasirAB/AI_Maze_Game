
// delete package
//package teamassignment;

import java.util.Arrays;

/**
 *
 * @author DanielSikstrom
 */
//make a main method to run the program
public class Board {

	public String[][] stringBoard;
	public int finishX, finishY;
	public MapNode[][] board;
	public int ROWS;
	public int COLUMNS;
  private AltMazeGenerator altMaze;
	// to see if a space is open use stringBoard[column][row].equals("#") or
	// stringBoard[column][row].equals(" ")
	// only use once to make the default stringBoard
	// public String[][] boardMemory = new
	// String[this.stringBoard[0].length][this.stringBoard.length];
	public int genStartColumn = 0;
	public int genStartRow = 0;
	public int genCurrentColumn = 100000; // set to something start row will never be
	public int genCurrentRow = 100000; // set to something start row will never be
	public int lastMove = 5;
	public int backtrackMove = 5;
	public int randomPick;
	public int randomMove = 99; // 0 = N, 1 = E, 2 = S, 3 = W
	public int repetitions = 0;
	public int stuckInLoop = 0;
	public boolean didBacktrack = false;
	public boolean firstMove = true;
	public boolean end = false;
	public boolean canMove = false;
	public boolean moved = false;

	public Board(int x, int y) {
		this.stringBoard = new String[x][y];
		this.board = new MapNode[x][y];
		ROWS = x;
		COLUMNS = y;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				board[i][j] = new MapNode(i, j);
			}
		}
    altMaze = new AltMazeGenerator(ROWS, COLUMNS);
	}

	public int getRows() {
		return ROWS;
	}

	public int getColumns() {
		return COLUMNS;
	}

	public void emptyBoard() {
		for (int i = 0; i < this.stringBoard.length; i++) {
			for (int j = 0; j < this.stringBoard[0].length; j++) {
				this.stringBoard[i][j] = "W";
				if (i == 0 || j == 0 || i == this.stringBoard.length - 1 || j == this.stringBoard[0].length - 1) {
					this.stringBoard[i][j] = " ";
				}
			}
		}
	}

	public void firstMove() {
		this.genCurrentColumn = this.genStartColumn;
		this.genCurrentRow = this.genStartRow;
		if (this.randomMove == 0 && !this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals(" ")) {
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow + -1] = " ";
			this.moved = true;
			this.genCurrentColumn -= 1;
		}
		if (this.randomMove == 1 && !this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals(" ")) {
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow - 1] = " ";
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow - 1] = " ";
			this.moved = true;
			this.genCurrentRow += 1;
		}
		if (this.randomMove == 2 && !this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals(" ")) {
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow - 1] = " ";
			this.moved = true;
			this.genCurrentColumn += 1;
		}
		if (this.randomMove == 3 && !this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals(" ")) {
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow + 1] = " ";
			this.moved = true;
			this.genCurrentRow -= 1;
		}
		this.firstMove = false;
	}

	public void testIfCanMove() {
		// testing if can move to new spot
		this.canMove = false;
		if (this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals("W")) {
			this.canMove = true;
		} else if (this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals("W")) {
			this.canMove = true;
		} else if (this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals("W")) {
			this.canMove = true;
		} else if (this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals("W")) {
			this.canMove = true;
		}
	}

	public void moveInStrightLine() {
		if (this.randomMove == 0 && this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals("W")) {
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
			this.moved = true;
			this.genCurrentColumn -= 1;
		}
		if (this.randomMove == 1 && this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals("W")) {
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
			this.moved = true;
			this.genCurrentRow += 1;
		}
		if (this.randomMove == 2 && this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals("W")) {
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
			this.moved = true;
			this.genCurrentColumn += 1;
		}
		if (this.randomMove == 3 && this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals("W")) {
			this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
			this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
			this.moved = true;
			this.genCurrentRow -= 1;
		}
	}

	public void turnMove() {
		if (this.lastMove == 0) {
			if (this.randomMove == 1 && this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals("W")) {
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
				this.moved = true;
				this.genCurrentRow += 1;

			}
			if (this.randomMove == 3 && this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals("W")) {
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow - 1] = " ";
				this.moved = true;
				this.genCurrentRow -= 1;
			}
		}
		if (this.lastMove == 1) {
			if (this.randomMove == 0 && this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals("W")) {
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow + 1] = " ";
				this.moved = true;
				this.genCurrentColumn -= 1;

			}
			if (this.randomMove == 2 && this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals("W")) {
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow + 1] = " ";
				this.moved = true;
				this.genCurrentColumn += 1;
			}
		}
		if (this.lastMove == 2) {
			if (this.randomMove == 1 && this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals("W")) {
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow + 1] = " ";
				this.moved = true;
				this.genCurrentRow += 1;

			}
			if (this.randomMove == 3 && this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals("W")) {
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow + 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
				this.moved = true;
				this.genCurrentRow -= 1;
			}
		}
		if (this.lastMove == 3) {
			if (this.randomMove == 0 && this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals("W")) {
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow - 1] = " ";
				this.moved = true;
				this.genCurrentColumn -= 1;

			}
			if (this.randomMove == 2 && this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals("W")) {
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow] = " ";
				this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow - 1] = " ";
				this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1] = " ";

				this.moved = true;
				this.genCurrentColumn += 1;
			}
		}

	}

	public void cannotMove() {
		if (this.lastMove == 0 || this.lastMove == 2 || this.lastMove == 3) {
			this.backtrackMove = Math.abs(this.lastMove - 2);
		} else if (this.lastMove == 1) {
			this.backtrackMove = this.lastMove + 2;
		}
		while (!this.canMove) {
			this.stuckInLoop++;
			if (this.stuckInLoop > 1000) {
				this.end = true;
			}
			this.didBacktrack = false;
			this.randomPick = (int) (Math.random() * 2);
			this.canMove = false;
			if (this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals("W")) {
				this.canMove = true;
			} else if (this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals("W")) {
				this.canMove = true;
			} else if (this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals("W")) {
				this.canMove = true;
			} else if (this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals("W")) {
				this.canMove = true;
			}

			if (this.randomPick == 0) {
				if (this.stringBoard[this.genCurrentColumn + 1][this.genCurrentRow].equals("#")
						&& this.backtrackMove != 0) {
					// System.out.println("Down");
					this.genCurrentColumn += 1;
					this.backtrackMove = 2;
					this.didBacktrack = true;
				} else if (this.stringBoard[this.genCurrentColumn - 1][this.genCurrentRow].equals("#")
						&& this.backtrackMove != 2) {
					// System.out.println("Up");
					this.genCurrentColumn -= 1;
					this.backtrackMove = 0;
					this.didBacktrack = true;
				}
			}
			if (this.randomPick == 1) {
				if (this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 1].equals("#")
						&& this.backtrackMove != 3) {
					// System.out.println("Right");
					this.genCurrentRow += 1;
					this.backtrackMove = 1;
					this.didBacktrack = true;
				} else if (this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 1].equals("#")
						&& this.backtrackMove != 1) {
					// System.out.println("Left");
					this.genCurrentRow -= 1;
					this.backtrackMove = 3;
					this.didBacktrack = true;
				}
			}

			if (this.genCurrentColumn + 3 <= this.stringBoard.length
					&& this.stringBoard[this.genCurrentColumn + 2][this.genCurrentRow].equals("W")) {
				this.genCurrentColumn += 1;
				this.lastMove = 2;
				this.didBacktrack = true;
			} else if (this.genCurrentColumn - 3 >= 0
					&& this.stringBoard[this.genCurrentColumn - 2][this.genCurrentRow].equals("W")) {
				this.genCurrentColumn -= 1;
				this.lastMove = 0;
				this.didBacktrack = true;
			} else if (this.genCurrentRow + 3 <= this.stringBoard.length
					&& this.stringBoard[this.genCurrentColumn][this.genCurrentRow + 2].equals("W")) {
				this.genCurrentRow += 1;
				this.lastMove = 1;
				this.didBacktrack = true;
			} else if (this.genCurrentRow - 3 >= 0
					&& this.stringBoard[this.genCurrentColumn][this.genCurrentRow - 2].equals("W")) {
				this.genCurrentRow -= 1;
				this.lastMove = 3;
				this.didBacktrack = true;
			}

			if (!this.didBacktrack) {
				this.backtrackMove = 5;
			}
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow] = "#";

		}

	}

	public void setPlayerStartLocation() {
		boolean b = true;
		for (int i = 0; i < this.stringBoard.length && b; i++) {
			for (int j = 0; j < this.stringBoard.length && b; j++) {
				if (this.stringBoard[j][i].equals("#")) {

					this.stringBoard[j][i] = "!";
					b = false;
				}
			}
		}

	}

	public void setFinishPostion() {
		boolean b = true;
		for (int i = this.stringBoard[0].length - 1; i >= 0 && b; i--) {
			for (int j = this.stringBoard.length - 1; j >= 0 && b; j--) {
				if (this.stringBoard[j][i].equals("#")) {
					this.stringBoard[j][i] = "O";
					b = false;
				}
			}
		}

	}

	public void errorFix() {
		for (int i = 0; i < this.stringBoard.length; i++) {
			for (int j = 0; j < this.stringBoard[0].length; j++) {
				if (i == this.stringBoard.length - 2 || i == 1 || i == (this.stringBoard.length / 2) || j == 11) {
					if (!this.stringBoard[i][j].equals("!") && !this.stringBoard[i][j].equals("!")) {
						this.stringBoard[i][j] = "#";
					}
				}
				this.randomPick = (int) (Math.random() * 3);
				if (this.stringBoard[i][j].equals("W")) {
					this.stringBoard[i][j] = " ";
				}
				if (this.stringBoard[i][j].equals(" ")) {
					if (this.randomPick == 0) {
						this.stringBoard[i][j] = "#";
					}
				}
				if (i == 0 || j == 0 || i == this.stringBoard.length - 1 || j == this.stringBoard[0].length - 1) {
					this.stringBoard[i][j] = " ";
				}
			}
		}
	}

	public void randomBoard() {
		while (!this.stringBoard[this.genStartColumn][this.genStartRow].equals("W")) {
			this.genStartColumn = (int) (Math.random() * this.stringBoard.length - 1);
			this.genStartRow = (int) (Math.random() * this.stringBoard[0].length - 1);
		}
		this.stringBoard[this.genStartColumn][this.genStartRow] = "#";

		while (!this.end) {
			this.stuckInLoop = 0;
			this.repetitions++;
			if (this.repetitions > 420) {
				this.end = true;
			}
			if (this.moved) {
				this.lastMove = this.randomMove;
			}

			// check if can move
			this.moved = false;
			this.randomMove = (int) (Math.random() * 4);

			if (this.firstMove) {
				firstMove();

			} else {
				testIfCanMove();
				if (this.canMove) {
					if (this.randomMove == this.lastMove) {
						// moving in a straight line
						moveInStrightLine();
					} else {
						// turning
						turnMove();
					}
				} else {
					// cannot move
					cannotMove();
				}

			}
			this.stringBoard[this.genCurrentColumn][this.genCurrentRow] = "#";
		}
		setPlayerStartLocation();
		errorFix();
		setFinishPostion();

	}

	public void createNodeBoard() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (stringBoard[i][j].equals("#")) {
					board[i][j].setPassability(true);
				} else if (stringBoard[i][j].equals(" ")) {
					board[i][j].setPassability(false);
				} else if (stringBoard[i][j].equals("O")) {
					board[i][j].setPassability(true);
					board[i][j].setFinish();
				}
			}
		}
	}

  public void genNodeBoard() {
    altMaze.generateBoard();
    board = altMaze.board;
  }

//will print out the new stringBoard (must use every time to see changes to the stringBoard array
	public void printBoard() {
		System.out.println(Arrays.deepToString(this.stringBoard).replace("], ", "\n").replace("[[", "")
				.replace("]]", "").replace("[", "").replace(",", ""));
	}
}
