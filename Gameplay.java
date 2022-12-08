/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



 class Gameplay extends JPanel implements KeyListener, ActionListener  {
  public boolean play = false; //instance variable
  public int score = 0;//instance variable
  public int totalBricks = 21;//instance variable
  public Timer timer;//instance variable
  public int delay = 8;//instance variable
  public int playerX = 310;//instance variable
  public int ballposX = 120;//instance variable
  public int ballposY = 350;//instance variable
  public int ballXdir = -1;//instance variable
  public int ballYdir = -2;//instance variable
  public MapGenerator map;//instance variable of class MapGenerator
  
  //main method
public static void main(String[] args) {
        JFrame obj = new JFrame();
         Gameplay gamePlay = new Gameplay();
         obj.setBounds(10, 10, 700, 600);
         obj.setTitle("Breakout ball");
         obj.setResizable(false);
         obj.setVisible(true);
         obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         obj.add(gamePlay);
     
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    //constructor 
    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    //method
    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1, 692, 592);
        //drawing map
        map.draw((Graphics2D)g);
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        //score 
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);
        //paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
       
        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif" , Font.BOLD, 30));
            g.drawString("You Won: "+score , 285, 300);
           
          String s = Integer.toString(score);
            g.setFont(new Font("serif" , Font.BOLD, 20));
            g.drawString("Press enter to restart the game", 230, 350);
        }  
        if(ballposY > 570 ) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.white);
            g.setFont(new Font("serif" , Font.BOLD, 30));
            g.drawString("Game Over! Score: "+score, 235, 300); 
            String s = Integer.toString(score);
            //write score in a file 
        try{
                   FileOutputStream out = new  FileOutputStream("filename.txt");
                   DataOutputStream data = new DataOutputStream(out); 
               
             data.writeUTF(s);
           
                data.close();
           out.close();
           }
           catch(Exception e){
               System.out.println(e);
           }
            g.setFont(new Font("serif" , Font.BOLD, 20));
            g.drawString("Press enter to restart the game", 230, 350);
           
           
                }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
        }
        A:  for(int i = 0; i < map.map.length; i++) {
            for(int j = 0;  j < map.map[0].length; j++){
                if(map.map[i][j] > 0){
                    int brickX = j * map.brickWidth + 80;
                    int brickY = i * map.brickHeight + 50;
                    int brickWidth = map.brickWidth;
                    int brickHeight = map.brickHeight;
                   
                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                    Rectangle brickRect = rect;
                   
                    if(ballRect.intersects(brickRect)){
                        map.draw(0, i, j);
                        totalBricks--;
                        score += 2;
                       
                        if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                            ballXdir = -ballXdir;
                        }
                       
                        else{
                            ballYdir = -ballYdir;
                        }
                       
                        break A;
                    }
                }
            }
        }

           
           
        if(play) {
           ballposX += ballXdir;
           ballposY += ballYdir;
           if(ballposX < 0) {
               ballXdir = -ballXdir;           }
        }
       
        if(ballposY < 0) {
            ballYdir = -ballYdir;
        }
       
        if(ballposX > 670){
            ballXdir = -ballXdir;
        }
               
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
   
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_RIGHT){
           if(playerX >= 600){
               playerX = 600;
           }
           
           else{
               moveRight();
           }
       }
       if(e.getKeyCode() == KeyEvent.VK_LEFT){
           if(playerX < 10){
               playerX = 10;
           }
           
           else{
               moveLeft();
           }
       }
       
       if(e.getKeyCode() == KeyEvent.VK_ENTER){
           if(!play){
               play = true;
               ballposX = 120;
               ballposY = 350;
               ballXdir = -1;
               ballYdir = -2;
               playerX = 310;
               score = 0;
               totalBricks = 21;
               map = new MapGenerator(3, 7);
               
               repaint();
           }
       }
    }
   
    public void moveRight(){
        play = true;
        playerX += 20;
    }
   
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
    
   
 }
  

class variables{
     public int map[][];
   public int brickWidth;
   public int brickHeight;
    private int i;
}

 class MapGenerator extends variables  {

    public MapGenerator(int row, int col){
        map = new int[row][col];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
            }
        }
       
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
   //overloading
    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80,  i * brickHeight + 50, brickWidth, brickHeight);
               
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
   //overloading
    public void draw(int value, int row, int col){
        map[row][col] = value;
    } 
}
