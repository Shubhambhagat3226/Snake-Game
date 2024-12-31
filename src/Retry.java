import Constant.CommonConstant;

import javax.swing.*;
import java.awt.*;

public class Retry extends JDialog {

    private MyFrame source;
    private MyPanel panel;

    public Retry(MyFrame frame, MyPanel panel){
        this.source = frame;
        this.panel = panel;
        setTitle("Message");
        setSize(300,170);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(source);
        setResizable(false);
        setLayout(null);

        addComponent();
    }

    public void addComponent(){
        int score = panel.snakeBody.size();
        panel.highScore = Math.max(panel.highScore, score);

        JLabel scoreLabel = new JLabel("SCORE: " + score);
        scoreLabel.setBounds(0,10,300,40);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel);

        JLabel highScoreLabel = new JLabel("HIGHSCORE: " + panel.highScore);
        highScoreLabel.setBounds(0,40,300,40);
        highScoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(highScoreLabel);

        JButton button = new JButton("Try again");
        button.setBounds(-10,85,300,50);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            panel.gameLoop.start();
            panel.gameOver = false;
            panel.snakeHead.setXY();
            panel.snakeBody.clear();
            CommonConstant.VELOCITY_X = 0;
            CommonConstant.VELOCITY_Y = 0;
            dispose();
        });
        add(button);

    }
}
