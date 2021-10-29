/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.character.ai;

import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.physical.PhysicalObject;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class RunOnGround extends AI<RunActionListener> {

    private float time;
    private int direction = 1;
    private float oldX;
    private PhysicalObject safeArea;

    public RunOnGround(BodyCharacter bodyCharacter) {
        super(bodyCharacter);
        oldX = bodyCharacter.getPhysicalBody().getLeft();
    }
    
    public void setSafeArea(PhysicalObject area) {
        safeArea = area;
    }
    
    @Override
    public void update(float deltaTime) {
        // Run only on safe area (a box landing)
        if(safeArea != null) {
            if(bodyCharacter.getPhysicalBody().getRight() >= safeArea.getRight()) {
                direction = -1;
            } else if(bodyCharacter.getPhysicalBody().getLeft() <= safeArea.getLeft()) {
                direction = 1;
            } else {
                direction = bodyCharacter.getIsLookingForward()?1:-1;
            }
            actionListener.run(direction);
            return;
        }
        
        // Change direction when reaching a wall of box
        if(bodyCharacter.getPhysicalBody().getLeft() == oldX) {
            direction *= -1;
        }
        oldX = bodyCharacter.getPhysicalBody().getLeft();
        actionListener.run(direction);
    }
    
}
