import Constant.CommonConstant;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MyPanel extends JPanel implements ActionListener, KeyListener {

    private MyFrame frame;

     class Tile{
        private int x;
        private int y;

        public void setXY(){
            x = y = 5;
        }

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x * CommonConstant.TILE_SIZE;
        }

        public int getY() {
            return y * CommonConstant.TILE_SIZE;
        }
    }

    //snake
    Tile snakeHead;
    //snake body
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //sound
    Clip clip;
    String foodEatingSound, gameOverSound;

    //game loop
    Timer gameLoop;

    //game over
    boolean gameOver;

    // high score
    int highScore;

    public MyPanel(MyFrame frame){
        this.frame = frame;
        setPreferredSize(new Dimension(CommonConstant.FRAME_WIDTH, CommonConstant.FRAME_HEIGHT));
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true);

        //snake head
        snakeHead = new Tile(5,5);
        food = new Tile(10,10);
        random = new Random();
        placeFood();

        //game Loop
        gameLoop = new Timer(150, this);
        gameLoop.start();

        //snakeBody
        snakeBody = new ArrayList<>();

        //sounds
        foodEatingSound = "src/sound/foodSound.wav";
        gameOverSound = "src/sound//gameOver.wav";
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void grid(Graphics g) {
        //grid
        for (int i = 0; i < CommonConstant.FRAME_HEIGHT / CommonConstant.TILE_SIZE; i++) {
            g.drawLine(i*CommonConstant.TILE_SIZE, 0
                    , i*CommonConstant.TILE_SIZE
                    , CommonConstant.FRAME_HEIGHT);
            g.drawLine(0, i*CommonConstant.TILE_SIZE
                    ,CommonConstant.FRAME_WIDTH
                    , i*CommonConstant.TILE_SIZE);
        }

    }
    public void draw(Graphics g){

        //food
        g.setColor(Color.RED);
        g.fill3DRect(food.getX(), food.getY()
                , CommonConstant.TILE_SIZE
                , CommonConstant.TILE_SIZE, true);



        //snake
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.getX(), snakeHead.getY()
                , CommonConstant.TILE_SIZE
                , CommonConstant.TILE_SIZE,true);
        
        //snake body
        g.setColor(Color.GREEN);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.getX(), snakePart.getY()
                    , CommonConstant.TILE_SIZE
                    , CommonConstant.TILE_SIZE,true);
        }

        // score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over : " + snakeBody.size()
               , CommonConstant.TILE_SIZE-16
               , CommonConstant.TILE_SIZE);
        } else {
            g.drawString("Score : " + snakeBody.size()
                , CommonConstant.TILE_SIZE-16
                , CommonConstant.TILE_SIZE);
        }
    }

    void placeFood(){
        food.x = random.nextInt(CommonConstant.FRAME_WIDTH/CommonConstant.TILE_SIZE);
        food.y = random.nextInt(CommonConstant.FRAME_HEIGHT/CommonConstant.TILE_SIZE);
    }

    boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void setSound(String soundFileName){

        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);

            //sound play
            clip.setFramePosition(10);
            clip.start();

        } catch (Exception e){
            e.getStackTrace();
        }
    }

    void move(){
        //food eat
        if (collision(snakeHead, food)){
            setSound(foodEatingSound);
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snake body
        for (int i = snakeBody.size()-1; i >= 0 ; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile preSnakePart = snakeBody.get(i-1);
                snakePart.x = preSnakePart.x;
                snakePart.y = preSnakePart.y;
            }
        }


        //snake
        snakeHead.x += CommonConstant.VELOCITY_X;
        snakeHead.y += CommonConstant.VELOCITY_Y;

//        to pass to gap
//
//        if (snakeHead.getX()>CommonConstant.FRAME_WIDTH){
//            snakeHead.x = 0;
//        }
//        if (snakeHead.x < 0){
//            snakeHead.x = CommonConstant.FRAME_WIDTH/CommonConstant.TILE_SIZE-1;
//        }
//        if (snakeHead.getY()>CommonConstant.FRAME_HEIGHT){
//            snakeHead.y = 0;
//        }
//        if (snakeHead.y == -1){
//            snakeHead.y = CommonConstant.FRAME_HEIGHT/CommonConstant.TILE_SIZE-1;
//        }

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead,snakePart)){
                gameOver = true;
            }
        }

        if (snakeHead.x < 0 || snakeHead.x > CommonConstant.FRAME_WIDTH/CommonConstant.TILE_SIZE-1
                || snakeHead.y < 0 || snakeHead.y > CommonConstant.FRAME_HEIGHT/CommonConstant.TILE_SIZE-1){
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver){
            setSound(gameOverSound);
            gameLoop.stop();
            new Retry(frame, this).setVisible(true);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP : CommonConstant.VELOCITY_X = 0;
                 CommonConstant.VELOCITY_Y = -1;
                 break;
            case KeyEvent.VK_DOWN : CommonConstant.VELOCITY_X = 0;
                CommonConstant.VELOCITY_Y = 1;
                break;
            case KeyEvent.VK_LEFT : CommonConstant.VELOCITY_X =- 1;
                CommonConstant.VELOCITY_Y = 0;
                break;
            case KeyEvent.VK_RIGHT : CommonConstant.VELOCITY_X = 1;
                CommonConstant.VELOCITY_Y = 0;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
