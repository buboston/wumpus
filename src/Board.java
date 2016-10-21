
public class Board {
	public int dim_x, dim_y = 0;
	public int start_x, start_y=0;

	public String grid[][];
	
	// define the states
	public boolean[][] breeze;
	public boolean[][] stench;
	public boolean[][] glitter;
	public boolean[][] pits;
	public boolean[][] wumpus;

	// Knowledge base - Keeps track items that have been explored and safe cells
	public boolean[][] explored;
	public boolean[][] safe;

	// Action Constraints
	public static String MOVE = "M";
	public static String SHOOT = "S";
	public static String QUIT = "Q";
	

	public final static String WUMPUS="W";
	public final static String PIT="P";
	
	// PRECEPTS	
	public final static String BREEZE="B";
	public final static String STENCH="S";
	public final static String GLITTER = "G";
	

	public Board(String[] fileContent) {	
		// Check to see if the file content has the size defined.
		if ( fileContent[0].contains("Size = ") == false)
			throw new IllegalArgumentException("Invalid Input string. Not able to find size of the board");
	
		
		String sizeStr = fileContent[0].replaceAll("Size = ", "");
		String[] sizeArr = sizeStr.split(",");
		dim_x= Integer.parseInt(sizeArr[0])+1;
		dim_y= Integer.parseInt(sizeArr[1])+1;
		
		String[] startingPos = fileContent[1].replaceAll("E,", "").split(",");
		start_x= Integer.parseInt(startingPos[0]);
		start_y= Integer.parseInt(startingPos[1]);
		
		// Setup Arrays
		grid = new String[dim_x][dim_y];
		
		breeze = new boolean[dim_x][dim_y];
		stench = new boolean[dim_x][dim_y];
		glitter = new boolean[dim_x][dim_y];
		pits = new boolean[dim_x][dim_y];
		wumpus = new boolean[dim_x][dim_y];
		explored = new boolean[dim_x][dim_y];
		safe= new boolean[dim_x][dim_y];
		
		// Initialize and Load the file content into board
		initialize(fileContent);
	}

	public void initialize(String[] fileContent) {
		//****************************
		// Initialize the board	
		//****************************
		for (int i = 0; i < dim_x; i++) {
			for (int j = 0; j < dim_y; j++) {
				grid[i][j] = ""; // initialize with 'x'
				
				breeze[i][j] = false;
				stench[i][j] = false;
				glitter[i][j] = false;
				pits[i][j] = false;
				wumpus[i][j] = false;
				explored[i][j] = false;
				safe[i][j] = false;
			}
		}
		
		int x=0,y=0;
		//****************************************
		// Loop thru fileContent and Set the board
		//****************************************
		for (int i = 2; i < fileContent.length; i++) {
				
			String[] input = fileContent[i].split(",");
			x= Integer.parseInt(input[0]);
			y= Integer.parseInt(input[1]);
			
			for (int j=2; j<input.length;j++)
			{
				if ( input[j].contains(BREEZE)) setBreeze(x, y);
				else if ( input[j].contains(WUMPUS)) setWumpus(x, y);
				else if ( input[j].contains(STENCH)) setStench(x, y);
				else if ( input[j].contains(GLITTER)) setGlitter(x, y);
				else if ( input[j].contains(PIT)) setPit(x, y);
			}	
		}
	}
	
	//***********************************************
	// PRECEPTS 
	//***********************************************
	
	public boolean isStinky(int x, int y, int n){
				
		if(x != 0 && grid[x-1][y].contains(WUMPUS))
			return true;
		if(x != n-1 && grid[x+1][y].contains( WUMPUS))
			return true;
		if(y != 0 && grid[x][y-1].contains(WUMPUS))
			return true;
		if(y != n-1 && grid[x][y+1].contains(WUMPUS))
			return true;
		return false;
	}
	

	
	public boolean isBreezy(int x, int y, int n){
		if(x != 0 && grid[x-1][y].contains(PIT))
			return true;
		if(x != n-1 && grid[x+1][y].contains(PIT))
			return true;
		if(y != 0 && grid[x][y-1].contains(PIT))
			return true;
		if(y != n-1 && grid[x][y+1].contains(PIT))
			return true;
		return false;
	}
	
	//*************************************************
	// ******* Setters and Getters *******************/
	//*************************************************
	public void setBreeze(int x, int y) {
		breeze[x][y]=true;
		grid[x][y] = grid[x][y]+ BREEZE;
	}

	public String getBreeze(int x, int y) {
		return grid[x][y];
	}

	public void setStench(int x, int y) {
		stench[x][y]=true;
		grid[x][y] = grid[x][y]+ STENCH;
	}

	public String getStench(int x, int y) {
		return grid[x][y];
	}

	public void setGlitter(int x, int y) {
		glitter[x][y]=true;
		grid[x][y] = grid[x][y] + GLITTER;
	}

	public String getGlitter(int x, int y) {
		return grid[x][y];
	}

	
	public void setExplored(int x, int y) {
		explored[x][y] = true;
	}

	public boolean getExplored(int x, int y) {
		return explored[x][y];
	}

	public void setWumpus(int x, int y) {
		wumpus[x][y]=true;
		grid[x][y] = grid[x][y] + WUMPUS;
	}

	public String getWumpus(int x, int y) {
		return grid[x][y];
	}

	public void setPit(int x, int y) {
		pits[x][y]=true;
		grid[x][y] = grid[x][y] + PIT;
	}

	public String getPit(int x, int y) {
		return grid[x][y];
	}

	public void setSafe(int x, int y) {
		safe[x][y] = true;
	}

	public boolean getSafe(int x, int y) {
		return safe[x][y];
	}

}
