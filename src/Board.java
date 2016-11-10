import java.util.*;

public class Board {
	public int dim_x, dim_y = 0;
	public int x, y = 0;
	public Cell startingCell;
	public Cell currentCell;
	private String grid[][]; // represents the whole Game file as RxC grid
	private Cell[][] cells;

	// Knowledge base - Keeps track items that have been explored and safe cells

	// Action Constraints
	public static String MOVE = "M";
	public static String SHOOT = "S";
	public static String QUIT = "Q";

	public final static String WUMPUS = "W";
	public final static String PIT = "P";

	// PRECEPTS
	public final static String BREEZE = "B";
	public final static String STENCH = "S";
	public final static String GOLD = "G";

	public Board(String[] fileContent) {
		// Check to see if the file content has the size defined.
		if (fileContent[0].contains("Size = ") == false)
			throw new IllegalArgumentException("Invalid Input string. Not able to find size of the board");

		String sizeStr = fileContent[0].replaceAll("Size = ", "");
		String[] sizeArr = sizeStr.split(",");
		dim_x = Integer.parseInt(sizeArr[0]) + 1;
		dim_y = Integer.parseInt(sizeArr[1]) + 1;

		String[] startingPos = fileContent[1].replaceAll("E,", "").split(",");
		x = Integer.parseInt(startingPos[0]);
		y = Integer.parseInt(startingPos[1]);

		// Setup Arrays
		grid = new String[dim_x][dim_y];
		cells = new Cell[dim_x][dim_y];
		
		// Initialize and Load the file content into board
		initialize(fileContent);
	}

	public boolean isGameOver() {
		return false;
	}

	public void initialize(String[] fileContent) {
		// ****************************
		// Initialize the board
		// ****************************
		for (int i = 0; i < dim_x; i++) {
			for (int j = 0; j < dim_y; j++) {
				grid[i][j] = ""; // initialize master list
				cells[i][j] = new Cell(i, j); // grid Cell
			}
		}

		int pos_x = 0, pos_y = 0;
		
		// *******************************************************
		// Loop thru fileContent and Set the board and each cell
		//// *******************************************************
		for (int i = 2; i < fileContent.length; i++) {

			String[] input = fileContent[i].split(",");
			pos_x = Integer.parseInt(input[0]);
			pos_y = Integer.parseInt(input[1]);

			for (int j = 2; j < input.length; j++) {
				if (input[j].contains(BREEZE))
					grid[pos_x][pos_y] = grid[pos_x][pos_y] + BREEZE;
				else if (input[j].contains(WUMPUS))
					grid[pos_x][pos_y] = grid[pos_x][pos_y] + WUMPUS;
				else if (input[j].contains(STENCH))
					grid[pos_x][pos_y] = grid[pos_x][pos_y] + STENCH;
				else if (input[j].contains(GOLD))
					grid[pos_x][pos_y] = grid[pos_x][pos_y] + GOLD;
				else if (input[j].contains(PIT))
					grid[pos_x][pos_y] = grid[pos_x][pos_y] + PIT;
			}
		}
		currentCell = startingCell = cells[x][y];
		
		currentCell.IsSafe  = true;
		//currentCell.IsExpored = true;
	}
	
	public void observeConditions(Cell cell)
	{
		Integer pos_x = cell.x;
		Integer pos_y = cell.y;
		
		if(cell.IsExplored == false){
			cell.IsExplored = true;
			cell.IsSafe = true;
			cell.IsFrientier = false;
			for (Cell adj_cell : AdjecentCells(cell)) {
				if ( adj_cell.IsExplored == false){
					adj_cell.IsFrientier = true;
						
				}
			}
			
			// If Empty
			if ( IsEmpty(pos_x, pos_y))
			{
				for (Cell adj_cell : AdjecentCells(cell)) {
					if ( adj_cell.IsExplored == false)
					{
						adj_cell.IsSafe = true;
						adj_cell.IsFrientier = true;
					}
				}
				
			}
			
			//Breezy
			if(isBreezy(pos_x, pos_y))
			{	
				cell.IsBreezy = true;
				for (Cell adj_cell : AdjecentCells(cell)) {
					if ( adj_cell.IsExplored == false)
					{
						adj_cell.PitThreatCount += 1;
					}
				}
			}
			
			if(isStench(pos_x, pos_y))
			{	
				cell.IsSmelly = true;
				
				for (Cell adj_cell : AdjecentCells(cell)) {
					adj_cell.WampusThreatCount += 1;
					
					if(adj_cell.WampusThreatCount == 2) {
						adj_cell.IsWampus = true;
						for (int x = 0; x < dim_x; x++) {
							for(int y = 0; y < dim_y; y++) {
								cells[x][y].WampusThreatCount = 0;
							}
						}
						break;
					}
				}
			}
		}
	}
	
	public ArrayList<Cell> AdjecentCells(Cell cell) {
		
	
		ArrayList<Cell> adjacent_cell_list = new ArrayList<Cell>();
				
		if(cell.x != 0){
			adjacent_cell_list.add(cells[cell.x - 1][cell.y]);
		}
		
		if(cell.x != dim_x-1){
			adjacent_cell_list.add(cells[cell.x + 1][cell.y]);
		}
		
		if(cell.y != 0){
			adjacent_cell_list.add(cells[cell.x][cell.y - 1]);
		}
		
		if(cell.y != dim_y-1){
			adjacent_cell_list.add(cells[cell.x][cell.y + 1]);
		}
		
		return adjacent_cell_list;
	}
	
	public void Start()
	{

		Cell tc1; // temporary cell
		
		Stack<Cell> st = new Stack<Cell>();
		st.push(currentCell);
		
		// Use Depth first search to process all the cells in the grid
		while ( !st.isEmpty())
		{
			Cell t = st.pop();
			observeConditions(t);
			System.out.println("X=> " + t.x + ", Y ==> " + t.y);
				
			// if it has already been explored, then move on.
			//if ( t.IsExpored) continue;
			
			
			//****************************************************
			//Push Adjacent rooms to the stack
			//****************************************************
			
			//Step 1: Cell X -1, Y
			if (t.x > 0 ) {
				tc1 =cells[t.x-1][t.y];
				if ( tc1.IsSafe  && tc1.IsExplored == false) 
					st.push(tc1);
			}
			
			//Step 2: Cell X + 1, Y
			if (t.x < dim_x - 1){
				tc1 = cells[t.x+1][t.y];
				if ( tc1.IsSafe && tc1.IsExplored == false)
					st.push(tc1);
			}
			
			// Step 3: X, Y-1
			if ( t.y > 0 ) {
				tc1 = cells[t.x][t.y-1];
				if (tc1.IsSafe && tc1.IsExplored == false)
				st.push(tc1);
			}
			
			//Step 4: X, Y + 1 
			if ( t.y < dim_y - 1){
				tc1 = cells[t.x][t.y+1];
				if (tc1.IsSafe && tc1.IsExplored == false)
				st.push(tc1);
			}
			
			//****************************************************
			// Check the explored and build the logic here
			//****************************************************
			
			
			// Hardcoding the values
			t.IsExplored = true;
			t.IsSafe = true;
		}
		
	}

	//Based on currentCell, gain knowledge about adjacent cells.  
	public Cell getNextMove() {
		
		currentCell.IsExplored = true;
		currentCell.IsSafe  = true;
		
		// Step 1: X- 1, Y
		if (x!=0 && cells[x-1][y].IsSafe == false)
		{
			if (isBreezy(x, y))
				cells[x-1][y].IsPit = true;
		}
		
		// Step 2: X+ 1, Y
		if (x!=dim_x -1  && cells[x+1][y].IsSafe == false)
		{
			if (isBreezy(x, y))
				cells[x+1][y].IsPit = true;	
		}
		
		// Step 3: X, Y-1
		if (y!=0 && cells[x][y-1].IsSafe == false)
		{
			if (isBreezy(x, y))
				cells[x][y-1].IsPit = true;
		}
				
				
		// Step 4: X, Y+1 
		if (x!=dim_y -1  && cells[x][y+1].IsSafe == false)
		{
			if (isBreezy(x, y))
				cells[1][y+1].IsPit = true;
		}
		
		return currentCell;
	}
	
	
	

	// Right
	public boolean MoveRight() {
		Cell cell = currentCell;

		if (cell.y + 1 < dim_y) {
			// Move Right
			currentCell = cells[cell.x][cell.y + 1];
			return true;
		}
		return false;
	}

	// Left
	public boolean MoveLeft() {
		Cell cell = currentCell;

		// Move Left
		if (cell.y - 1 >= 0) {
			// Move left
			currentCell = cells[cell.x][cell.y - 1];
			return true;
		} else
			return false;
	}

	// UP
	public boolean MoveUp() {
		Cell cell = currentCell;
		// Move up
		if (cell.x - 1 >= 0) {
			// Move UP
			currentCell = cells[cell.x - 1][cell.y];
			return true;
		} else
			return false;

	}

	// DOWN
	public boolean MoveDown() {
		Cell cell = currentCell;

		if (cell.x + 1 < dim_x) {
			// Move DOwn
			currentCell = cells[cell.x][cell.x + 1];
			return true;
		} else
			return false;
	}
	
	
	

	public String[][] getGrid() {
		return grid;
	}
	
	
	//*************************************************
	// ******* Setters and Getters *******************/
	//*************************************************
	public boolean isBreezy(int x, int y) {
		return ( grid[x][y].contains(Board.BREEZE));
	}

	public boolean isStench(int x, int y) {
		return ( grid[x][y].contains(Board.STENCH));
	}

	public boolean isGold(int x, int y) {
		return ( grid[x][y].contains(Board.GOLD));
	}
	
	public boolean isWumpus(int x, int y) {
		return ( grid[x][y].contains(Board.WUMPUS));
	}

	public boolean isPit(int x, int y) {
		return ( grid[x][y].contains(Board.PIT));
	}
	
	public boolean IsEmpty(int x, int y)
	{
		return grid[x][y].isEmpty();
	}

}
