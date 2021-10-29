/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.character;

import tphamgamecoding.demonslayer.asset.TextureAsssets;
import tphamgamecoding.demonslayer.common.Logger;
import tphamgamecoding.demonslayer.graphic.Animation;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Camera;
import tphamgamecoding.demonslayer.object.Offset;
import tphamgamecoding.demonslayer.physical.GroundCharacterBox;
import tphamgamecoding.demonslayer.physical.PhysicalObject;
import tphamgamecoding.demonslayer.texture.Frame;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import tphamgamecoding.demonslayer.skill.Skill;
import tphamgamecoding.demonslayer.skill.SkillPool;
import tphamgamecoding.demonslayer.skill.WaterCutBuilder;
import tphamgamecoding.demonslayer.skill.WaterDragonBuilder;
import tphamgamecoding.demonslayer.skill.WaterGroundBuilder;
import tphamgamecoding.demonslayer.skill.WaterGroundSkill;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Tanjiro extends BodyCharacter<GroundCharacterBox>{
    
    private List<Skill> listSkill;
    
    private Tanjiro(GroundCharacterBox physcialBody) {
        super(physcialBody);
        listSkill = new ArrayList();
        
        listAnimationForward = new ArrayList<Animation>();
        listAnimationBackWard = new ArrayList<Animation>();
        listOffset = new ArrayList<Offset>();
        float scaleSize = 1.5f;
        // Idle anim [0]
        addAnimation(new String[]{"idle1"}, new Offset(0, 0), scaleSize, 0.2f, false, TextureAsssets.tanjiroTextures);
        // Run anim [1]
        addAnimation(new String[]{"run1", "run2", "run3", "run4"}, new Offset(0, 0), scaleSize, 0.1f, false, TextureAsssets.tanjiroTextures);
        // Jumpup anim [2]
        addAnimation(new String[]{"jump1", "jump2", "jump3"}, new Offset(0, 0), scaleSize, 0.06f, true, TextureAsssets.tanjiroTextures);
        // Jump down anim [3]
        addAnimation(new String[]{"landing1", "landing2"}, new Offset(0, 0), scaleSize, 0.06f,true, TextureAsssets.tanjiroTextures);
        // Run jump up [4]
        addAnimation(new String[]{"jump1", "jump2", "jump3"}, new Offset(0, 0), scaleSize, 0.06f,true, TextureAsssets.tanjiroTextures);
        // Run jump down [5]
        addAnimation(new String[]{"landing1", "landing2"}, new Offset(0, 0), scaleSize, 0.06f,true, TextureAsssets.tanjiroTextures);
        // Run attack [6]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, new Offset(0, 0), 
                scaleSize, 0.1f,true, TextureAsssets.tanjiroTextures);
        // Jump down attack [7]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, new Offset(0, 0), 
                scaleSize, 0.06f,false, TextureAsssets.tanjiroTextures);
        // Jump up attack [8]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, new Offset(0, 0), 
                scaleSize, 0.06f,false, TextureAsssets.tanjiroTextures);
        // Jump up attack [9]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, new Offset(0, 0), 
                scaleSize, 0.06f,false, TextureAsssets.tanjiroTextures);
        // Jump up run attack [10]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, 
               new Offset(0, 0),  scaleSize, 0.06f,false, TextureAsssets.tanjiroTextures);
        // Jump down run attack [11]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, 
                new Offset(0, 0), scaleSize, 0.06f,false, TextureAsssets.tanjiroTextures);
        // idle attack [12]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, 
                new Offset(0, 0), scaleSize, 0.1f,true, TextureAsssets.tanjiroTextures);
        // Special idle attack [13]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, 
                new Offset(-100, 0), scaleSize, 0.1f,false, TextureAsssets.tanjiroTextures);
        // Duck [14]
        addAnimation(new String[]{"idle1"}, 
                new Offset(0, 0), scaleSize, 0.1f,false, TextureAsssets.tanjiroTextures);
        // Duck attack [15]
        addAnimation(new String[]{"start_attack", "end_attack", "end_attack"}, 
                new Offset(0, 0), scaleSize, 0.1f,false, TextureAsssets.tanjiroTextures);
        //attack skill index 1 [16] water cut
        addAnimation(new String[]{"start_attack", "idle_attack2", "idle_attack2"}, 
                new Offset(0, 0), scaleSize, 0.1f,false, TextureAsssets.tanjiroTextures);
        initDefault();
        //attack skill index 2 [17] water dragon
        addAnimation(new String[]{"start_attack", "water_dragon1","water_dragon2"}, 
                new Offset(0, 0), scaleSize, 0.1f,true, TextureAsssets.tanjiroTextures);
        initDefault();
    }

    @Override
    protected Animation getDefaultAnim(boolean isForward) {
        return listAnimationForward.get(0);
    }
    
    @Override
    protected Animation getDefaultAnimAttack(boolean isForward) {
        return listAnimationForward.get(0);
    }

    @Override
    protected Animation getIdleAnim(boolean isForward) {
//        Logger.log("Cobra: getAnim idle");
        return isForward ? listAnimationForward.get(0) : listAnimationBackWard.get(0);
    }

    @Override
    protected Animation getIdleAttackAnim(boolean isForward) {
        return getAttackAnimBySkillIndex(isForward);
    }
    
    private Animation getAttackAnimBySkillIndex(boolean isForward) {
        if(skillIndex == 0) {
            return isForward?listAnimationForward.get(13):listAnimationBackWard.get(13);
        } else if(skillIndex == 1) {
            return isForward?listAnimationForward.get(16):listAnimationBackWard.get(16);
        } else if(skillIndex == 2) {
            return isForward?listAnimationForward.get(17):listAnimationBackWard.get(17);
        } else {
            return isForward?listAnimationForward.get(13):listAnimationBackWard.get(13);
        }
    }

    @Override
    protected Animation getDuckAnim(boolean isForward) {
//        Logger.log("Cobra: getAnim duck");
        return isForward ? listAnimationForward.get(14) : listAnimationBackWard.get(14);
    }

    @Override
    protected Animation getDuckAttackAnim(boolean isForward) {
//        Logger.log("Cobra: getAnim duck attack");
        return isForward ? listAnimationForward.get(15) : listAnimationBackWard.get(15);
    }

    @Override
    protected Animation getRunAnim(boolean isForward) {
//        Logger.log("Cobra: getAnim run");
        return isForward ? listAnimationForward.get(1) : listAnimationBackWard.get(1);
    }

    @Override
    protected Animation getRunAttackAnim(boolean isForward) {
        
        return isForward ? listAnimationForward.get(6) : listAnimationBackWard.get(6);
    }

    @Override
    protected Animation getJumpAnim(boolean isForward, boolean isDown, boolean isMoveX) {
//        Logger.log("Cobra: getAnim jump");
        int jumpIndex;
        if(isMoveX) {
            jumpIndex = isDown ? 5 : 4;
        } else {
            jumpIndex = isDown ? 3 : 2;
        }
        return isForward ? listAnimationForward.get(jumpIndex) : listAnimationBackWard.get(jumpIndex);
    }

    @Override
    protected Animation getJumpAttackAnim(boolean isForward, boolean isDown, boolean isMoveX) {
        int jumpIndex;
        if(isMoveX) {
            jumpIndex = isDown ? 11 : 10;
        } else {
            jumpIndex = isDown ? 7 : 8;
        }
        return isForward ? listAnimationForward.get(jumpIndex) : listAnimationBackWard.get(jumpIndex);
    }

    @Override
    protected Offset getDefaultOffset(boolean isForward) {
        return listOffset.get(0);
    }

    private Offset idleOffset = new Offset(0, -5);
    @Override
    protected Offset getIdleOffset(boolean isForward) {
        return idleOffset;
    }

    @Override
    protected Offset getIdleAttackOffset(boolean isForward) {
        return null;
    }

    private Offset duckOffset = new Offset(0, 10);
    
    @Override
    protected Offset getDuckOffset(boolean isForward) {
        return duckOffset;
    }

    @Override
    protected Offset getDuckAttackOffset(boolean isForward) {
        return null;
    }

    private Offset runOffset = new Offset(0, 0);
    @Override
    protected Offset getRunOffset(boolean isForward) {
        return runOffset;
    }

    @Override
    protected Offset getRunAttackOffset(boolean isForward) {
        return null;
    }

    private Offset jumpOffset = new Offset(0, 0);
    @Override
    protected Offset getJumpOffset(boolean isForward, boolean isDown) {
        int jumpIndex = isDown ? 3 : 2;
        jumpOffset.setY(isDown?-3:0);
        return jumpOffset;
    }

    @Override
    protected Offset getJumpAttackOffset(boolean isForward, boolean isDown) {
        return null;
    }

    @Override
    protected float getAttackTime() {
        if(skillIndex == 2) 
            return 0.5f;
        else
            return 0.3f;
    }
    
    @Override
    protected List<SkillPool> getSkillPools() {
        List<SkillPool> pools = new ArrayList();
        pools.add(new SkillPool(5, new WaterGroundBuilder(this)));
        pools.add(new SkillPool(5, new WaterCutBuilder(this)));
        pools.add(new SkillPool(5, new WaterDragonBuilder(this)));
        return pools;
    }
    
    private Offset weaponOffset = new Offset(0, 0);
    @Override
    protected Offset getWeaponOffset(boolean isSpecialAttack, boolean isForward) {
        int offsetX, offsetY;
        if(isDuck) {
            offsetX = 40;
            offsetY = -5;
        } else if(isRun) {
            if(isOnLand) {
                offsetX = 80;
                offsetY = -38;
            } else {
                offsetX = 60;
                offsetY = -30;
            }
        } else {
            if(isOnLand) {
                offsetX = 50;
                offsetY = -45;
            } else {
                offsetX = 40;
                offsetY = -40;
            }
        }
        weaponOffset.setX(isForward?offsetX:-offsetX);
        weaponOffset.setY(offsetY);
        return weaponOffset;
    }
    
    public static class Builder {
        private int x, y;
        private Camera camera;
        
        public Tanjiro build() {
            if(camera == null) {
                camera = new Camera(0, 0, 0, 0);
            }
            GroundCharacterBox characterBox = new GroundCharacterBox(camera);
            characterBox.setLeft(x);
            characterBox.setTop(y);
            characterBox.setRight(x + 40);
            characterBox.setBottom(y + 65);
            characterBox.setCameraFocusOn(true);
            Tanjiro cobra = new Tanjiro(characterBox);
            characterBox.setOwner(cobra);
            return cobra;
        }
        
        public Builder setX(int x) {
            this.x = x;
            return this;
        }
        
        public Builder setY(int y) {
            this.y = y;
            return this;
        }
        
        public Builder setCamera(Camera camera) {
            this.camera = camera;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Cobra"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
