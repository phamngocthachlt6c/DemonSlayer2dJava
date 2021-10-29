/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.character.ai;

import tphamgamecoding.demonslayer.object.BodyCharacter;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class FireAction extends AI<FireActionListener> {

    private float lastFireTime = 0;
    public FireAction(BodyCharacter bc) {
        super(bc);
    }

    @Override
    public void update(float deltaTime) {
        lastFireTime+= deltaTime;
        if(lastFireTime > 0.5) {
            lastFireTime = 0;
            actionListener.fire();
        }
    }
    
}
