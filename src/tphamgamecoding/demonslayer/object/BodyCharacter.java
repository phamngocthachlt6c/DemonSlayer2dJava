/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.object;

import tphamgamecoding.demonslayer.asset.TextureAsssets;
import tphamgamecoding.demonslayer.common.BuildConfig;
import tphamgamecoding.demonslayer.graphic.Animation;
import tphamgamecoding.demonslayer.physical.GroundCharacterBox;
import tphamgamecoding.demonslayer.physical.PhysicalObject;
import tphamgamecoding.demonslayer.texture.Frame;
import tphamgamecoding.demonslayer.texture.Texture;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tphamgamecoding.demonslayer.character.Tanjiro;
import tphamgamecoding.demonslayer.skill.Skill;
import tphamgamecoding.demonslayer.skill.SkillBuilder;
import tphamgamecoding.demonslayer.skill.SkillPool;
import tphamgamecoding.demonslayer.skill.WaterGroundBuilder;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public abstract class BodyCharacter<T extends GroundCharacterBox> 
        implements PhysicalObject.CollisionCallback {
    private T physicalBody;
    
    protected List<Animation> listAnimationForward;
    protected List<Animation> listAnimationBackWard;
    protected List<Offset> listOffset;
    
    protected Animation currentAnim;
    protected Offset currentOffset;
    
    // Anim attack
    // This anim will change depend on currentAnim
    private Animation animationAttack;
    
    // These flags for detect take anim once time
    protected boolean isOnLand = false;
    protected boolean isDuck = false;
    protected boolean isRun = false;
    protected boolean isLookForward = true;
    protected boolean isAttack = false;
    protected boolean isHurt = false;
    protected boolean isDeath = false;
    protected boolean isJumpDown = false;
    
    private float attackTime;
    
    private List<SkillPool> skillPools;
    
    protected int skillIndex;
    
    private List<Skill> skills;
    
    private int HURT_TIME = 1;
    private float blood = 10;
    private float hurtTime;
    private boolean showHurt;
    private float showHurtTime;
    
    public BodyCharacter(T physcialBody) {
        this.physicalBody = physcialBody;
        physcialBody.setCollisionCallback(this);
        skills = new ArrayList<>();
        skillPools = getSkillPools();
    }
    
    protected abstract List<SkillPool> getSkillPools();
    
    protected void initDefault() {
        currentAnim = getDefaultAnim(true);
        currentOffset = getDefaultOffset(true);
        animationAttack = getDefaultAnimAttack(true);
    }
    
    public void update(float deltaTime) {
        if(isHurt) {
            hurtTime+= deltaTime;
            if(hurtTime > HURT_TIME) {
                isHurt = false;
            }
            showHurtTime += deltaTime;
            if(showHurtTime > 0.1) {
                showHurtTime = 0;
                showHurt = !showHurt;
            }
        }
        if(isAttack) {
            attackTime+= deltaTime;
            if(attackTime > getAttackTime()) {
                isAttack = false;
            }
        }
        
        // THE LOGIC FOR GETTING ANIMATION HERE IS COMPLEX BECAUSE "PERFORMANCE"
        // Don't fix bug by always detect animtion, every something change, it will update the anim
        if(isDuck) {
            // This take performance low!!!!!
            detectCurrentAnim();
        } else if(physicalBody.getVelocY() < 0) {
            isJumpDown = false;
            // Please care full with this check
            // This is not redundant, it needs to check to prevent take anim multiple times
            // => low performance
            if(isOnLand) {
                isOnLand = false;
                detectCurrentAnim();
            }
        } else if(physicalBody.getVelocY() > 0) {
            if(isOnLand) {
                isJumpDown = false;
                isOnLand = false;
            }
            if(!isJumpDown) {
                isJumpDown = true;
                //It's very important to get current animation just one time every status changes
                // => performace
                detectCurrentAnim();
            }
        } else if(physicalBody.getVelocX() != 0) {
            boolean oldValue = isLookForward;
            isLookForward = physicalBody.getVelocX() > 0;
            if(oldValue != isLookForward || !isRun) {
                detectCurrentAnim();
            }
            isRun = true;
            
        } else if(isRun && physicalBody.getVelocX() == 0) {
            isRun = false;
            detectCurrentAnim();
        }
        if(currentAnim != null) {
            currentAnim.update(deltaTime);
        }
        
        if(animationAttack != null) {
            animationAttack.update(deltaTime);
        }
        
        for(Skill skill : skills) {
            skill.update(deltaTime);
        }
    }
    
    public void move(int direction) {
        isLookForward = direction == GroundCharacterBox.MOVE_RIGHT;
        if(!isDuck)
            physicalBody.move(direction);
    }
    
    public void beAttack(float damage) {
        isHurt = true;
        hurtTime = 0;
        showHurtTime = 0;
        blood -= damage;
        if(blood <= 0) {
            isDeath = true;
        }
    }
    
    public T getPhysicalBody() {
        return physicalBody;
    }
    
    protected Animation getCurrentAnim() {
        return currentAnim;
    }
    
    protected void setCurrentAnim(Animation animation) {
        currentAnim = animation;
    }
    
    protected Offset getCurrentOffset() {
        return currentOffset;
    }
    
    protected void setCurrentOffset(Offset offset) {
        currentOffset = offset;
    }
    
    public void draw(Graphics g) {
       if(currentAnim == null) {
           return;
       }
       int offsetX = currentOffset == null?0:currentOffset.getX();
       int offsetY = currentOffset == null?0:currentOffset.getY();
       if(!isHurt || (isHurt && !showHurt)) {
        if(isAttack) {
            if(animationAttack != null) {
                 animationAttack.draw(g, 
                     getPhysicalBody().getCamera().getXOnScreen(getPhysicalBody().getCenterX() + offsetX), 
                     getPhysicalBody().getCamera().getYOnScreen(getPhysicalBody().getCenterY() + offsetY));
            }

         } else {
            currentAnim.draw(g, 
                getPhysicalBody().getCamera().getXOnScreen(getPhysicalBody().getCenterX() + offsetX), 
                getPhysicalBody().getCamera().getYOnScreen(getPhysicalBody().getCenterY() + offsetY));
         }
       }
        
       // Draw be attack area
       physicalBody.draw(g, Color.yellow, getBeAttackArea());
    }

    @Override
    public void onCollision(int edge) {
        
        if(edge == PhysicalObject.CollisionCallback.BOTTOM_EDGE) {
            if(!isOnLand) {
                isOnLand = true;
                isJumpDown = false;
                detectCurrentAnim();
            }
        }
    }
    
    public void setDuck(boolean isDuck) {
        if(!isOnLand) return;
        this.isDuck = isDuck;
        if(isDuck) {
            currentAnim = getDuckAnim(isLookForward);
            animationAttack = getDefaultAnimAttack(isLookForward);
            currentOffset = getDuckOffset(isLookForward);
        } else {
            detectCurrentAnim();
        }
    }
    
    @Override
    public void onNoCollisionFuture(int edge) {
    }
    
    private void detectCurrentAnim() {
        if(isDuck) {
            currentAnim = getDuckAnim(isLookForward);
            animationAttack = getDuckAttackAnim(isLookForward);
        } else {
            if(isOnLand) {
                if(physicalBody.getVelocX() != 0) {
                    isLookForward = physicalBody.getVelocX() > 0;
                    currentAnim = getRunAnim(isLookForward);
                    currentOffset = getRunOffset(isLookForward);
                    animationAttack = getRunAttackAnim(isLookForward);
                } else {
                    currentAnim = getIdleAnim(isLookForward);
                    currentOffset = getIdleOffset(isLookForward);
                    animationAttack = getIdleAttackAnim(isLookForward);
                }
            } else {
                currentAnim = getJumpAnim(isLookForward, physicalBody.getVelocY() > 0, physicalBody.getVelocX() != 0);
                currentOffset = getJumpOffset(isLookForward, physicalBody.getVelocY() > 0);
                animationAttack = getJumpAttackAnim(isLookForward, physicalBody.getVelocY() > 0, physicalBody.getVelocX() != 0);
                
            }
        }
        if(animationAttack != null) {
            animationAttack.reset();
        }
        currentAnim.reset();
    }
    
    protected void addAnimation(String[] listFrameName, Offset offset, float scaleSize, float frameTime, 
            boolean isOneShot, Map<String, Texture> textureMap) {
        Animation animForward = new Animation();
        Animation animBackward = new Animation();
        for(String frameName : listFrameName) {
                BufferedImage image = textureMap.get(frameName).getSpriteImage();
                Frame frame = new Frame((int) (image.getWidth() * scaleSize), (int)(image.getHeight() * scaleSize), 
                    image);
                animForward.addFrame(frame, frameTime);
                animBackward.addFrame(frame.clone().flipFrame(), frameTime);
        }
        animBackward.setOneShot(isOneShot);
        animForward.setOneShot(isOneShot);
        listAnimationForward.add(animForward);
        listAnimationBackWard.add(animBackward);
        listOffset.add(offset);
    }
    
    protected void addAnimation(int fromFrame, int toFrame, Offset offset, float scaleSize, float frameTime, 
            boolean isOneShot, List<Texture> textures) {
        Animation animForward = new Animation();
        Animation animBackward = new Animation();
        if(fromFrame < toFrame) {
            for(int i = fromFrame; i <= toFrame; i++) {
                BufferedImage image = textures.get(i).getSpriteImage();
                Frame frame = new Frame((int) (image.getWidth() * scaleSize), (int)(image.getHeight() * scaleSize), 
                    image);
                animForward.addFrame(frame, frameTime);
                animBackward.addFrame(frame.clone().flipFrame(), frameTime);
            }
        } else {
            for(int i = fromFrame; i >= toFrame; i--) {
                BufferedImage image = textures.get(i).getSpriteImage();
                Frame frame = new Frame((int) (image.getWidth() * scaleSize), (int)(image.getHeight() * scaleSize), 
                    image);
                animForward.addFrame(frame, frameTime);
                animBackward.addFrame(frame.clone().flipFrame(), frameTime);
            }
        }
        animBackward.setOneShot(isOneShot);
        animForward.setOneShot(isOneShot);
        listAnimationForward.add(animForward);
        listAnimationBackWard.add(animBackward);
        listOffset.add(offset);
    }
    
    protected abstract Offset getWeaponOffset(boolean isSpecialAttack, boolean isForward);
    
    public void attack(Skill.Owner owner, String weaponName, int skillIndex) {
        this.skillIndex = skillIndex;
        if(skillPools.size() > 0) {
            Skill skill = skillPools.get(skillIndex).getSkill();
            skill.setOwner(Skill.Owner.League);
            skills.remove(skill);
            skills.add(skill);
            skill.startSkill();
        }
        isAttack = true;
        animationAttack = getIdleAttackAnim(isLookForward);
        animationAttack.reset();
        attackTime = 0;
    }
    
    public void drawSkills(Graphics g) {
        for(Skill skill: skills) {
            if(skill.getActive()) {
                skill.draw(g);
            }
        }
    }
    
    public List<Skill> getListSkills() {
        return skills;
    }
    
    protected abstract Animation getDefaultAnim(boolean isForward);
    protected abstract Animation getDefaultAnimAttack(boolean isForward);
    protected abstract Animation getIdleAnim(boolean isForward);
    protected abstract Animation getIdleAttackAnim(boolean isForward);
    protected abstract Animation getDuckAnim(boolean isForward);
    protected abstract Animation getDuckAttackAnim(boolean isForward);
    protected abstract Animation getRunAnim(boolean isForward);
    protected abstract Animation getRunAttackAnim(boolean isForward);
    protected abstract Animation getJumpAnim(boolean isForward, boolean isDown, boolean isMoveX);
    protected abstract Animation getJumpAttackAnim(boolean isForward, boolean isDown, boolean isMoveX);
    
    protected abstract Offset getDefaultOffset(boolean isForward);
    protected abstract Offset getIdleOffset(boolean isForward);
    protected abstract Offset getIdleAttackOffset(boolean isForward);
    protected abstract Offset getDuckOffset(boolean isForward);
    protected abstract Offset getDuckAttackOffset(boolean isForward);
    protected abstract Offset getRunOffset(boolean isForward);
    protected abstract Offset getRunAttackOffset(boolean isForward);
    protected abstract Offset getJumpOffset(boolean isForward, boolean isDown);
    protected abstract Offset getJumpAttackOffset(boolean isForward, boolean isDown);
    
    protected abstract float getAttackTime();
    
    private void enableShortBody() {
        
    }
    
    private void enableLongBody() {
        
    }
    
    public boolean getIsLookingForward() {
        return isLookForward;
    }
    
    public boolean isInScreen() {
        Camera camera = getPhysicalBody().getCamera();
        int leftOnScreen = camera.getXOnScreen((int) getPhysicalBody().getLeft());
        int rightOnScreen = camera.getXOnScreen((int) getPhysicalBody().getRight());
        int topOnScreen = camera.getYOnScreen((int) getPhysicalBody().getTop());
        int bottomOnScreen = camera.getYOnScreen((int) getPhysicalBody().getBottom());
        return leftOnScreen < camera.getWidth() && rightOnScreen > 0
                && topOnScreen < camera.getHeigth() && bottomOnScreen > 0;
    }
    
    public boolean canRun() {
        return true;
    }
    
    public boolean getIsDeath() {
        return isDeath;
    }
    
    private Rectangle beAttackArea = new Rectangle();
    
    public Rectangle getBeAttackArea() {
        Rectangle characterBound = physicalBody.getRectBound();
        beAttackArea.x = characterBound.x;
        beAttackArea.width = characterBound.width;
        if(isDuck) {
            beAttackArea.y = characterBound.y + characterBound.height / 2;
            beAttackArea.height = characterBound.height / 2;
        } else {
            beAttackArea.y = characterBound.y;
            beAttackArea.height = characterBound.height;
        }
        // For visible the debug rectangle
        beAttackArea.x++;
        beAttackArea.y++;
        return beAttackArea;
    }
}
