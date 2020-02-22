/**
* Checks to see if the player has won the game or lost the game
**/

public class GameState {

/** checks if player is over to finish square in maze
 * thus signaling a win if player has won return true
 * @param b current board
 * @return true if user has won else false
 */

public boolean checkWin(Board b) {
	boolean win = false;
	for (int i = 0; i < b.getRows(); i++) {
	    for (int j = 0; j < b.getColumns(); j++) {
	        if (b.board[i][j].checkPlayer() && b.board[i][j].getFinish()) {
	        	win = true;
	        }
	    }
	}

return win;
}

/** checks if AI has moved on top of player this ending the game
 * if player has lost return true and reset the board
 *
 * @param b current board
 * @return return true if user has lost
 */
public boolean checkLose(Board b ) {
	boolean lose = false;
	for (int i = 0; i < b.getRows(); i++) {
	    for (int j = 0; j <  b.getColumns(); j++) {
	        if (b.board[i][j].checkPlayer() && b.board[i][j].checkEnemy()) {
	        	lose = true;
	        }
	    }
	}


	return lose;
}
}
