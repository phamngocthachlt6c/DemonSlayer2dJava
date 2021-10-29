/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.skill;

import tphamgamecoding.demonslayer.asset.TextureAsssets;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Offset;

/**
 *
 * @author THANH
 */
public class WaterDragonSkill extends Skill {
    public WaterDragonSkill(BodyCharacter character) {
        super(character);
        skillBounds.width = 100;
        skillBounds.height = 100;
        setDamage(1);
        setAnimation(new String[]{"transparent", "transparent"}, new Offset(0, 0), 2f, 0.5f, true, 
            TextureAsssets.tanjiroTextures);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(skillAnim.isEnded()) {
            isActive = false;
        }
    }
    
    @Override
    public void startSkill() {
        super.startSkill();
        if(character.getIsLookingForward()) {
            skillBounds.x += 70;
        } else {
            skillBounds.x -= 70;
        }
        skillAnim.reset();
    }
}
