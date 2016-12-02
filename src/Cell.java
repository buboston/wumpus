
public class Cell {
	
	public int x; 
	public int y;
	public boolean IsBreezy = false;
	public boolean IsSafe = false;
	public boolean IsSmelly = false;
	public boolean IsPit = false;
	public boolean IsExplored = false;
	public Integer PitThreatCount = 0;
	public Integer WumpusThreatCount = 0;
	public boolean IsWumpus;
	public boolean IsFrientier = false;
	
	public Cell(int pos_x, int pos_y)
	{
		x=pos_x;
		y=pos_y;
	}
	
	public void Display() {
		 Display("");
	}

	
	public void Display(String prefix)
	{
		String str="X=> " + x + ", Y ==> " + y +" ==> ";
		
		if( IsBreezy) str+= "B";
		
		if( IsSmelly) str+= "S";
		if( IsSafe) str+= "[SF]";
		if( IsPit) str+= "P";
		if( IsWumpus) str+= "W";
		if( IsExplored) str+= "[EX]";
		
		
		
		str+= " Wumpus Count" + WumpusThreatCount;
		
		str+= " Pit Count" + PitThreatCount;
		
		System.out.println(prefix + " " + str);
		 
	}
}
