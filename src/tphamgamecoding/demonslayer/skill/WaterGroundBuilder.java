/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.skill;

import tphamgamecoding.demonslayer.object.BodyCharacter;

/**
 *
 * @author THANH
 */
public class WaterGroundBuilder extends SkillBuilder {

    public WaterGroundBuilder(BodyCharacter character) {
        super(character);
    }

    @Override
    public Skill create() {
        return new WaterGroundSkill(character);
    }
    
}
