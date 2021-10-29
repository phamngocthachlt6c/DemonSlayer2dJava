/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.ui;
import tphamgamecoding.demonslayer.scene.Context;
import tphamgamecoding.demonslayer.scene.Level1Scene;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, Context {
   
    private Thread thread;

    private Level1Scene level1Scene;
    
    public GamePanel() {
        level1Scene = new Level1Scene(this);
        thread = new Thread(this);
        thread.start();
    }
    
    public void update(float deltaTime) {
        level1Scene.update(deltaTime);
        
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        level1Scene.render(g);
        g.drawImage(level1Scene.getRenderImage(), 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void run() {
        long period = 20;
        long beginTime = System.currentTimeMillis();
        long currentTime;
        float deltaTime = 0;
        while(true) {
            update(deltaTime);
            currentTime = System.currentTimeMillis();
            try {
                long sleepTime = period - currentTime + beginTime;
                if(sleepTime > 0) {
                    thread.sleep(sleepTime);
                }
                
            } catch (InterruptedException ex) {}
            currentTime = System.currentTimeMillis();
            deltaTime = (float) (currentTime - beginTime) / 1000;
            beginTime = currentTime;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        level1Scene.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        level1Scene.keyReleased(e);
    }

    @Override
    public int getScreenWidth() {
        return getWidth();
    }

    @Override
    public int getScreenHeight() {
        return getHeight();
    }
}
