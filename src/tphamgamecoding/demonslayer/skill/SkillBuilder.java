/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.skill;

import tphamgamecoding.demonslayer.object.BodyCharacter;

/**
 *
 * @author THACH
 */
public abstract class SkillBuilder {
    protected BodyCharacter character;
    
    public SkillBuilder(BodyCharacter character) {
        this.character = character;
    }
    public abstract Skill create();
}
