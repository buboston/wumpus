
public class Cell {
	
	public int x; 
	public int y;
	public boolean IsBreezy = true;
	public boolean IsSafe = false;
	public boolean IsSmelly = false;
	public boolean IsPit = false;
	public boolean IsExplored = false;
	public Integer PitThreatCount = 0;
	public Integer WampusThreatCount = 0;
	public boolean IsWampus;
	public boolean IsFrientier = false;
	
	public Cell(int pos_x, int pos_y)
	{
		x=pos_x;
		y=pos_y;
	}
}
