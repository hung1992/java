/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.util.ArrayList;
import java.util.Random;

public class HCBRunner implements Runnable {
	public Cell[] start;
	public int goal;
	public ArrayList<Cell[]> list;
	public Game gamePane;

	public HCBRunner(Cell[] start, int goal, ArrayList<Cell[]> list,
			Game gamePane) {
		list = new ArrayList<Cell[]>();
		this.goal = goal;
		this.start = Utils.copy(start);
		this.list = list;
		this.gamePane = gamePane;
	}

	@Override
	public void run() {
		list.add(start);
		while (!list.isEmpty()) {
			Cell[] cells = list.remove(0);
			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}
			gamePane.myState = Utils.copy(cells);
			gamePane.repaint();
			if (Utils.isGoal(cells, goal)) {
				Utils.viewWin("YOU WIN THE 2048 GAME");
				return;
			} else
				generateMoves(cells, list);
			if (list.isEmpty()) {
				Utils.viewLose("GAME OVER, YOU LOSE THE 2048 GAME");
				return;
			}
		}
	}

	private boolean isBetter(Cell[] old, Cell[] news) {
		int oM = Utils.findMaxInCells(old);
		int nM = Utils.findMaxInCells(news);
		if (oM < nM)
			return true;
		else {
			int oo = Utils.getMaxSwipe(old);
			int nn = Utils.getMaxSwipe(news);
			if (oo < nn)
				return true;
		}
		return false;
	}

	private void generateMoves(Cell[] cells, ArrayList<Cell[]> list) {
		boolean allBad = true;
		ArrayList<Cell[]> childrens = Utils.generateChildren(cells);
		int N = childrens.size();
		for (int i = 0; i < N; i++) {
			Cell[] childCell = childrens.get(i);
			if (isBetter(cells, childCell)) {
				allBad = false;
				list.add(childCell);
				return;
			}
		}
		if (allBad && N > 0) {
			Random r = new Random();
			int K = r.nextInt(N);
			list.add(childrens.get(K));
			return;
		}
		return;
	}
}
