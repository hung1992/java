package sources;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Frame extends JFrame {
	private static final long serialVersionUID = -1383468418860401608L;
	protected JRadioButton rbDFS;
	protected JRadioButton rbBFS;
	protected JRadioButton rbHCB;
	protected JPanel radioPane;
	private ButtonGroup group;
	private JPanel contentPane;
	private JLabel lbGoal;
	protected JTextField txtGoal;
	protected JButton btRun;
	protected JButton btPlay;
	protected JPanel bottomPane;
	protected JTextArea textArea;
	private JScrollPane scrollPane;
	protected int goal;
	protected GamePanel game;

	public Frame() {
		game = new GamePanel();
		createFrame();
	}

	private void createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 423);
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(null);
		inits();
		contentPane.add(radioPane);
		contentPane.add(btRun);
		contentPane.add(btPlay);
		contentPane.add(bottomPane);
		// contentPane.add(topPane);
	}

	private void inits() {
		rbDFS = new JRadioButton("DFS");
		rbBFS = new JRadioButton("BFS");
		rbHCB = new JRadioButton("HCB");
		group = new ButtonGroup();
		btRun = new JButton("Run AI");
		btRun.setBounds(94, 56, 103, 23);
		btPlay = new JButton("By Hand");
		btPlay.setBounds(252, 56, 108, 23);
		bottomPane = new JPanel();
		bottomPane.setBounds(0, 90, 434, 294);
		radioPane = new JPanel();
		radioPane.setBounds(10, 11, 414, 23);
		txtGoal = new JTextField();
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setCaretPosition(0);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		bottomPane.setLayout(new BorderLayout());
		bottomPane.add(scrollPane);

		group.add(rbBFS);
		group.add(rbDFS);
		group.add(rbHCB);
		radioPane.setLayout(new GridLayout(1, 4));
		radioPane.add(rbBFS, 0);
		radioPane.add(rbDFS, 1);
		radioPane.add(rbHCB, 2);
		lbGoal = new JLabel("Goal : ");
		radioPane.add(lbGoal, 3);
		txtGoal = new JTextField();
		radioPane.add(txtGoal, 4);
		goal = 2;
	}

	// /////////////////////////////////////////////////////////////////////////////

	public ActionListener btRunAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};

	public ActionListener btPlayAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};

	public ActionListener rbBFSAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};

	public ActionListener rbDFSAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};

	public ActionListener rbHCBAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	};

	// /////////////////////////////////////////////////////////////////////////////

	private ArrayList<Tile[]> generateSuccessor(Tile[] startTiles) {
		ArrayList<Tile[]> lists = new ArrayList<Tile[]>();
		lists.add(goUp(startTiles));
		lists.add(goDown(startTiles));
		lists.add(goLeft(startTiles));
		lists.add(goRight(startTiles));
		return lists;
	}

	private Tile[] cloneTile(Tile[] oldTiles) {
		Tile[] tiles = new Tile[16];
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = oldTiles[i];
		return tiles;
	}

	private Tile[] goUp(Tile[] tiles) {
		game.myTiles = cloneTile(tiles);
		game.up();
		Tile[] newTiles = cloneTile(game.myTiles);
		return newTiles;
	}

	private Tile[] goDown(Tile[] tiles) {
		game.myTiles = cloneTile(tiles);
		game.down();
		Tile[] newTiles = cloneTile(game.myTiles);
		return newTiles;
	}

	private Tile[] goLeft(Tile[] tiles) {
		game.myTiles = cloneTile(tiles);
		game.left();
		Tile[] newTiles = cloneTile(game.myTiles);
		return newTiles;
	}

	private Tile[] goRight(Tile[] tiles) {
		game.myTiles = cloneTile(tiles);
		game.right();
		Tile[] newTiles = cloneTile(game.myTiles);
		return newTiles;
	}

	public void runBFS(Tile[] startState) {
		if (checkInput()) {
			ArrayList<Tile[]> rsList = new ArrayList<Tile[]>();
			ArrayList<Tile[]> queueList = new ArrayList<Tile[]>();

			rsList.add(startState);
			while (!rsList.isEmpty()) {
				Tile[] tiles = rsList.get(0);
				if (isGoal(tiles))
					return;
				ArrayList<Tile[]> lists = generateSuccessor(tiles);
				for (int i = 0; i < lists.size(); i++)
					queueList.add(lists.get(i));
				for (int i = 0; i < queueList.size(); i++) {
					rsList.add(queueList.get(i));
				}
			}
		}
	}

	private boolean isGoal(Tile[] tiles) {
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i].value == this.goal)
				return true;
		}
		return false;
	}

	private boolean checkInput() {
		if (txtGoal.getText().equals(""))
			return false;
		return true;
	}

	private void printList(ArrayList<Tile[]> lists) {
		for (int i = 0; i < lists.size(); i++) {
			printTile(lists.get(i));
		}
	}

	private void printTile(Tile[] tiles) {
		for (int i = 0; i < tiles.length; i++) {
			System.out.print(tiles[i] + " ");
			if ((i + 1) % 4 == 0)
				System.out.println();
		}
	}
}
