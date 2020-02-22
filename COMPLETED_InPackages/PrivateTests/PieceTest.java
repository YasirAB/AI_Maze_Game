import static org.junit.Assert.assertEquals;
import org.junit.Test;



public class PieceTest {

	
	@Test
	public void testConstructor(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 10, 10);
		assertEquals("testing row", 10, p.getRow());
		assertEquals("testing column", 10, p.getCol());		
	}
	
	/**@Test
	public void testConstructor_invalid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 31, 10);
		assertEquals("testing row", 0, p.getRow());
		assertEquals("testing column", 10, p.getCol());		
	}*/
	
	@Test
	public void testMoveUpValid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 10, 10);
		b.board[9][10].setPassability(true);
		p.moveUp();
		assertEquals("testing row", 9, p.getRow());
			
	}
	@Test
	public void testMoveUpInvalid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 10, 10);
		b.board[9][10].setPassability(false);
		p.moveUp();
		assertEquals("testing row", 10, p.getRow());
			
	}
	@Test
	public void testMoveDownValid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 15, 20);
		b.board[16][20].setPassability(true);
		p.moveDown();
		assertEquals("testing row", 16, p.getRow());
			
	}
	@Test
	public void testMoveDownInvalid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 20, 10);
		b.board[21][10].setPassability(false);
		p.moveDown();
		assertEquals("testing row", 20, p.getRow());
			
	}
	@Test
	public void testMoveRightValid(){
		Board b = new Board(30,30);
		Piece p = new SeekerEnemy(b,0, 10, 16);
		b.board[10][17].setPassability(true);
		p.moveRight();
		assertEquals("testing column", 17, p.getCol());
			
	}
	@Test
	public void testMoveRightInvalid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 10, 16);
		b.board[10][17].setPassability(false);
		p.moveRight();
		assertEquals("testing column", 16, p.getCol());
			
	}
	@Test
	public void testMoveLeftValid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 10, 16);
		b.board[10][15].setPassability(true);
		p.moveLeft();
		assertEquals("testing column", 15, p.getCol());
			
	}
	@Test
	public void testMoveLeftInvalid(){
		Board b = new Board(30,30);
		Piece p = new Player(b, 10, 16);
		b.board[10][15].setPassability(false);
		p.moveLeft();
		assertEquals("testing column", 16, p.getCol());
			
	}
	
	

}
