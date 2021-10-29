/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.physical;

import tphamgamecoding.demonslayer.object.Camera;
import java.awt.Rectangle;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class GroundCharacterBox extends PhysicalObject {
    
    public static int MOVE_RIGHT = 1;
    public static int MOVE_LEFT = -1;
    public static int MOVE_NONE = 0;
    private float currentMaxSpeed = 200;
    private float velocityX;
    private float velocityY = 50;
    
    private int horizontalDirection = MOVE_NONE;
    
    public GroundCharacterBox(Camera camera) {
        super(camera);
    }
    
    public void update(float deltaTime) {
//        Logger.log("Ground update");
        velocityX = currentMaxSpeed * horizontalDirection;
    }
    
    public boolean canRun() {
        return (owner == null) || owner.canRun();
    }
    
    public float getVelocX() {
        return velocityX;
    }
    
    public float getVelocY() {
        return velocityY;
    }
    
    public void setVelocY(float veloc) {
        velocityY = veloc;
    }
    
    public void setVelocX(float veloc) {
        velocityX = veloc;
    }
    
    public void move(int direction) {
        horizontalDirection = direction;
    }
    
    public int getHorizontalDirection() {
        return horizontalDirection;
    }
    
    public void jump() {
        velocityY = - 200;
    }
    
    public void updateRect(Rectangle rect) {
        setLeft(rect.x);
        setTop(rect.y);
        setRight(rect.x + rect.width);
        setBottom(rect.y + rect.height);
    }
    
    public boolean isInScreenArea() {
        return true;
    }

    @Override
    public boolean canGoDownThrough() {
        return false;
    }

    @Override
    public boolean canGoUpThrought() {
        return false;
    }

    @Override
    public boolean canGoThroughLeftSide() {
        return false;
    }

    @Override
    public boolean canGoThroughRighSide() {
        return false;
    }
}
