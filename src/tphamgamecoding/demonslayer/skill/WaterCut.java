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
public class WaterCut extends Skill {
    public WaterCut(BodyCharacter character) {
        super(character);
        skillBounds.width = 50;
        skillBounds.height = 100;
        setDamage(1);
        setAnimation(new String[]{"water_cut", "water_cut"}, new Offset(0, 0), 1.5f, 0.2f, true, 
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
            skillBounds.x += 80;
        } else {
            skillBounds.x -= 80;
        }
        skillAnim.reset();
    }
}
