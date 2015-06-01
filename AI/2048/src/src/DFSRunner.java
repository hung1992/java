/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.util.ArrayList;

public class DFSRunner implements Runnable {

	public Cell[] start;
	public int goal;
	public ArrayList<Cell[]> stackList;
	public Game gamePane;

	public DFSRunner(Cell[] start, int goal, ArrayList<Cell[]> list,
			Game gamePane) {
		list = new ArrayList<Cell[]>();
		this.start = Utils.copy(start);
		this.goal = goal;
		this.stackList = list;
		this.gamePane = gamePane;
	}

	@Override
	public void run() {
		this.gamePane.resetGame();
		stackList.add(start);
		while (!stackList.isEmpty()) {
			Cell[] cells = stackList.remove(0); // popStack
			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}
			gamePane.myState = Utils.copy(cells);
			gamePane.repaint();
			if (Utils.isGoal(cells, goal)) {
				Utils.viewWin("YOU WIN THE 2048 GAME");
				return;
			} else {
				ArrayList<Cell[]> list = Utils.generateChildren(cells);
				for (int i = list.size() - 1; i >= 0; i--) {
					Cell[] st = list.get(i);
					stackList.add(0, st); // pushStack
				}
			}
			if (stackList.isEmpty())
				Utils.viewLose("GAME OVER , YOU LOSE THE 2048 GAME");
		}
	}
}
