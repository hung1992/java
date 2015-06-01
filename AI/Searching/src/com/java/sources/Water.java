package com.java.sources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Water extends JFrame {

	private JPanel contentPane;
	private JPanel optionPane;
	private JLabel lbFirstJug;
	private JLabel lblSecondJug;
	private JTextField txtFirst;
	private JTextField txtSecond;
	private JLabel lblGoal;
	private JTextField txtGoal;
	private JLabel lbllit;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblAlgorithm;
	private JComboBox cbAlgorithm;
	private JLabel lblHeuristic;
	private JComboBox comboBox_1;
	private JLabel lblTestCase;
	private JComboBox cbTestCase;
	private JButton btRandom;
	private JButton btEdit;
	private JButton btSolve;
	private JPanel resultPane;
	private static JTextArea textArea;
	private JScrollPane scrollPane;

	private static int X;
	private static int Y;
	private static int goal;
	private static int startFirstJug = 0, startSecondJug = 0;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { try { WaterUI frame = new WaterUI();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * }); }
	 */

	/**
	 * Create the frame.
	 */
	public Water() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 476, 581);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(253, 245, 230));
		contentPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 51,
				255), 3, true), "WATER JUG", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(255, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		optionPane = new JPanel();
		optionPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0,
				255), 2, true), "JUG OPTION", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(255, 0, 0)));
		optionPane.setBackground(new Color(255, 250, 240));
		optionPane.setBounds(12, 24, 450, 185);
		contentPane.add(optionPane);
		optionPane.setLayout(null);

		lbFirstJug = new JLabel("First Jug :");
		lbFirstJug.setFont(new Font("Dialog", Font.BOLD, 13));
		lbFirstJug.setBounds(12, 33, 84, 15);
		optionPane.add(lbFirstJug);

		lblSecondJug = new JLabel("Second Jug : ");
		lblSecondJug.setFont(new Font("Dialog", Font.BOLD, 13));
		lblSecondJug.setBounds(12, 74, 97, 15);
		optionPane.add(lblSecondJug);

		txtFirst = new JTextField();
		txtFirst.setBounds(114, 31, 60, 19);
		optionPane.add(txtFirst);
		txtFirst.setColumns(10);

		txtSecond = new JTextField();
		txtSecond.setColumns(10);
		txtSecond.setBounds(114, 70, 60, 19);
		optionPane.add(txtSecond);

		lblGoal = new JLabel("Goal : ");
		lblGoal.setBounds(12, 119, 70, 15);
		optionPane.add(lblGoal);

		txtGoal = new JTextField();
		txtGoal.setColumns(10);
		txtGoal.setBounds(114, 117, 60, 19);
		optionPane.add(txtGoal);

		lbllit = new JLabel("(lit)");
		lbllit.setBounds(178, 33, 37, 15);
		optionPane.add(lbllit);

		label = new JLabel("(lit)");
		label.setBounds(178, 70, 37, 15);
		optionPane.add(label);

		label_1 = new JLabel("(lit)");
		label_1.setBounds(178, 119, 37, 15);
		optionPane.add(label_1);

		lblAlgorithm = new JLabel("Algorithm : ");
		lblAlgorithm.setBounds(240, 31, 91, 15);
		optionPane.add(lblAlgorithm);

		cbAlgorithm = new JComboBox();
		cbAlgorithm.setModel(new DefaultComboBoxModel(new String[] { "DFS",
				"BFS", "Hill Climbing" }));
		cbAlgorithm.setSelectedIndex(0);
		cbAlgorithm.setBounds(324, 24, 114, 24);
		optionPane.add(cbAlgorithm);

		lblHeuristic = new JLabel("Heuristic : ");
		lblHeuristic.setBounds(240, 74, 76, 15);
		optionPane.add(lblHeuristic);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(
				new String[] { "incomplete!" }));
		comboBox_1.setBounds(324, 72, 114, 19);
		optionPane.add(comboBox_1);

		lblTestCase = new JLabel("Test Case :");
		lblTestCase.setBounds(240, 119, 91, 15);
		optionPane.add(lblTestCase);

		cbTestCase = new JComboBox();
		cbTestCase.setModel(new DefaultComboBoxModel(new String[] {
				"--choose--", "case 1", "case 2", "case 3", "case 4", "case 5",
				"case 6", "case 7", "case 8", "case 9", "case 10" }));
		cbTestCase.setBounds(334, 114, 104, 24);
		optionPane.add(cbTestCase);

		btRandom = new JButton("Random");
		btRandom.setFont(new Font("Dialog", Font.BOLD, 10));
		btRandom.setBounds(12, 148, 84, 25);
		optionPane.add(btRandom);

		btEdit = new JButton("Edit");
		btEdit.setBounds(104, 148, 70, 25);
		optionPane.add(btEdit);

		btSolve = new JButton("SOLVE");
		btSolve.setBounds(219, 147, 97, 25);
		optionPane.add(btSolve);

		resultPane = new JPanel();
		resultPane.setLayout(new BorderLayout());
		resultPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0,
				255), 2, true), "RESULT", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.RED));
		resultPane.setBackground(new Color(255, 250, 240));
		resultPane.setBounds(12, 221, 450, 312);

		textArea = new JTextArea();
		textArea.setFont(new Font("Dialog", Font.BOLD, 18));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		resultPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(resultPane);

		txtFirst.setEditable(false);
		txtSecond.setEditable(false);
		txtGoal.setEditable(false);

		// register actionListener for button
		btSolve.addActionListener(solveAction);
		btEdit.addActionListener(editAction);
		btRandom.addActionListener(randomAction);
		cbTestCase.addActionListener(testAction);

	}

	// define action listener for button SOLVE
	ActionListener solveAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			textArea.setText("");

			// check whether input is valid

			if (txtFirst.getText().equals("") || txtSecond.getText().equals("")
					|| txtGoal.getText().equals("")
					|| cbTestCase.getSelectedIndex() == -1) {
				JOptionPane
						.showMessageDialog(
								null,
								"Please insert value by choose a test case in "
										+ "the ComboBox \n "
										+ "or make it random by click button Random \n "
										+ "or click button Edit to insert your favorite values",
								"Null Input", JOptionPane.ERROR_MESSAGE, null);
				return;
			} else {

				try {
					X = Integer.parseInt(txtFirst.getText().trim());
					Y = Integer.parseInt(txtSecond.getText().trim());
					goal = Integer.parseInt(txtGoal.getText().trim());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null,
							"Input Must Be Integer Number",
							"Number Format Exception",
							JOptionPane.ERROR_MESSAGE, null);
					return;
				}

				// disable input textField
				txtFirst.setEditable(false);
				txtSecond.setEditable(false);
				txtGoal.setEditable(false);

				int ucln = UCLN(X, Y);
				if (goal % ucln != 0 || goal > X && goal > Y) {

					textArea.append("CANNOT FIND SOLUTION !!!");
					return;

				} else {

					ArrayList<State> arrayList = new ArrayList<>();
					State theState = new State(startFirstJug, startSecondJug);
					arrayList.add(theState);

					String algorithm = (String) cbAlgorithm.getSelectedItem();
					if (algorithm.equals("DFS")) {

						arrayList = DFS(arrayList);
						int steps = arrayList.size();
						textArea.append("      DFS : (" + steps + " steps)\n");
						textArea.append("------------------------ \n");
						disPlayResult(arrayList);
						textArea.append("------------------------\n");

					} else if (algorithm.equals("BFS")) {

						arrayList = BFS(arrayList);
						int steps = arrayList.size();
						textArea.append("      BFS : (" + steps + " steps)\n");
						textArea.append("--------------------------------------- \n");
						disPlayResult(arrayList);
						textArea.append("--------------------------------------- \n");
					}

					else {
						return;
					}

				}
				textArea.setCaretPosition(0);
			}
		} // end action perform
	};

	private static int UCLN(int a, int b) {

		while (a != b) {
			if (a > b)
				a = a - b;
			else
				b = b - a;
		}
		return a;
	}

	// define action listener for button EDIT
	ActionListener editAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			txtFirst.setEditable(true);
			txtSecond.setEditable(true);
			txtGoal.setEditable(true);

		}
	};

	// define action listener for button RANDOM
	ActionListener randomAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			Random r = new Random();
			int num1;
			do {
				num1 = r.nextInt(200);
			} while (num1 == 0);
			int num2;
			do {
				num2 = r.nextInt(200);
			} while (num2 == num1 || num2 == 0);

			int MAX = max(num1, num2);
			int goalNum;
			do {
				goalNum = r.nextInt(200);
			} while (goalNum > MAX);

			txtFirst.setText(String.valueOf(num1));
			txtSecond.setText(String.valueOf(num2));
			txtGoal.setText(String.valueOf(goalNum));

		}

		private int max(int a, int b) {

			if (a >= b)
				return a;
			else
				return b;
		}
	};

	// define actionListener for ComboBox testcase
	ActionListener testAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			String testCase = (String) cbTestCase.getSelectedItem();
			switch (testCase) {
			case "case 1":

				txtFirst.setText("3");
				txtSecond.setText("4");
				txtGoal.setText("2");
				break;

			case "case 2":

				txtFirst.setText("5");
				txtSecond.setText("7");
				txtGoal.setText("4");
				break;

			case "case 3":

				txtFirst.setText("8");
				txtSecond.setText("12");
				txtGoal.setText("4");
				break;

			case "case 4":

				txtFirst.setText("12");
				txtSecond.setText("36");
				txtGoal.setText("24");

				break;

			case "case 5":

				txtFirst.setText("100");
				txtSecond.setText("25");
				txtGoal.setText("30");
				break;

			case "case 6":

				txtFirst.setText("20");
				txtSecond.setText("25");
				txtGoal.setText("15");
				break;

			case "case 7":

				txtFirst.setText("14");
				txtSecond.setText("18");
				txtGoal.setText("8");
				break;

			case "case 8":

				txtFirst.setText("11");
				txtSecond.setText("22");
				txtGoal.setText("11");
				break;

			case "case 9":

				txtFirst.setText("9");
				txtSecond.setText("12");
				txtGoal.setText("6");
				break;

			case "case 10":

				txtFirst.setText("16");
				txtSecond.setText("24");
				txtGoal.setText("5");
				break;

			default:
				break;
			}
		}
	};

	// add nesscessary code for solving BFS DFS
	static class State {

		int first;
		int second;

		public State(int x, int y) {

			this.first = x;
			this.second = y;
		}
	}

	private static boolean isNext(int x, int y) {

		if (x == goal || y == goal || !checkConds(x, y))
			return false;
		if (x < X)
			return true;
		else if (y < Y)
			return true;
		else if (x > 0)
			return true;
		else if (y > 0)
			return true;
		else if (y > 0 && x + y >= X)
			return true;
		else if (x > 0 && x + y >= Y)
			return true;
		else if (y > 0 && x + y <= X)
			return true;
		else if (x > 0 && y + x <= Y)
			return true;
		return false;
	}

	private static boolean checkConds(int x, int y) {

		if (0 <= x && x <= X && 0 <= y && y <= Y)
			return true;
		else
			return false;
	}

	private static ArrayList<State> DFS(ArrayList<State> list) {

		int currentSize = list.size();
		State currentState = list.get(currentSize - 1);
		int x = currentState.first;
		int y = currentState.second;
		if (!isNext(x, y)) {
			if (currentState.first == goal || currentState.second == goal)
				return list;
			else {
				list.remove(currentSize - 1);
				return list;
			}
		} else {
			if (x < X) {
				State nextState = new State(X, y);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && y < Y) {
				State nextState = new State(x, Y);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && x > 0) {
				State nextState = new State(0, y);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && y > 0) {
				State nextState = new State(x, 0);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && y > 0 && x + y >= X) {
				State nextState = new State(X, y - (X - x));
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && x > 0 && x + y >= Y) {
				State nextState = new State(x - (Y - y), Y);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && y > 0 && x + y <= X) {
				State nextState = new State(x + y, 0);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
			if (list.size() == currentSize && x > 0 && y + x <= Y) {
				State nextState = new State(0, x + y);
				if (!isHave(nextState, list)) {
					list.add(nextState);
					list = DFS(list);
				}
			}
		}
		return list;
	}

	private static boolean isHave(State x, ArrayList<State> list) {

		for (int i = 0; i < list.size(); i++) {
			if (x.first == list.get(i).first && x.second == list.get(i).second)
				return true;
		}
		return false;
	}

	private static void disPlayResult(ArrayList<State> result) {

		for (State state : result) {
			textArea.append("Jug1 : " + state.first + "  , Jug2 : "
					+ state.second + "\n");
		}
	}

	// end of DFS

	// BFS CODE

	static class StateTree {

		State next, previous;

		public StateTree(State child, State parent) {

			this.next = child;
			this.previous = parent;
		}

	}

	private static ArrayList<State> BFS(ArrayList<State> arrList) {

		ArrayList<StateTree> return_list = new ArrayList<StateTree>();
		ArrayList<StateTree> list_2 = new ArrayList<StateTree>();
		State curState = arrList.get(0);
		int x, y;
		StateTree child = new StateTree(curState, curState);
		list_2.add(child);

		while (!list_2.isEmpty()) {
			curState = list_2.get(0).next;
			x = curState.first;
			y = curState.second;
			return_list.add(list_2.get(0));
			list_2.remove(0);
			if (!isNext(x, y)) {
				if (x == goal || y == goal) {
					list_2.clear();
				}
			} else {
				if (x < X) {
					State nextState = new State(X, y);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (y < Y) {
					State nextState = new State(x, Y);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (x > 0) {
					State nextState = new State(0, y);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (y > 0) {
					State nextState = new State(x, 0);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (y > 0 && x + y >= X) {
					State nextState = new State(X, y - (X - x));
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (x > 0 && x + y >= Y) {
					State nextState = new State(x - (Y - y), Y);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (y > 0 && x + y <= X) {
					State nextState = new State(x + y, 0);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
				if (x > 0 && y + x <= Y) {
					State nextState = new State(0, x + y);
					if (!Visited(nextState, return_list)) {
						child = new StateTree(nextState, curState);
						list_2.add(child);
					}
				}
			}
		}

		return viewBFS(return_list);
	}

	private static ArrayList<State> viewBFS(ArrayList<StateTree> theState) {

		ArrayList<State> result = new ArrayList<State>();
		State child, parent = theState.get(theState.size() - 1).previous;
		result.add(theState.get(theState.size() - 1).next);
		for (int i = theState.size() - 1; i >= 0; i--) {
			child = theState.get(i).next;
			if (parent.first == startFirstJug
					&& parent.second == startSecondJug) {
				result.add(0, parent);
				break;
			}
			if (child.first == parent.first && child.second == parent.second) {
				result.add(0, child);
				parent = theState.get(i).previous;
			}
		}
		return result;
	}

	private static boolean Visited(State nextState, ArrayList<StateTree> theList) {

		for (int i = 0; i < theList.size(); i++) {
			if (nextState.first == theList.get(i).next.first
					&& nextState.second == theList.get(i).next.second)
				return true;
		}
		return false;
	}

}
