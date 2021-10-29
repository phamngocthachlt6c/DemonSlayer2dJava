/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.skill;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import tphamgamecoding.demonslayer.common.BuildConfig;
import tphamgamecoding.demonslayer.graphic.Animation;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Camera;
import tphamgamecoding.demonslayer.object.Offset;
import tphamgamecoding.demonslayer.texture.Frame;
import tphamgamecoding.demonslayer.texture.Texture;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public abstract class Skill {
    public static enum Owner {
        League,
        Enemy
    }
    protected Rectangle skillBounds;
    
    protected BodyCharacter character;
    
    protected Animation skillAnim;
    protected Animation skillAnimBack;
    protected Offset offset;
    
    protected boolean isActive;
    
    private float damage;
    
    private Owner owner = Owner.League;
    
    public Skill(BodyCharacter character) {
        this.character = character;
        skillBounds = new Rectangle();
        skillAnim = new Animation();
        skillAnimBack = new Animation();
        offset = new Offset(0, 0);
    }
    public void update(float deltaTime) {
        skillAnim.update(deltaTime);
        skillAnimBack.update(deltaTime);
    }
    
    public void startSkill() {
        isActive = true;
        skillBounds.x = character.getPhysicalBody().getCenterX() - skillBounds.width / 2;
        skillBounds.y = character.getPhysicalBody().getCenterY() - skillBounds.height / 2;
    }
    
    public Rectangle getSkillBounds() {
        return skillBounds;
    }
    
    public void draw(Graphics g) {
        if(!isActive) 
            return;

        Animation skillAnim = character.getIsLookingForward()?this.skillAnim: skillAnimBack;
        skillAnim.draw(g, 
                     character.getPhysicalBody().getCamera().getXOnScreen(skillBounds.x + offset.getX() + skillBounds.width / 2), 
                     character.getPhysicalBody().getCamera().getYOnScreen(skillBounds.y + offset.getY() + skillBounds.height / 2));
        // Draw debug
        if(BuildConfig.DEBUG) {
            g.setColor(Color.red);
            Camera camera = character.getPhysicalBody().getCamera();
            g.drawRect(camera.getXOnScreen(skillBounds.x), camera.getYOnScreen(skillBounds.y), skillBounds.width, skillBounds.height);
        }
    }
    
    protected void setAnimation(String[] listFrameName, Offset offset, float scaleSize, float frameTime, 
            boolean isOneShot, Map<String, Texture> textureMap) {
        for(String frameName : listFrameName) {
                BufferedImage image = textureMap.get(frameName).getSpriteImage();
                Frame frame = new Frame((int) (image.getWidth() * scaleSize), (int)(image.getHeight() * scaleSize), 
                    image);
                skillAnim.addFrame(frame, frameTime);
                skillAnimBack.addFrame(frame.clone().flipFrame(), frameTime);
        }
        skillAnimBack.setOneShot(isOneShot);
        skillAnim.setOneShot(isOneShot);
        this.offset.setX(offset.getX());
        this.offset.setY(offset.getY());
    }
    
    public boolean getActive() {
        return isActive;
    }
    
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    public Rectangle getBound() {
        return skillBounds;
    }
    
    public void setDamage(float damage) {
        this.damage = damage;
    }
    
    public float getDamage() {
        return damage;
    }
}
