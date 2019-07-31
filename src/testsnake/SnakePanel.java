/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Aidan
 */
public class SnakePanel extends JPanel implements ActionListener{
    
    private final int PANEL_WIDTH = 1200;
    private final int PANEL_HEIGHT = 800;
    
    private int snakeSize;
    private int snakeLength;
    private List<int[]> snakeSegments;
    private int[] snakeLoc;
    private int[] snakeVelocity;
    
    private JLabel snakePanelText;
    private GridBagConstraints middleLabel;
    
    private Image snakeHeadImageLeft;
    private Image snakeHeadImageRight;
    private Image snakeHeadImageUp;
    private Image snakeHeadImageDown;
    private Image snakeBodyImage;
    
    private int[] appleLoc;
    private Image appleImage;
    
    private int gameState;
    private int score;
    
    private boolean allowInput;
    private boolean firstMove;
    
    private Timer timer;
    private final int DELAY = 140;
    
    public SnakePanel(){
        this.initSnakePanel();
    }
    
    /**
     * Adds KeyListener to panel using custom TAdapter class
     * Sets panel size, colour and dimensions
     */
    private void initSnakePanel(){
        this.addKeyListener(new TAdapter());
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(new GridBagLayout());
        middleLabel = new GridBagConstraints();
        middleLabel.gridx = GridBagConstraints.CENTER;
        middleLabel.gridy = GridBagConstraints.CENTER;
        snakePanelText = new JLabel("Press space to play again");
        snakePanelText.setFont(new Font("Serif", Font.PLAIN, 60));
        snakePanelText.setForeground(Color.red);
        this.add(snakePanelText, middleLabel); 
        snakeSize = 40;
        this.loadImages();
        this.initGame();
    }
    
    /**
     * Loads all images used on this Panel
     */   
    private void loadImages(){
        ImageIcon snakeHeadIconLeft = new ImageIcon(new ImageIcon(getClass().getResource("snakeHeadLeft.png")).getImage().getScaledInstance(this.snakeSize, this.snakeSize, Image.SCALE_SMOOTH));
        snakeHeadImageLeft = snakeHeadIconLeft.getImage();
        ImageIcon snakeHeadIconRight = new ImageIcon(new ImageIcon(getClass().getResource("snakeHeadRight.png")).getImage().getScaledInstance(this.snakeSize, this.snakeSize, Image.SCALE_SMOOTH));
        snakeHeadImageRight = snakeHeadIconRight.getImage();
        ImageIcon snakeHeadIconUp = new ImageIcon(new ImageIcon(getClass().getResource("snakeHeadUp.png")).getImage().getScaledInstance(this.snakeSize, this.snakeSize, Image.SCALE_SMOOTH));
        snakeHeadImageUp = snakeHeadIconUp.getImage();
        ImageIcon snakeHeadIconDown = new ImageIcon(new ImageIcon(getClass().getResource("snakeHeadDown.png")).getImage().getScaledInstance(this.snakeSize, this.snakeSize, Image.SCALE_SMOOTH));
        snakeHeadImageDown = snakeHeadIconDown.getImage();
        ImageIcon snakeBodyIcon = new ImageIcon(new ImageIcon(getClass().getResource("snakeBody.png")).getImage().getScaledInstance(this.snakeSize, this.snakeSize, Image.SCALE_SMOOTH));
        snakeBodyImage = snakeBodyIcon.getImage();
        
        ImageIcon appleImageIcon = new ImageIcon(new ImageIcon(getClass().getResource("apple.png")).getImage().getScaledInstance(this.snakeSize, this.snakeSize, Image.SCALE_SMOOTH));
        appleImage = appleImageIcon.getImage();
    }
    
    /**
     * initiates snake length and adds that number of int[]'s to the snakeSegments
     * variable.
     * The last index will be the snake head
     * Randomizes an apple on the panel
     * Starts a timer to loop the doDrawing and actionPerformed methods
     */
    public void initGame(){
        timer = new Timer(DELAY, this);
        timer.start();
        snakePanelText.setVisible(false);
        score = 0;
        SnakeWindow.titlePanel.setScoreLabelText(score);
        snakeSegments = new ArrayList<>();
        appleLoc = new int[]{0,0};
        snakeLoc = new int[]{PANEL_WIDTH/2, PANEL_HEIGHT/2};
        snakeVelocity = new int[]{0, 0};
        snakeLength = 4;
        gameState = 1;
        allowInput = true;
        firstMove = true;
        for (int i = 0; i < snakeLength - 1; i++){
            snakeSegments.add(new int[]{snakeLoc[0]-snakeSize, snakeLoc[1]});
        }
        snakeSegments.add(new int[]{snakeLoc[0], snakeLoc[1]});
        changeAppleLoc();
        
    }
    
    /**
     * Called by the paintComponent method which is called automatically at intervals specified by timer variable
     * if gameState = 1 (playing) it draws the apple image, then each of the body segments,
     * then the snake head.
     * @param g 
     */
    private void doDrawing(Graphics g){
        if (gameState == 1){
            g.drawImage(appleImage, appleLoc[0], appleLoc[1], this);
            for(int i = 0; i < snakeSegments.size() - 1; i++){
                g.drawImage(snakeBodyImage, snakeSegments.get(i)[0], snakeSegments.get(i)[1], this);
            }
            if(snakeVelocity[1] == 0){
                if(snakeVelocity[0] < 0){
                    g.drawImage(snakeHeadImageLeft, snakeSegments.get(snakeSegments.size() - 1)[0], snakeSegments.get(snakeSegments.size() - 1)[1], this);
                }
                else{
                    g.drawImage(snakeHeadImageRight, snakeSegments.get(snakeSegments.size() - 1)[0], snakeSegments.get(snakeSegments.size() - 1)[1], this);
                }
            }
            else{
                if(snakeVelocity[1] < 0){
                    g.drawImage(snakeHeadImageUp, snakeSegments.get(snakeSegments.size() - 1)[0], snakeSegments.get(snakeSegments.size() - 1)[1], this);
                }
                else{
                    g.drawImage(snakeHeadImageDown, snakeSegments.get(snakeSegments.size() - 1)[0], snakeSegments.get(snakeSegments.size() - 1)[1], this);
                }
            }
        }
        else if(gameState == 0){
            //draw dead snake under the snakePanelLabel?
        }
    }
    
    /**
     * Randomises an apple on the panel
     * Creates a number divisible by 20 within the range of 0 - PANEL_WIDTH and 0 - PANEL_HEIGHT
     */
    private void changeAppleLoc(){
        int randNum = (int)Math.floor(Math.random() * (((PANEL_WIDTH - snakeSize) / snakeSize) + 1));
        appleLoc[0] = randNum * snakeSize;
        randNum = (int)Math.floor(Math.random() * (((PANEL_HEIGHT - snakeSize) / snakeSize) + 1));
        appleLoc[1] = randNum * snakeSize;
    }
    
    /**
     * Adds velocity to current snake x,y location
     * Removes first item in snakeSegments list and appends current location of the head to the end.
     * Doesn't remove first item if apple has been eaten and snake length has increased.
     */
    private void move(){
        snakeLoc[0] += snakeVelocity[0];
        snakeLoc[1] += snakeVelocity[1];
        if (isMoving()){
            if (snakeLength == snakeSegments.size()){
                snakeSegments.remove(0);
                snakeSegments.add(new int[]{snakeLoc[0], snakeLoc[1]});
            }
            else {
                snakeSegments.add(new int[]{snakeLoc[0], snakeLoc[1]});
            }
        }
        allowInput = true;

    }
    
    /**
     * Checks that the snake head is moving in any direction
     * @return 
     */
    private boolean isMoving(){
        return(snakeVelocity[0] != 0 || snakeVelocity[1] != 0);
    }
    
    /**
     * Checks that apple doesn't spawn on the snake head or any body segment
     * Checks if the snake head will be on the same co-ordinates as the apple after the next move() call
     * If so, calls appleFound method
     */
    private void checkApple(){
        for(int i = 0; i < snakeSegments.size(); i++){
            if (snakeSegments.get(i)[0] == appleLoc[0] && snakeSegments.get(i)[1] == appleLoc[1]){
                changeAppleLoc();
            }
        }
        if (appleLoc[0] == snakeLoc[0] - snakeSize && appleLoc[1] == snakeLoc[1] && snakeVelocity[0] < 0) {
            appleFound();
        } 
        else if(appleLoc[0] == snakeLoc[0] + snakeSize && appleLoc[1] == snakeLoc[1] && snakeVelocity[0] > 0){
            appleFound();
        }
        else if(appleLoc[1] == snakeLoc[1] - snakeSize && appleLoc[0] == snakeLoc[0] && snakeVelocity[1] < 0){
            appleFound();
        }
        else if(appleLoc[1] == snakeLoc[1] + snakeSize && appleLoc[0] == snakeLoc[0] && snakeVelocity[1] > 0){
            appleFound();
        } 
    }
    
    /**
     * If apple found, the apple is moved, snake length incremented and end score shown in titlePanel
     * Timer is set to a slightly lower number based on score, the snake speed increments lineally.
     */
    private void appleFound(){
        changeAppleLoc();
        score++;
        snakeLength++;
        SnakeWindow.titlePanel.setScoreLabelText(score);
        timer.stop();
        if(DELAY - score > 0){
            timer = new Timer(DELAY - score, this);
        }
        timer.start();
    }
    
    /**
     * Checks if the snake head is going to collide with any wall after the next move() call
     * If so, calls gameOver() and game ends.
     * Checks if the snake head is on top of any body segment and calls gameOver if so.
     */
    private void checkCollision(){
        if((snakeLoc[0] == 0 && snakeVelocity[0] < 0) || 
                (snakeLoc[0] == PANEL_WIDTH - snakeSize && snakeVelocity[0] > 0) ||
                (snakeLoc[1] == 0 && snakeVelocity[1] < 0) ||
                (snakeLoc[1] == PANEL_HEIGHT - snakeSize && snakeVelocity[1] > 0)){
            gameOver();
        }
        for(int i = 0; i < snakeSegments.size() - 1; i++){
            if (snakeSegments.get(i)[0] == snakeLoc[0] && snakeSegments.get(i)[1] == snakeLoc[1]){
                gameOver();
            }
        }
    }
    
    
    /**
     * sets gameState to 0 signalling game over to all observers
     * Calls titlePanel method to show game over score.
     */
    private void gameOver(){
        timer.stop();
        gameState = 0;
        snakePanelText.setVisible(true);
        SnakeWindow.titlePanel.setGameOverText(score);

    }
    
    /**
     * Called automatically Java at intervals set by timer variable
     * checks gameState and calls necessary methods.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (gameState == 1){
            checkApple();
            checkCollision();
            move();
        }
        else if(gameState == 0){
            //do nothing
        }
        repaint();
        
    }
    
    /**
     * Called automatically by java at intervals specified by the timer variable
     * Calls drawing method
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    
    /**
     * Nested class to be added to components as a key listener
     */
    private class TAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){    
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(firstMove){
                        break;
                    }
                    if (snakeVelocity[0] <= 0 && allowInput){
                        snakeVelocity[0] = -snakeSize;
                        snakeVelocity[1] = 0;
                        allowInput = false;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(firstMove){
                        firstMove = false;
                    }                    
                    if (snakeVelocity[1] <= 0 && allowInput){
                        snakeVelocity[0] = 0;
                        snakeVelocity[1] = -snakeSize;
                        allowInput = false;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(firstMove){
                        firstMove = false;
                    }                       
                    if (snakeVelocity[0] >= 0 && allowInput){
                        snakeVelocity[0] = snakeSize;
                        snakeVelocity[1] = 0;
                        allowInput = false;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(firstMove){
                        firstMove = false;
                    }                       
                    if (snakeVelocity[1] >= 0 && allowInput){
                        snakeVelocity[0] = 0;
                        snakeVelocity[1] = snakeSize;
                        allowInput = false;
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (gameState == 0){
                        initGame();
                    }
                    break;
                default:
                    break;
            }
        }                
    }    
}
