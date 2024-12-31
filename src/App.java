import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyFrame myFrame = new MyFrame();
                myFrame.setVisible(true);
            }
        });
    }
}
