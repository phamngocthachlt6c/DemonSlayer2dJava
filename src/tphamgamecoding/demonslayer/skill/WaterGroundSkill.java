/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.skill;

import java.awt.Graphics;
import java.util.List;
import tphamgamecoding.demonslayer.asset.TextureAsssets;
import tphamgamecoding.demonslayer.graphic.Animation;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Offset;
import tphamgamecoding.demonslayer.texture.Texture;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class WaterGroundSkill extends Skill {
    public WaterGroundSkill(BodyCharacter character) {
        super(character);
        skillBounds.width = 50;
        skillBounds.height = 100;
        setDamage(0.1f);
        setAnimation(new String[]{"water_ground1", "water_ground2", "water_ground3",
            "water_ground4", "water_ground5", "water_ground6", "water_ground7",
            "water_ground8", "water_ground9", "water_ground10"}, new Offset(0, -35), 2f, 0.08f, true, 
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
            skillBounds.x += 100;
        } else {
            skillBounds.x -= 100;
        }
        skillAnim.reset();
    }
}
