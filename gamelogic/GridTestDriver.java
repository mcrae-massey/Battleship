package gamelogic;

public class GridTestDriver {
	public static void main(String[] args) {
		Grid myGrid = new Grid(10);
		myGrid.makeGrid();
		
		// fire 11 shots
		myGrid.fire(0, 0);
		// print after firing
		myGrid.gridFormatPrint();
		
		myGrid.fire(1, 1);
		myGrid.gridFormatPrint();
		
		myGrid.fire(2, 2);
		myGrid.gridFormatPrint();
		
		myGrid.fire(3, 3);
		myGrid.gridFormatPrint();
		
		myGrid.fire(4, 4);
		myGrid.gridFormatPrint();
		
		myGrid.fire(5, 5);
		myGrid.gridFormatPrint();
		
		myGrid.fire(6, 6);
		myGrid.gridFormatPrint();
		
		myGrid.fire(7, 7);
		myGrid.gridFormatPrint();
		
		myGrid.fire(8, 8);
		myGrid.gridFormatPrint();
		
		myGrid.fire(9, 9);
		myGrid.gridFormatPrint();
		
		//should be out of bounds and print message
		myGrid.fire(10, 10);
		myGrid.gridFormatPrint();
	}
}
