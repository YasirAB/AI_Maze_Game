import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GameStateTest {

	
	@Test
	public void testCheckLoseTrue(){
		GameState gs = new GameState();
		Board b = new Board(30,30);
		SeekerEnemy e = new SeekerEnemy(b,0, 10, 10);
		Player p = new Player(b, 10, 10);
		boolean lose = gs.checkLose(b);
		
		assertEquals("the palyer doesnot exist so game should be over", true, lose);
		
	}
	
	@Test
	public void testCheckLoseFalse(){
		GameState gs = new GameState();
		Board b = new Board(30,30);
		SeekerEnemy e = new SeekerEnemy(b,0, 10, 11);
		Player p = new Player(b, 10, 10);
		boolean lose = gs.checkLose(b);
		
		assertEquals("the palyer does exist so game should not be over", false, lose);
		
	}
	
	@Test
	public void testCheckWinTrue(){
		GameState gs = new GameState();
		Board b = new Board(30,30);
		b.board[10][10].setFinish();
		Player p = new Player(b, 10, 10);
		boolean win = gs.checkWin(b);
		
		assertEquals("the finish doesn't exist so game should be over", true, win);
		
	}
	
	@Test
	public void testCheckWinFalse(){
		GameState gs = new GameState();
		Board b = new Board(30,30);
		b.board[10][10].setFinish();
		Player p = new Player(b, 8, 10);
		boolean win = gs.checkWin(b);
		
		assertEquals("the finish does exist so game should not be over", false, win);
		
	}
	
	
}
