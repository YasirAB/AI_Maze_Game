import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class SurrondingsCheckerTest {
	@Test
	public void testConstructorForNewIcon(){
		SurroundingsChecker s = new SurroundingsChecker("TEST");
		assertEquals("testing new icon", "TEST", s.playerPiece);
		
	}
	
		@Test
		public void testCheckEmptyFull(){
			SurroundingsChecker s = new SurroundingsChecker();
			Board b = new Board(30,30);
			b.board[9][10].setPassability(false);
			boolean r = s.checkEmpty(b,9,10);
			assertEquals("should be false spot is full", false, r);
			
		}
		@Test
		public void testCheckEmptyForPlayerEnemy(){
			SurroundingsChecker s = new SurroundingsChecker();
			Board b = new Board(30,30);
			b.board[9][10].setPassability(true);
			b.board[9][10].setEnemy(true);
			boolean r = s.checkEmptyForPlayer(b,9,10);
			assertEquals("should be false spot occupied by enemy", false, r);
			
		}
		@Test
		public void testcheckAround(){
			SurroundingsChecker s = new SurroundingsChecker();
			Board b = new Board(30,30);
			b.board[9][10].setPassability(true);
			b.board[11][10].setPassability(true);
			b.board[10][11].setPassability(false);
			b.board[10][9].setPassability(false);
			ArrayList<int[]> r = s.checkAround(b,10,10);
			assertTrue("should be 2 open spaces", r.size() == 2);
			
		}
		@Test
		public void testcheckAroundPlayer(){
			SurroundingsChecker s = new SurroundingsChecker();
			Board b = new Board(30,30);
			b.board[9][10].setPassability(true);
			b.board[11][10].setPassability(true);
			b.board[10][11].setPassability(false);
			b.board[10][9].setPassability(false);
			b.board[9][10].setEnemy(false);
			b.board[11][10].setEnemy(true);
			b.board[10][11].setEnemy(true);
			b.board[10][9].setEnemy(false);
			ArrayList<int[]> r = s.checkAroundPlayer(b,10,10);
			assertTrue("the size of the array should equal 1", r.size() == 1);
			
		}
		@Test
		public void testcheckIfinArrayNot(){
			SurroundingsChecker s = new SurroundingsChecker();
			Board b = new Board(30,30);
			b.board[9][10].setPassability(true);
			b.board[11][10].setPassability(true);
			b.board[10][11].setPassability(false);
			b.board[10][9].setPassability(false);
			ArrayList<int[]> r = s.checkAround(b,10,10);
			assertTrue("should not be found in array", !s.isInArray(r,10,11));
			
		}
		@Test
		public void testcheckIfinArrayTrue(){
			SurroundingsChecker s = new SurroundingsChecker();
			Board b = new Board(30,30);
			b.board[9][10].setPassability(true);
			b.board[11][10].setPassability(true);
			b.board[10][11].setPassability(false);
			b.board[10][9].setPassability(false);
			ArrayList<int[]> r = s.checkAround(b,10,10);
			assertTrue("should be found in array", s.isInArray(r,11,10));
			
		}
		
		@Test
		public void testManhattenScore(){
			SurroundingsChecker s = new SurroundingsChecker();
			int man = s.manhattanScore(5,7,12,16);
			
			assertEquals("Manhatten score calcuated should be",16 , man);
			
		}
		
		@Test
		public void testManhattenScoreNegative(){
			SurroundingsChecker s = new SurroundingsChecker();
			int man = s.manhattanScore(20,25,12,16);
			
			assertEquals("Manhatten score calcuated should be",17 , man);
			
		}
		
		
	    
}
