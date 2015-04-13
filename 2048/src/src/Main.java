/* ******************************
 * AI - PROGRAMING ASSIGNMENT 1
 * Student : NGUYEN NGOC HUNG
 * ID      :  51001393
 ********************************/

package src;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				Frame frame = new Frame();
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(r);
	}
}
