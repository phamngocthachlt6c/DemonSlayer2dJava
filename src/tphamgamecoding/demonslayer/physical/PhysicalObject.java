/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.physical;

import tphamgamecoding.demonslayer.common.BuildConfig;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public abstract class PhysicalObject {
    private Camera camera;
    private Rectangle rectBound = new Rectangle();
    public abstract boolean canGoDownThrough();
    public abstract boolean canGoUpThrought();
    public abstract boolean canGoThroughLeftSide();
    public abstract boolean canGoThroughRighSide();
    
    private Color debugColor = Color.RED;
    
    private boolean isCameraFocusOn = false;
    
    private CollisionCallback collisionCallback;
    
    protected BodyCharacter owner;
    
    public PhysicalObject(Camera camera) {
        this.camera = camera;
    }
    
    public PhysicalObject() {
        
    }
    
    public void setOwner(BodyCharacter owner) {
        this.owner = owner;
    }
    
    public void setCamera(Camera camera) {
        this.camera = camera;
    } 
    
    public float getTop() {
        return rectBound.y;
    }
    public float getBottom() {
        return rectBound.y + rectBound.height;
    }
    public float getLeft() {
        return rectBound.x;
    }
    public float getRight() {
        return rectBound.x + rectBound.width;
    }
    
    public void setTop(int top) {
        rectBound.y = top;
        updateCamera();
    }
    public void setBottom(int bottom) {
        rectBound.height = bottom - rectBound.y;
        updateCamera();
    }
    public void setLeft(int left) {
        rectBound.x = left;
        updateCamera();
    }
    public void setRight(int right) {
        rectBound.width = right - rectBound.x;
        updateCamera();
    }
    
    public Rectangle getRectBound() {
        return rectBound;
    }
    
    public void draw(Graphics g) {
        if(BuildConfig.DEBUG) {
            g.setColor(debugColor);
            g.drawRect(camera.getXOnScreen(rectBound.x), camera.getYOnScreen(rectBound.y), 
                    rectBound.width, rectBound.height);
        }
    }
    
    public void draw(Graphics g, Color color, Rectangle rectBound) {
        if(BuildConfig.DEBUG) {
            g.setColor(color);
            g.drawRect(camera.getXOnScreen(rectBound.x), camera.getYOnScreen(rectBound.y), 
                    rectBound.width, rectBound.height);
        }
    }
    
    public void setDebugColor(Color color) {
        debugColor = color;
    }
    
    public void copyRect(Rectangle rect) {
        Rectangle bound = getRectBound();
        rect.x = bound.x;
        rect.y = bound.y;
        rect.width = bound.width;
        rect.height = bound.height;
    }
    
    private void updateCamera() {
        if(camera == null || !isCameraFocusOn) return;
        camera.setCenterX(getCenterX());
        camera.setCenterY(getCenterY());
    }
    
    public int getCenterX() {
        return rectBound.x + rectBound.width / 2;
    }
    
    public int getCenterY() {
        return rectBound.y + rectBound.height / 2;
    }
    
    public Camera getCamera() {
        return camera;
    }
    
    public void onCollisionCallback(int edge) {
        if(collisionCallback == null) 
            return;
        collisionCallback.onCollision(edge);
    }
    
    public void onNoCollisionInFuture(int edge) {
        if(collisionCallback == null)
            return;
        collisionCallback.onNoCollisionFuture(edge);
    }
    
    public void setCollisionCallback(CollisionCallback callback) {
        collisionCallback = callback;
    }
    
    public void setCameraFocusOn(boolean focus) {
        isCameraFocusOn = focus;
    }
    
    public interface CollisionCallback {
        final int TOP_EDGE = 0;
        final int BOTTOM_EDGE = 1;
        final int LEFT_EDGE = 2;
        final int RIGHT_EDGE = 3;
        void onCollision(int edge);
        void onNoCollisionFuture(int edge);
    }
}
