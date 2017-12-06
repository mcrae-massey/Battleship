package server;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author McRae Massey and CJ Bland
 * @version 11/17/2017
 * grid class makes a grid, populates it with the appropriate amount of ships and
 * provides the fire() method to hit the ships.
 *  exit 1 - board size too small.
 *  exit 2 - fired out of bounds.
 */
public class Grid {

	/** Size of rows and columns for the grid */
	private int size;

	/** 2D array to star values for grid */
	private String[][] grid;

	/** Array of ship types */
	private String[] type;

	/** Array of ship sizes */
	private int[] typeSize;

	/** Default number of ships */
	private int numShips = 5;

	/**
	 * Constructor initializes the size of the grid.
	 * 
	 * @param size
	 *            the length and width of the grid
	 */
	public Grid(int size) {
		this.size = size;
	}

	/**
	 * Makes an empty grid the populates it with ships and prints it out.
	 */
	public void makeGrid() {
		grid = new String[size][size];

		fill(grid, " ");
		placeShips();
		gridFormatPrint();
	}

	/**
	 * Prints out the grid structure with numbers on each axis.
	 * 
	 * @param grid - the grid to be printed.
	 */
	public void gridFormatPrint() {

		// spacing for formatting
		System.out.print("   ");

		// print column numbers
		for (int index = 0; index < grid[0].length; index++) {
			System.out.print("  " + index + " ");
		}

		// new line for formatting
		System.out.println("");

		String split = "";
		StringJoiner rowJoin = new StringJoiner("+", "|", "|");
		for (int rowIndex = 0; rowIndex < grid[0].length; rowIndex++) {
			rowJoin.add(String.format("%3s", "").replace(" ", "-"));
		}
		split = rowJoin.toString();

		int colIndex = 0;
		for (String[] row : grid) {
			StringJoiner colJoin = new StringJoiner(" | ", "| ", " |");

			// print row numbers
			System.out.print("   ");
			colJoin.add("" + colIndex);
			colIndex++;

			for (String col : row) {
				colJoin.add(String.format("%s", col));
			}
			System.out.println(split);
			System.out.println(colJoin.toString().substring(1));
		}
		System.out.println("   " + split);
		System.out.println("");
	}

	/**
	 * This method will fill the grid 2D array with the default value passed in
	 * as a parameter.
	 * 
	 * @param array
	 *            - the grid
	 * @param element
	 *            - what the grid should be initialized with(space).
	 */
	public void fill(String[][] array, String element) {
		for (String[] subarray : array) {
			Arrays.fill(subarray, element);
		}
	}

	/**
	 * Checks the size of the grid to use the correct amount of ships then
	 * randomly populates the board with one ship of each type with that types
	 * length. The ships will not overlap one another.
	 */
	public void placeShips() {

		// direction either vertical or horizontal
		int direction = 0;

		// x coordinate
		int x = 0;

		// y coordinate
		int y = 0;

		// stops to checking loop if there is no overlap
		boolean flag;

		// turns true if ship is already occupying space
		boolean overlap;

		// Type and Length
		// --------------------------------
		// (A)Aircraft Carrier --------- 5
		// (B)Battleship --------------- 4
		// (S)Submarine ---------------- 3
		// (D)Destroyer ---------------- 3
		// (P)Patrol Boat -------------- 2
		type = new String[] { "A", "B", "S", "D", "P" };
		typeSize = new int[] { 5, 4, 3, 3, 2 };

		// need to change amount of ships for smaller board
		if (size == 4) {
			// take out the Aircraft Carrier if the board size is 4
			numShips = 4;
			type = new String[] { "B", "S", "D", "P" };
			typeSize = new int[] { 4, 3, 3, 2 };
			// error if board size is smaller than 4
		} else if (size < 4) {
			System.out.println("Board size too small!");
			java.lang.System.exit(1);
		}
		for (int i = 0; i < numShips; i++) {

			flag = true;
			while (flag) {
				overlap = false;

				// get a random x coordinate
				x = (int) (Math.random() * (size));

				// get a random y coordinate
				y = (int) (Math.random() * (size)); // get a random y coordinate

				// get a random horizontal or vertical direction,
				// 0 = horizontal
				// 1 = vertical
				direction = (int) (Math.random() * (2));

				if ((grid[x][y] == " ") && (((direction == 0) && ((x + typeSize[i]) <= size))
						|| ((direction == 1) && ((y + typeSize[i]) <= size)))) {
					// check if empty for whole ship length
					for (int j = 0; j < typeSize[i]; j++) {
						if ((direction == 0) && ((grid[x + j][y] != " "))) {
							overlap = true;
						} else if ((direction == 1) && ((grid[x][y + j] != " "))) {
							overlap = true;
						}
					}
					if (overlap == false) {
						flag = false;
					}
				}
			}
			for (int n = 0; n < typeSize[i]; n++) {
				if (direction == 0) {
					grid[x + n][y] = type[i];
				} else {
					grid[x][y + n] = type[i];
				}
			}
		} // end for
	}// end placeShips

	/**
	 * Will show the "@" on a hit and "M" on a miss on the grid.
	 * 
	 * @param x - value of x coordinate 
	 * @param y - value of y coordinate 
	 */
	public void fire(int x, int y) {
		if ((x > size - 1) || (y > size - 1)) {
			System.out.println("You fired out of bounds!");
			//should not exit in real application
			//this is for testing purposes only
			java.lang.System.exit(2);
		} else if ((grid[x][y] == " ")||(grid[x][y] == "M")) {
			// miss
			grid[x][y] = "M";
		} else {
			// hit
			grid[x][y] = "@";
		}
	}// end fire

	/**
	 * Prints a grid that only shows hits and misses.
	 */
	public void gridPrintOther() {
		// make a copy of the grid
		String[][] gridCopy = grid.clone();
		for (int i = 0; i < gridCopy.length; i++) {
		    gridCopy[i] = gridCopy[i].clone();
		}
	
		// remove the ships to show just hits and misses
		for (int i = 0; i < gridCopy.length; i++) {
			for (int j = 0; j < gridCopy[i].length; j++) {
				if ((gridCopy[i][j] != " ") && (gridCopy[i][j] != "@") && (gridCopy[i][j] != "M")) {
					gridCopy[i][j] = " ";
				}else{
					gridCopy[i][j] = gridCopy[i][j];
				}
			} // end j loop
		} // end i loop
		
			// spacing for formatting
		System.out.print("   ");

		// print column numbers
		for (int index = 0; index < gridCopy[0].length; index++) {
			System.out.print("  " + index + " ");
		}

		// new line for formatting
		System.out.println("");

		String split = "";
		StringJoiner rowJoin = new StringJoiner("+", "|", "|");
		for (int rowIndex = 0; rowIndex < gridCopy[0].length; rowIndex++) {
			rowJoin.add(String.format("%3s", "").replace(" ", "-"));
		}
		split = rowJoin.toString();

		int colIndex = 0;
		for (String[] row : gridCopy) {
			StringJoiner colJoin = new StringJoiner(" | ", "| ", " |");

			// print row numbers
			System.out.print("   ");
			colJoin.add("" + colIndex);
			colIndex++;

			for (String col : row) {
				colJoin.add(String.format("%s", col));
			}
			System.out.println(split);
			System.out.println(colJoin.toString().substring(1));
		}
		System.out.println("   " + split);
		System.out.println("");
	}

	/**
	 * Checks the grid to see if any ships are left. If there are none left this
	 * method returns a true value.
	 * 
	 * @return boolean
	 */
	public boolean allSunk() {
		List<String> list = Arrays.asList(Arrays.asList(grid).get(size - 1));
		if (list.contains("A") || list.contains("B") || list.contains("S") || list.contains("D")
				|| list.contains("P")) {
			return false;
		} else {
			// ships are sunk if they are not in the 2D array
			return true;
		} // end if else
	}
}
