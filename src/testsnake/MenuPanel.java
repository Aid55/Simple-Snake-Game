/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsnake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Aidan
 */
public class MenuPanel extends JPanel implements ActionListener{
    JButton playGame;
    JLabel headerText;
    GridBagConstraints topLabel;
    GridBagConstraints middleButton;
    
    public MenuPanel(){
        initMenuPanel();
    }
    
    private void initMenuPanel(){
        this.setLayout(new GridBagLayout());
        topLabel = new GridBagConstraints();
        topLabel.gridx = GridBagConstraints.NORTH;
        topLabel.gridy = GridBagConstraints.NORTH;
        headerText = new JLabel("Welcome to Snake!");
        headerText.setFont(new Font("Serif", Font.PLAIN, 60));
        headerText.setForeground(Color.red);
        this.add(headerText, topLabel); 
        
        middleButton = new GridBagConstraints();
        middleButton.gridx = GridBagConstraints.CENTER;
        middleButton.gridy = GridBagConstraints.CENTER;
        playGame = new JButton("Play Game");
        playGame.setFont(new Font("Serif", Font.PLAIN, 60));
        playGame.addActionListener(this);
        this.add(playGame, middleButton); 
        
        this.setPreferredSize(new Dimension(1200, 900));
        this.setBackground(Color.GREEN);
        this.setFocusable(true);
        this.setLayout(new GridBagLayout());       
    }
    
    public void actionPerformed(ActionEvent e){
        SnakeWindow.titlePanel.setVisible(true);
        SnakeWindow.snakePanel.setVisible(true);
        SnakeWindow.menuPanel.setVisible(false);
    }
    
    
}
