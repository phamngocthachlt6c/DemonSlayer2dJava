/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.physical;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class PhysicalManager {
    
    private List<GroundCharacterBox> listCharacterBox;
    private List<PhysicalObject> listPhysicalObject;
    
    private Rectangle characterBoxFutureRect;
    
    public PhysicalManager() {
        listCharacterBox = new ArrayList<GroundCharacterBox>();
        listPhysicalObject = new ArrayList<PhysicalObject>();
        characterBoxFutureRect = new Rectangle();
    }
    
    public void addCharacterBox(GroundCharacterBox characterBox) {
        listCharacterBox.add(characterBox);
    }
    
    public void addStaticObject(PhysicalObject staticObject) {
        listPhysicalObject.add(staticObject);
    }
    
    public void removeAllObject() {
        listCharacterBox.clear();
        listPhysicalObject.clear();
    }
    
    public void update(float deltaTime) {
        // Never let cb get collision with others
        for(GroundCharacterBox cb : listCharacterBox) {
            if(!cb.isInScreenArea()) 
                continue;
            Rectangle oldRect = cb.getRectBound();
            cb.copyRect(characterBoxFutureRect);
            characterBoxFutureRect.y = oldRect.y + 2;
            if(cb.getVelocY() == 0) {
                boolean isCollisionAppear = false;
                for(PhysicalObject staticObject : listPhysicalObject) {
                    if(isCollision(characterBoxFutureRect, staticObject.getRectBound())) {
                        isCollisionAppear = true;   
                        break;
                    }
                }
                if(!isCollisionAppear)
                    cb.setVelocY(cb.getVelocY() + EnvironmentAttributes.gravity * deltaTime);
            } else {
                cb.setVelocY(cb.getVelocY() + EnvironmentAttributes.gravity * deltaTime);
            }
            for(PhysicalObject staticObject : listPhysicalObject) {
                cb.copyRect(characterBoxFutureRect);
                // Check horizontal first
                if(cb.canRun())
                    characterBoxFutureRect.x = (int) (oldRect.x + deltaTime * cb.getVelocX());
                if(isCollision(characterBoxFutureRect, staticObject.getRectBound())) {
                    int newX;
                    if(cb.getVelocX() > 0) 
                        newX = (int) (staticObject.getLeft() - characterBoxFutureRect.width);
                    else 
                        newX = (int) (staticObject.getRight());
                    characterBoxFutureRect.x = newX;
                    cb.onCollisionCallback(cb.getVelocX() > 0 ? 
                            PhysicalObject.CollisionCallback.RIGHT_EDGE : PhysicalObject.CollisionCallback.LEFT_EDGE);
                    cb.setVelocX(0);
//                    break;
                } else {
                    cb.onNoCollisionInFuture(cb.getVelocY() > 0 ? 
                            PhysicalObject.CollisionCallback.BOTTOM_EDGE : PhysicalObject.CollisionCallback.TOP_EDGE);
                }
                // Check vertical
                characterBoxFutureRect.y = (int) (oldRect.y + deltaTime * cb.getVelocY());
                if(isCollision(characterBoxFutureRect, staticObject.getRectBound())) {
                    int newY;
                    if(cb.getVelocY() > 0) 
                        newY = (int) (staticObject.getTop() - characterBoxFutureRect.height);
                    else 
                        newY = (int) (staticObject.getBottom());
                    characterBoxFutureRect.y = newY;
                    cb.onCollisionCallback(cb.getVelocY() > 0 ? 
                            PhysicalObject.CollisionCallback.BOTTOM_EDGE : PhysicalObject.CollisionCallback.TOP_EDGE);
                    cb.setVelocY(0);
                    break;
                } else {
                    cb.onNoCollisionInFuture(cb.getVelocY() > 0 ? 
                            PhysicalObject.CollisionCallback.BOTTOM_EDGE : PhysicalObject.CollisionCallback.TOP_EDGE);
                }
            }
            // Update new position
            cb.updateRect(characterBoxFutureRect);
        }
    }
    
    public void checkCollisions() {
        for(GroundCharacterBox cb : listCharacterBox) {
            if(!cb.isInScreenArea()) 
                return;
            for(PhysicalObject staticObject : listPhysicalObject) {
                checkCollisionWithX(cb, staticObject);
            }
        }
    }
    
    private void checkCollisionWithX(GroundCharacterBox cb, PhysicalObject staticObject) {
        cb.copyRect(characterBoxFutureRect);
//        characterBoxFutureRect.x += cb.get
    }
    
    private boolean isCollision(Rectangle rectA, Rectangle rectB) {
        return (rectA.x < rectB.x + rectB.width && rectA.x + rectA.width > rectB.x &&
                    rectA.y < rectB.y + rectB.height && rectA.y + rectA.height > rectB.y);
    }
    
    
    public void drawPhysicalObjectForDebug(Graphics g) {
        for(PhysicalObject physicalObject : listPhysicalObject) {
            physicalObject.draw(g);
        }
    }
        
}
