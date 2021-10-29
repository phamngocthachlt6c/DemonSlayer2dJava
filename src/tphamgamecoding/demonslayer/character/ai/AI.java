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
public abstract class AI<T> {
    protected T actionListener;
    protected BodyCharacter bodyCharacter;
    public AI(BodyCharacter bodyCharacter) {
        this.bodyCharacter = bodyCharacter;
    }
    
    public void setActionListener(T actionListener) {
        this.actionListener = actionListener;
    }
    
    public abstract void update(float deltaTime);
}
