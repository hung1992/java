package com.java.sources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class Puzzle extends JFrame {

	// field declaration
	private JButton buttons[] = new JButton[9]; // button represents init state
	private JPanel panelNode = new JPanel(new GridLayout(3, 3));
	private static String startState = ""; // state string
	private JComboBox cbCase = new JComboBox(); // save test case
	private JTextArea textArea = new JTextArea();// output textArea
	private JPanel contentPane;
	private boolean flag = false;
	private Thread t;

	class EightPuzzle {

		// Use of Queue Implemented using LinkedList for Storing All the Nodes
		Queue<String> agenda = new LinkedList<String>();

		// HashMap isused to ignore repeated nodes
		Map<String, Integer> stateDepth = new HashMap<String, Integer>();
		// relates each position to its predecessor
		Map<String, String> stateHistory = new HashMap<String, String>();

		// Add method to add the new string to the Map and Queue
		void add(String newState, String oldState) {

			if (!stateDepth.containsKey(newState)) {
				int newValue = oldState == null ? 0
						: stateDepth.get(oldState) + 1;
				stateDepth.put(newState, newValue);
				agenda.add(newState);
				stateHistory.put(newState, oldState);
			}
		}

		/*
		 * Each of the Methods below Takes the Current State of Board as String.
		 * Then the operation to move the blank space is done if possible. After
		 * that the new string is added to the map and queue. If it is the Goal
		 * State then the Program Terminates.
		 */

		void up(String currentState) {

			int a = currentState.indexOf("0");
			if (a > 2) {
				String nextState = currentState.substring(0, a - 3) + "0"
						+ currentState.substring(a - 2, a)
						+ currentState.charAt(a - 3)
						+ currentState.substring(a + 1);
				checkCompletion(currentState, nextState);
			}

		}

		void down(String currentState) {

			int a = currentState.indexOf("0");
			if (a < 6) {
				String nextState = currentState.substring(0, a)
						+ currentState.substring(a + 3, a + 4)
						+ currentState.substring(a + 1, a + 3) + "0"
						+ currentState.substring(a + 4);
				checkCompletion(currentState, nextState);
			}
		}

		void left(String currentState) {

			int a = currentState.indexOf("0");
			if (a != 0 && a != 3 && a != 6) {
				String nextState = currentState.substring(0, a - 1) + "0"
						+ currentState.charAt(a - 1)
						+ currentState.substring(a + 1);
				checkCompletion(currentState, nextState);
			}
		}

		void right(String currentState) {

			int a = currentState.indexOf("0");
			if (a != 2 && a != 5 && a != 8) {
				String nextState = currentState.substring(0, a)
						+ currentState.charAt(a + 1) + "0"
						+ currentState.substring(a + 2);
				checkCompletion(currentState, nextState);
			}
		}

		private void checkCompletion(String oldState, String newState) {

			add(newState, oldState);
			if (newState.equals("123456780")) {
				/*
				 * System.out.println("Solution Exists After " +
				 * stateDepth.get(newState) + " Steps");
				 */

				textArea.append("Solution Exists After "
						+ stateDepth.get(newState) + " Steps \n");

				String traceState = newState;

				/*
				 * while (traceState != null) { System.out.println(traceState +
				 * " --Step " + stateDepth.get(traceState)); traceState =
				 * stateHistory.get(traceState); }
				 */

				ArrayList<String> list = new ArrayList<>();

				while (traceState != null) {
					String str = "--Step " + stateDepth.get(traceState) + "\n"
							+ setString(traceState);
					list.add(str);

					/*
					 * System.out.println(" --Step " +
					 * stateDepth.get(traceState) + "\n" + str);
					 */

					traceState = stateHistory.get(traceState);
				}
				for (int i = list.size() - 1; i >= 0; i--) {
					// System.out.println(list.get(i));
					textArea.append(list.get(i) + "\n");
				}
				textArea.append("--------------END------------------");
				textArea.setCaretPosition(0);
				t.stop();
			}

		}

		// format string like 3x3 array for easier looking .
		public String setString(String str) {

			String strcp = str;

			char[] arr = str.toCharArray();
			String newStr = "";
			for (int i = 0; i < arr.length; i++) {
				if (i == 2 || i == 5) {
					newStr = newStr + arr[i];
					newStr = newStr + "\n";
				} else {
					newStr = newStr + arr[i];
				}

			}
			return newStr;
		}

	}

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { try { PuzzleUI frame = new PuzzleUI();
	 * frame.setVisible(true); frame.setLocationRelativeTo(null);
	 * frame.setResizable(false); } catch (Exception e) { e.printStackTrace(); }
	 * } }); }
	 */

	/**
	 * Create the frame.
	 */

	// create components
	public Puzzle() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 486, 582);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 255, 0),
				3, true), "Puzzle", TitledBorder.CENTER, TitledBorder.TOP,
				null, new Color(128, 0, 0)));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		panelNode.setBounds(12, 12, 153, 123);
		panel.add(panelNode);

		JPanel panelConfig = new JPanel();
		panelConfig.setBounds(177, 12, 281, 164);
		panel.add(panelConfig);

		TitledBorder border = new TitledBorder(null, "PUZZLE CONFIGURATION",
				TitledBorder.TOP, TitledBorder.CENTER, new Font("San-Serif",
						Font.BOLD, 13), Color.RED);
		panelConfig.setBorder(new TitledBorder(new LineBorder(new Color(0, 191,
				255), 3, true), "PUZZLE OPTION", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(255, 0, 0)));
		panelConfig.setLayout(null);

		JLabel lblAlgorithmType = new JLabel("Algorithm Type :");
		lblAlgorithmType.setBounds(12, 28, 115, 15);
		panelConfig.add(lblAlgorithmType);

		JComboBox cbAlgorithm = new JComboBox();
		cbAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "DFS",
				"BFS", "HillClimbing" }));
		cbAlgorithm.setBounds(145, 23, 124, 24);
		panelConfig.add(cbAlgorithm);

		JLabel lblHeuristic = new JLabel("Heuristic Function :");
		lblHeuristic.setFont(new Font("Dialog", Font.BOLD, 10));
		lblHeuristic.setBounds(12, 69, 115, 15);
		panelConfig.add(lblHeuristic);

		JComboBox cbHeuristic = new JComboBox();
		cbHeuristic.setFont(new Font("Dialog", Font.BOLD, 9));
		cbHeuristic.setModel(new DefaultComboBoxModel(
				new String[] { "incomplete!!" }));
		cbHeuristic.setBounds(145, 64, 124, 24);
		panelConfig.add(cbHeuristic);

		JLabel lblInputCase = new JLabel("Input Case :");
		lblInputCase.setBounds(12, 108, 94, 15);
		panelConfig.add(lblInputCase);

		cbCase.setModel(new DefaultComboBoxModel(new String[] { "---chose---",
				"case 1", "case 2", "case 3", "case 4", "case 5", "case 6",
				"case 7", "case 8", "case 9", "case 10" }));
		cbCase.setSelectedIndex(0);
		cbCase.setBounds(145, 103, 124, 24);
		panelConfig.add(cbCase);

		border = new TitledBorder("");
		panelNode
				.setBorder(new MatteBorder(3, 3, 3, 3, new Color(0, 191, 255)));

		for (int i = 0; i < 8; i++) {
			JButton btn = new JButton(Integer.toString(i + 1));
			buttons[i] = btn;
			panelNode.add(btn, i);
		}

		JButton blank = new JButton();
		// buttons[9] = blank;
		panelNode.add(blank);
		blank.setBackground(Color.WHITE);

		JButton btnRandom = new JButton("Random");
		btnRandom.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandom.setBounds(12, 147, 74, 25);
		panel.add(btnRandom);

		JButton btnSolve = new JButton("Solve");
		btnSolve.setFont(new Font("Dialog", Font.BOLD, 9));
		btnSolve.setBounds(104, 147, 61, 25);
		panel.add(btnSolve);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());
		panel_1.setBorder(new TitledBorder(new LineBorder(
				new Color(0, 191, 255), 3, true), "RESULT",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 0,
						0)));
		panel_1.setBounds(12, 182, 446, 349);
		panel.add(panel_1);

		// view results
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("San-Serif", Font.BOLD, 20));
		JScrollPane pane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_1.add(pane, BorderLayout.CENTER);

		btnSolve.addActionListener(btnSolveActionListener);
		cbCase.addActionListener(cbCaseActionListener);
		btnRandom.addActionListener(btnRandomActionListener);
	}

	// define action for button solve
	ActionListener btnSolveActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {

			textArea.setText("");
			if (cbCase.getSelectedItem().equals("case 10")) {
				textArea.append("CANNOT FIND SOLUTION");
			}

			else if (cbCase.getSelectedIndex() == 0 & startState.equals("")) {
				JOptionPane
						.showMessageDialog(
								null,
								"Please choose a test case or make a random state \n by press button Random",
								"Condiction Missing",
								JOptionPane.WARNING_MESSAGE, null);
			} else {
				// start a new thread for solving the problem
				t = new Thread(new Runnable() {

					@Override
					public void run() {

						textArea.setText("");
						if (cbCase.getSelectedItem().equals("case 10")) {
							// textArea.append("CANNOT FIND SOLUTION ON THIS TEST CASE");
						} else {

							EightPuzzle e = new EightPuzzle();
							e.add(startState, null); // Add the Initial State

							while (!e.agenda.isEmpty()) {
								String currentState = e.agenda.remove();
								// Move the blank space up and add new state to
								// queue
								e.up(currentState);

								// Move the blank space down
								e.down(currentState);

								// Move left
								e.left(currentState);

								// Move right and remove the current node from
								// Queue
								e.right(currentState);
							}

						}
					}
				});
				t.start();

			}
		}
	};

	// define action for comboBox Case :

	public void fillNodePanel(int[] array) {

		panelNode.removeAll();
		panelNode.setLayout(new GridLayout(3, 3));

		JButton btn = null;
		for (int i = 0; i < 9; i++) {
			if (array[i] == 0) {
				btn = new JButton();
				btn.setBackground(Color.WHITE);
			} else {
				btn = new JButton(Integer.toString(array[i]));
			}

			panelNode.add(btn, i);
		}
		panelNode.revalidate();
		panelNode.repaint();
	}

	ActionListener cbCaseActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			String str = String.valueOf(cbCase.getSelectedItem());
			switch (str) {
			case "case 1":
				int array1[] = { 0, 4, 7, 8, 6, 5, 1, 2, 3 };
				fillNodePanel(array1);
				startState = "047865123";
				break;

			case "case 2":
				int array2[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
				fillNodePanel(array2);
				startState = "012345678";
				break;

			case "case 3":
				int array3[] = { 8, 7, 0, 1, 5, 6, 4, 3, 2 };
				fillNodePanel(array3);
				startState = "870156432";
				break;
			case "case 4":
				int array4[] = { 0, 7, 8, 4, 6, 5, 1, 2, 3 };
				fillNodePanel(array4);
				startState = "078465123";
				break;

			case "case 5":
				int array5[] = { 0, 7, 4, 3, 6, 5, 1, 2, 8 };
				fillNodePanel(array5);
				startState = "074365128";
				break;

			case "case 6":
				int array6[] = { 1, 2, 0, 3, 4, 5, 6, 7, 8 };
				fillNodePanel(array6);
				startState = "120345678";
				break;

			case "case 7":
				int array7[] = { 1, 0, 3, 8, 2, 5, 6, 4, 7 };
				fillNodePanel(array7);
				startState = "103825647";
				break;

			case "case 8":
				int array8[] = { 1, 3, 0, 8, 5, 2, 6, 7, 4 };
				fillNodePanel(array8);
				startState = "130852674";
				break;

			case "case 9":
				int array9[] = { 3, 1, 0, 8, 2, 5, 7, 4, 6 };
				startState = "310825746";
				fillNodePanel(array9);
				break;

			case "case 10":
				int array10[] = { 1, 2, 3, 4, 5, 6, 8, 7, 0 }; // this state is
																// no solutions
				startState = "123456870";
				fillNodePanel(array10);
				break;

			default:
				break;
			}
		}
	}; // end ComboBox action

	// define ActionListener for button Random
	ActionListener btnRandomActionListener = new ActionListener() {

		int[] array = new int[9];
		int zero_Pos;
		Random r = new Random();

		@Override
		public void actionPerformed(ActionEvent e) {

			// generate blank button position
			zero_Pos = r.nextInt(9);
			array[zero_Pos] = 0;
			int num = 0;

			for (int i = 0; i < 9; i++) {
				if (i == zero_Pos)
					continue;
				else {
					do {
						num = r.nextInt(9);
					} while (num == 0 || check(i, num) == false);
					array[i] = num;
				}
			}

			fillNodePanel(array);
			String str = "";
			for (int i = 0; i < 9; i++) {
				str = str + Integer.toString(array[i]);
			}
			startState = str;
		}

		private boolean check(int i, int number) {

			for (int j = 0; j < i; j++) {
				if (array[j] == number)
					return false;
			}
			return true;
		}

	};

}
