/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Utils {
	public static boolean goUp(Cell[] cells, ArrayList<Cell[]> list) {
		Game game = new Game(cells);
		game.up();
		if (!equalCells(cells, game.myState)) {
			list.add(game.myState);
			return true;
		}
		game = null;
		return false;
	}

	public static boolean goDown(Cell[] cells, ArrayList<Cell[]> list) {
		Game game = new Game(cells);
		game.down();
		if (!equalCells(cells, game.myState)) {
			list.add(game.myState);
			return true;
		}
		game = null;
		return false;
	}

	public static boolean goLeft(Cell[] cells, ArrayList<Cell[]> list) {
		Game game = new Game(cells);
		game.left();
		if (!equalCells(cells, game.myState)) {
			list.add(game.myState);
			return true;
		}
		game = null;
		return false;
	}

	public static boolean goRight(Cell[] cells, ArrayList<Cell[]> list) {
		Game game = new Game(cells);
		game.right();
		if (!equalCells(cells, game.myState)) {
			list.add(game.myState);
			return true;
		}
		game = null;
		return false;
	}

	public static ArrayList<Cell[]> generateChildren(Cell[] cells) {
		ArrayList<Cell[]> list = new ArrayList<Cell[]>();
		goUp(cells, list);
		goDown(cells, list);
		goLeft(cells, list);
		goRight(cells, list);
		return list;
	}

	public static Cell[] copy(Cell[] cells) {
		int N = cells.length;
		Cell[] rs = new Cell[N];
		for (int i = 0; i < N; i++)
			rs[i] = new Cell(cells[i].value);
		return rs;
	}

	public static boolean equalCells(Cell[] cells1, Cell[] cell2) {
		int N = cells1.length;
		for (int i = 0; i < N; i++) {
			if (cells1[i].value != cell2[i].value)
				return false;
		}
		return true;
	}

	public static boolean isGoal(Cell[] cells, int goal) {
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].value == goal)
				return true;
		}
		return false;
	}

	public static int calExp(int a, int n) {
		if (n == 0)
			return 1;
		if (n == 1)
			return a;
		return a * calExp(a, n - 1);
	}

	public static void viewWin(String message) {
		JOptionPane.showMessageDialog(null, message, "WIN GAME",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void viewLose(String message) {
		JOptionPane.showMessageDialog(null, message, "GAME OVER",
				JOptionPane.ERROR_MESSAGE);
	}

	public static int findMaxInCells(Cell[] cells) {
		int MAX = cells[0].value;
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].value > MAX)
				MAX = cells[i].value;
		}
		return MAX;
	}

	public static int[] getColum(Cell[] cells, int i) {
		int[] col = new int[4];
		int j = i;
		for (int k = 0; k < 4; k++) {
			col[k] = cells[j].value;
			j += 4;
		}
		return col;
	}

	public static int[] getLine(Cell[] cells, int i) {
		int[] rs = new int[4];
		int j = 4 * i;
		for (int k = 0; k < 4; k++) {
			rs[k] = cells[j].value;
			j += 1;
		}
		return rs;
	}

	public static int checkRowCol(int[] line) {
		int max = 0;
		for (int i = 0; i < line.length - 1; i++) {
			if (line[i] == 0)
				continue;
			for (int j = i + 1; j < line.length; j++) {
				if (line[j] == 0)
					continue;
				else {
					if (line[j] != line[i])
						break;
					else {
						if (line[j] > max)
							max = line[j];
					}
				}
			}
		}
		return max;
	}

	private static int getMaxHeightSwipe(Cell[] cells) {
		int[] rs = new int[4];
		for (int i = 0; i < 4; i++) {
			int[] col = getColum(cells, i);
			rs[i] = checkRowCol(col);
		}
		return max(rs);
	}

	private static int getMaxWidthSwipe(Cell[] cells) {
		int[] rs = new int[4];
		for (int i = 0; i < 4; i++) {
			int[] line = getLine(cells, i);
			rs[i] = checkRowCol(line);
		}
		return max(rs);
	}

	public static int getMaxSwipe(Cell[] cells) {
		int W = Utils.getMaxWidthSwipe(cells);
		int H = Utils.getMaxHeightSwipe(cells);
		return Math.max(W, H);
	}

	private static int max(int[] array) {
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max)
				max = array[i];
		}
		return max;
	}

	public static boolean checkGoal(int goal) {
		for (int i = 1; i < 15; i++) {
			int n = Utils.calExp(2, i);
			if (n == goal)
				return true;
			else if (n > goal)
				return false;
		}
		return false;
	}

	private static final long MEGABYTE = 1024L * 1024L;

	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

}
