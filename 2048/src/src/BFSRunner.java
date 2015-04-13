/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.util.ArrayList;

public class BFSRunner implements Runnable {

	public Cell[] start;
	public int goal;
	public ArrayList<Cell[]> queueList;
	public Game gamePane;

	public BFSRunner(Cell[] start, int goal, ArrayList<Cell[]> list,
			Game gamePane) {
		list = new ArrayList<Cell[]>();
		this.start = Utils.copy(start);
		this.goal = goal;
		this.queueList = list;
		this.gamePane = gamePane;
	}

	@Override
	public void run() {
		gamePane.resetGame();
		queueList.add(start);
		while (!queueList.isEmpty()) {
			Cell[] cells = queueList.remove(0);
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
				for (int i = 0; i < list.size(); i++) {
					Cell[] aCell = list.get(i);
					queueList.add(aCell);
				}
			}
			if (queueList.isEmpty())
				Utils.viewLose("GAME OVER, YOU LOSE THE 2048 GAME");
		}
	}

}
