/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsnake;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *
 * @author atbat
 */
public class SnakeWindow extends JFrame{
    
    public static TitlePanel titlePanel;
    public static SnakePanel snakePanel;
    public static MenuPanel menuPanel;
    
    public SnakeWindow(){
        super("Snake Game");
        initUI();
    }
    
    private void initUI(){
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        menuPanel = new MenuPanel();
        this.add(menuPanel);
        this.menuPanel.setVisible(true);
        titlePanel = new TitlePanel();
        this.add(titlePanel);
        this.titlePanel.setVisible(false);
        snakePanel = new SnakePanel();
        this.add(snakePanel);
        this.snakePanel.setVisible(false);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
