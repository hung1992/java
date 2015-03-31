package sources;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        Runnable runner = new Runnable() {

            @Override
            public void run() {
                Frame frame = new Frame();
                frame.setTitle("AI ASSIGNMENT");
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }
}
