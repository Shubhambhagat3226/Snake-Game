import Constant.CommonConstant;
import javax.swing.*;
public class MyFrame extends JFrame {
    public MyFrame(){
        super("Snake Game");
        setSize(CommonConstant.FRAME_WIDTH, CommonConstant.FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        MyPanel myPanel = new MyPanel(this);
        myPanel.setFocusable(true);
        add(myPanel);
        pack();
    }
}