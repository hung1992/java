
package design;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                Frame frame = new Frame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        };
          EventQueue.invokeLater(r);
    }
}
