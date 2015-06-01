/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Frame extends JFrame {
	private static final long serialVersionUID = 7737765546452888635L;
	protected JTextField txtGoal;
	protected Game gamePane;
	protected JRadioButton rbBFS;
	protected JRadioButton rbDFS;
	protected JRadioButton rbHCB;
	protected ButtonGroup rdbGroup;
	protected JPanel topPane;
	protected JPanel downPane;
	protected JPanel contentPane;
	protected JPanel northPane;
	protected JButton btnRun;
	protected JPanel southPane;
	protected JButton btnReset;
	private Thread bfsThread;
	private Thread dfsThread;
	private Thread hcbThread;

	public Frame() {
		inits();
	}

	private void inits() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(350, 440);
		initJRadioButtons();
		initPanelAndButtons();
		addComponents();
		btnRun.addActionListener(btnRunAction);
		btnReset.addActionListener(btnEscAction);
	}

	private void initJRadioButtons() {
		rbDFS = new JRadioButton("DFS");
		rbBFS = new JRadioButton("BFS");
		rbHCB = new JRadioButton("HCB");
		rdbGroup = new ButtonGroup();
		rdbGroup.add(rbBFS);
		rdbGroup.add(rbDFS);
		rdbGroup.add(rbHCB);
	}

	private void initPanelAndButtons() {
		contentPane = (JPanel) getContentPane();
		northPane = new JPanel(new BorderLayout());
		topPane = new JPanel(new GridLayout(0, 5));
		downPane = new JPanel(new GridLayout(0, 5));
		gamePane = new Game();
		gamePane.setSize(350, 415);
		btnRun = new JButton("Run");
		txtGoal = new JTextField();
		southPane = new JPanel();
		southPane.setLayout(new GridLayout(0, 4));
		btnReset = new JButton("ESC");
	}

	private void addComponents() {
		topPane.add(rbBFS);
		topPane.add(rbDFS);
		topPane.add(rbHCB);
		JLabel lbGoal = new JLabel("  Goal : ");
		topPane.add(lbGoal);
		topPane.add(txtGoal);
		downPane.add(new JLabel());
		downPane.add(btnRun);
		downPane.add(new JLabel());
		downPane.add(btnReset);
		northPane.add(topPane, BorderLayout.CENTER);
		northPane.add(downPane, BorderLayout.SOUTH);
		contentPane.add(northPane, BorderLayout.NORTH);
		contentPane.add(gamePane, BorderLayout.CENTER);

	}

	private void runDFS(Cell[] start, int goal) {
		ArrayList<Cell[]> stackList = new ArrayList<Cell[]>();
		DFSRunner r = new DFSRunner(start, goal, stackList, this.gamePane);
		dfsThread = new Thread(r);
		dfsThread.start();
	}

	private void runBFS(Cell[] start, int goal) {
		ArrayList<Cell[]> queueList = new ArrayList<Cell[]>();
		BFSRunner r = new BFSRunner(start, goal, queueList, this.gamePane);
		bfsThread = new Thread(r);
		bfsThread.start();
	}

	private void runHCB(Cell[] start, int goal) {
		ArrayList<Cell[]> list = new ArrayList<Cell[]>();
		HCBRunner r = new HCBRunner(start, goal, list, this.gamePane);
		hcbThread = new Thread(r);
		hcbThread.start();
	}

	private ActionListener btnRunAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			runAlgorithm();
		}
	};

	private ActionListener btnEscAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			btnRun.setEnabled(true);
			txtGoal.setEnabled(true);
			stopThread();
			Game newGame = new Game();
			gamePane.myState = Utils.copy(newGame.myState);
			gamePane.repaint();
		}
	};

	private void runAlgorithm() {
		int goal = getGoal();
		Game newGame = new Game();
		Cell[] start = Utils.copy(newGame.myState);
		if (goal == -1)
			return;
		else {
			if (!Utils.checkGoal(goal)) {
				JOptionPane.showMessageDialog(null,
						"Invalid goal\nThere is not exist n for :\n2^n == "
								+ goal, "Invalid Goal",
						JOptionPane.ERROR_MESSAGE);
			} else if (rbBFS.isSelected()) {
				btnRun.setEnabled(false);
				txtGoal.setEnabled(false);
				runBFS(start, goal);
			} else if (rbDFS.isSelected()) {
				btnRun.setEnabled(false);
				txtGoal.setEnabled(false);
				runDFS(start, goal);
			} else if (rbHCB.isSelected()) {
				btnRun.setEnabled(false);
				txtGoal.setEnabled(false);
				runHCB(start, goal);
			} else {
				JOptionPane.showMessageDialog(null, "Choose Algorithm",
						"Algorithm Not Choose", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	}

	private void stopThread() {
		if (hcbThread != null && hcbThread.isAlive())
			hcbThread.stop();
		if (dfsThread != null && dfsThread.isAlive())
			dfsThread.stop();
		if (bfsThread != null && bfsThread.isAlive())
			bfsThread.stop();
	}

	private int getGoal() {
		String goalString = txtGoal.getText().trim();
		if (goalString.equals("")) {
			JOptionPane.showMessageDialog(null,
					"Goal is not specify\nPlay with default goal 2048",
					"Goal not filled", JOptionPane.INFORMATION_MESSAGE);
			return 2048;
		} else
			return parseGoal(goalString);
	}

	private int parseGoal(String goalString) {
		try {
			int t = Integer.parseInt(goalString);
			return t;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Parse Goal Exception : " + e.getMessage());
			return -1;
		}
	}
}
