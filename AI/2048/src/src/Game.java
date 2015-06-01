/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

public class Game extends JPanel {

	private static final long serialVersionUID = -1164301520562669635L;
	private static final Color BG_COLOR = new Color(0xbbada0);
	private static final String FONT_NAME = "Arial";
	private static final int TILE_SIZE = 64;
	private static final int TILES_MARGIN = 16;

	public Cell[] myState;
	public boolean isWin = false;
	public boolean isLose = false;
	public int myScore = 0;
	public int myGoal = 0;

	public Game() {
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					resetGame();
				}
				if (!canMove()) {
					isLose = true;
				}

				if (!isWin && !isLose) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						left();
						break;
					case KeyEvent.VK_RIGHT:
						right();
						break;
					case KeyEvent.VK_DOWN:
						down();
						break;
					case KeyEvent.VK_UP:
						up();
						break;
					}
				}

				if (!isWin && !canMove()) {
					isLose = true;
				}

				repaint();
			}
		});
		resetGame();
	}

	public Game(Cell[] sts) {
		myState = Utils.copy(sts);
	}

	public void resetGame() {
		myScore = 0;
		isWin = false;
		isLose = false;
		myState = new Cell[4 * 4];
		for (int i = 0; i < myState.length; i++) {
			myState[i] = new Cell();
		}
		addCell();
		addCell();
	}

	public void left() {
		boolean needAddTile = false;
		for (int i = 0; i < 4; i++) {
			Cell[] line = getLine(i);
			Cell[] merged = mergeLine(moveLine(line));
			setLine(i, merged);
			if (!needAddTile && !compare(line, merged)) {
				needAddTile = true;
			}
		}

		if (needAddTile) {
			addCell();
		}
	}

	public void right() {
		myState = rotate(180);
		left();
		myState = rotate(180);
	}

	public void up() {
		myState = rotate(270);
		left();
		myState = rotate(90);
	}

	public void down() {
		myState = rotate(90);
		left();
		myState = rotate(270);
	}

	private Cell tileAt(int x, int y) {
		return myState[x + y * 4];
	}

	private void addCell() {
		List<Cell> list = availableSpace();
		if (!availableSpace().isEmpty()) {
			int index = (int) (Math.random() * list.size()) % list.size();
			Cell emptyTime = list.get(index);
			emptyTime.value = Math.random() < 0.9 ? 2 : 4;
		}
	}

	private List<Cell> availableSpace() {
		final List<Cell> list = new ArrayList<Cell>(16);
		for (Cell t : myState) {
			if (t.isEmpty()) {
				list.add(t);
			}
		}
		return list;
	}

	private boolean isFull() {
		return availableSpace().isEmpty();
	}

	boolean canMove() {
		if (!isFull()) {
			return true;
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Cell t = tileAt(x, y);
				if ((x < 3 && t.value == tileAt(x + 1, y).value)
						|| ((y < 3) && t.value == tileAt(x, y + 1).value)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean compare(Cell[] line1, Cell[] line2) {
		if (line1 == line2) {
			return true;
		} else if (line1.length != line2.length) {
			return false;
		}

		for (int i = 0; i < line1.length; i++) {
			if (line1[i].value != line2[i].value) {
				return false;
			}
		}
		return true;
	}

	private Cell[] rotate(int angle) {
		Cell[] newTiles = new Cell[4 * 4];
		int offsetX = 3, offsetY = 3;
		if (angle == 90) {
			offsetY = 0;
		} else if (angle == 270) {
			offsetX = 0;
		}

		double rad = Math.toRadians(angle);
		int cos = (int) Math.cos(rad);
		int sin = (int) Math.sin(rad);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				int newX = (x * cos) - (y * sin) + offsetX;
				int newY = (x * sin) + (y * cos) + offsetY;
				newTiles[(newX) + (newY) * 4] = tileAt(x, y);
			}
		}
		return newTiles;
	}

	private Cell[] moveLine(Cell[] oldLine) {
		LinkedList<Cell> l = new LinkedList<Cell>();
		for (int i = 0; i < 4; i++) {
			if (!oldLine[i].isEmpty()) {
				l.addLast(oldLine[i]);
			}
		}
		if (l.size() == 0) {
			return oldLine;
		} else {
			Cell[] newLine = new Cell[4];
			ensureSize(l, 4);
			for (int i = 0; i < 4; i++) {
				newLine[i] = l.removeFirst();
			}
			return newLine;
		}
	}

	private Cell[] mergeLine(Cell[] oldLine) {
		LinkedList<Cell> list = new LinkedList<>();
		for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
			int num = oldLine[i].value;
			if (i < 3 && oldLine[i].value == oldLine[i + 1].value) {
				num *= 2;
				myScore += num;
				if (num == myGoal) {
					isWin = true;
				}
				i++;
			}
			list.add(new Cell(num));
		}
		if (list.size() == 0) {
			return oldLine;
		} else {
			ensureSize(list, 4);
			return list.toArray(new Cell[4]);
		}
	}

	private static void ensureSize(java.util.List<Cell> l, int s) {
		while (l.size() != s) {
			l.add(new Cell());
		}
	}

	private Cell[] getLine(int index) {
		Cell[] result = new Cell[4];
		for (int i = 0; i < 4; i++) {
			result[i] = tileAt(i, index);
		}
		return result;
	}

	private void setLine(int index, Cell[] re) {
		System.arraycopy(re, 0, myState, index * 4, 4);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				drawTile(g, myState[x + y * 4], x, y);
			}
		}
	}

	private void drawTile(Graphics g2, Cell tile, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
		int value = tile.value;
		int xOffset = offsetCoors(x);
		int yOffset = offsetCoors(y);
		g.setColor(tile.getBackground());
		g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
		g.setColor(tile.getForeground());
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font(FONT_NAME, Font.BOLD, size);
		g.setFont(font);

		String s = String.valueOf(value);
		final FontMetrics fm = getFontMetrics(font);

		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

		if (value != 0) {
			g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE
					- (TILE_SIZE - h) / 2 - 2);
		}

		if (isWin || isLose) {
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(78, 139, 202));
			g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
			if (isWin) {
				g.drawString("You Win!", 68, 150);
			}
			if (isLose) {
				g.drawString("Game Over!", 50, 130);
			}
			if (isWin || isLose) {
				g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
				g.setColor(new Color(128, 128, 128, 128));
				g.drawString("Press ESC to play again", 80, getHeight() - 40);
			}
		}
		g.setFont(new Font(FONT_NAME, Font.BOLD, 18));

	}

	private static int offsetCoors(int arg) {
		return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
	}

}
