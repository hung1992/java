package com.java.sources;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Main extends JFrame {

	private JPanel contentPane;
	private JButton btWater;
	private JButton btPuzzle;
	private JButton btnBlockWorld;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {

		setResizable(false);
		setBackground(new Color(255, 250, 250));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 205);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0,
				255), 3, true), "Select A Problem", TitledBorder.CENTER,
				TitledBorder.TOP, null, Color.RED));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btWater = new JButton("WATER JUG");
		btWater.setBounds(98, 33, 141, 25);
		contentPane.add(btWater);

		btPuzzle = new JButton("PUZZLE");
		btPuzzle.setBounds(98, 77, 141, 25);
		contentPane.add(btPuzzle);

		btnBlockWorld = new JButton("BLOCK WORLD");
		btnBlockWorld.setBounds(98, 120, 141, 25);
		contentPane.add(btnBlockWorld);

		btWater.addActionListener(waterAction);
		btPuzzle.addActionListener(puzzleAction);

	}

	ActionListener waterAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			Water waterUI = new Water();
			waterUI.setVisible(true);
			waterUI.setLocationRelativeTo(null);
		}
	};

	ActionListener puzzleAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			Puzzle puzzle = new Puzzle();
			puzzle.setVisible(true);
			puzzle.setLocationRelativeTo(null);
		}
	};

}
