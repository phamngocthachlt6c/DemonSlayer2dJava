/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.object;

import java.util.Random;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Camera {
    private int width;
    private int heigth;
    private int centerX;
    private int centerY;
    
    private Random random;
    private boolean isEarchWake = false;
    
    private boolean isLock = false;

    public Camera(int centerX, int centerY, int width, int height) {
        this.centerX = centerX;
        this.centerY = centerY;
        random = new Random();
    }
    
    public void setEarchWake(boolean isEnable) {
        isEarchWake = isEnable;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        if(isLock) return;
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }
    
    public void moveCamera(int x, int y) {
        centerX = x;
        centerY = y;
    }

    public void setCenterY(int centerY) {
        if(isLock) return;
        this.centerY = centerY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }
    
    public int getXOnScreen(int realX) {
        return realX - centerX + width / 2 + getRandomForWake();
    }
    
     public int getYOnScreen(int realY) {
        return realY - centerY + (int)(heigth / 1.5f) + getRandomForWake();
    }
     
     public void lock() {
         isLock = true;
     }
     
     public void unLock() {
         isLock = false;
     }
     
     private int getRandomForWake() {
         return isEarchWake?(random.nextInt(10) - 5):0;
     }
}
