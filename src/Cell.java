
public class Cell {
	
	public int x; 
	public int y;
	private Board board;
	public boolean IsSafe = true;
	public boolean IsPit = false;
	public boolean IsExpored = false;
	
	public Cell(int pos_x, int pos_y, Board currentBoard)
	{
		board = currentBoard;
		x=pos_x;
		y=pos_y;
	}
}
