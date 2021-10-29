/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.physical;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class CollisionManager {
    private List<GroundCharacterBox> listCharacterBox;
    private List<PhysicalObject> listPhysicalObject;
    
    private Rectangle characterBoxFutureRect;
    
    public CollisionManager() {
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
}
