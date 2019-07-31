/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

/**
 *
 * @author Aidan
 */
public class TitlePanel extends JPanel{
    
    private final int PANEL_WIDTH = 1200;
    private final int PANEL_HEIGHT = 100;
    
    private JLabel scoreLabel;
    private String scoreText = "Score = ";
    
    public TitlePanel(){
        this.initSnakePanel();
    }
    
    private void initSnakePanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.GREEN);
        this.setFocusable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        scoreLabel = new JLabel("Score = " + 0);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        this.add(scoreLabel);
    }
    
    public void setScoreLabelText(int score){
        scoreLabel.setText(scoreText + score);
    }
    
    public void setGameOverText(int score){
        scoreLabel.setText("Game Over! " + scoreText + score);
    }
}
