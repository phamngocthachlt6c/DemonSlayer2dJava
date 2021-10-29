/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.scene;

import tphamgamecoding.demonslayer.asset.TileMapAssets;
import tphamgamecoding.demonslayer.common.Logger;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Camera;
import tphamgamecoding.demonslayer.object.Offset;
import tphamgamecoding.demonslayer.physical.Box;
import tphamgamecoding.demonslayer.physical.GroundCharacterBox;
import tphamgamecoding.demonslayer.physical.PhysicalManager;
import tphamgamecoding.demonslayer.physical.PhysicalObject;
import tphamgamecoding.demonslayer.tilemap.MapLayer;
import tphamgamecoding.demonslayer.tilemap.TileMap;
import tphamgamecoding.demonslayer.tilemap.TileMapModel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tphamgamecoding.demonslayer.skill.Skill;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public abstract class Scene {
    public static int STATE_BEGIN = 0;
    public static int STATE_GAME_PLAY = 1;
    public static int STATE_MAIN_DIE = 2;
    public static int STATE_GAME_OVER = 3;
    public static int STATE_BOSS_INTRODUCE = 4;
    private Context context;
    private int width, height;
    private BodyCharacter mainCharacter;
    private List<BodyCharacter> enemies;
    private BufferedImage renderedImage;
    private Camera camera;
    
    private Map<String, PhysicalObject> triggers;
    private PhysicalObject currentTrigger;
    
    private TileMap tileMap;
    
    private PhysicalManager physicalManager;
    
    private BufferedImage backgroundGame;
    
    private List<BodyCharacter> listToRemove;
    
    private int state = STATE_BEGIN;
    private float timeDelay;
    
    // For the boss introduce
    private int cameraMoveToBossSpeedX;
    private int cameraMoveToBossSpeedY;
    
    public Scene(Context context) {
        this.context = context;
        listToRemove = new ArrayList<>();
        enemies = new ArrayList<BodyCharacter>();
        physicalManager = new PhysicalManager();
        camera = new Camera(0, 0, 0, 0);
        
        TileMapModel tileMapModel = TileMapAssets.scene1TileMap;
        tileMap = new TileMap(tileMapModel, camera);
        
        triggers = new HashMap<>();
    }
    
    public void addStaticPhysicalObject(PhysicalObject p) {
        p.setCamera(camera);
        physicalManager.addStaticObject(p);
        
    }
    
    public void addTrigger(PhysicalObject physicalObject, String name) {
        physicalObject.setCamera(camera);
        physicalObject.setDebugColor(Color.yellow);
        triggers.put(name, physicalObject);
    }
    
    public void setBackgroundGame(BufferedImage bg) {
        backgroundGame = bg;
    }
    
    public void addEnemy(BodyCharacter character) {
        character.getPhysicalBody().setCamera(camera);
        physicalManager.addCharacterBox(character.getPhysicalBody());
        enemies.add(character);
    }
    
    public void addMainCharacter(BodyCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
        this.mainCharacter.getPhysicalBody().setCamera(camera);
        
        physicalManager.addCharacterBox(mainCharacter.getPhysicalBody());
    }
    
    public void update(float deltaTime) {
        if(state == STATE_BEGIN) {
            timeDelay+= deltaTime;
            if(timeDelay > 1) {
                setState(STATE_GAME_PLAY);
            }
            return;
        } else if(state == STATE_MAIN_DIE) {
            timeDelay+=deltaTime;
            if(timeDelay > 1.5) {
                setState(STATE_GAME_OVER);
            }
        } else if(state == STATE_GAME_PLAY) {
            if(mainCharacter.getIsDeath()) {
                setState(STATE_MAIN_DIE);
            }
        } else if(state == STATE_BOSS_INTRODUCE) {
            Logger.log("update Boss introduce speedX = " + cameraMoveToBossSpeedX);
            camera.moveCamera((int) (camera.getCenterX() + deltaTime * cameraMoveToBossSpeedX),
                    (int) (camera.getCenterY() + deltaTime * cameraMoveToBossSpeedY));
            timeDelay+=deltaTime;
            if(timeDelay < 1)
                ((GroundCharacterBox) mainCharacter.getPhysicalBody()).move(GroundCharacterBox.MOVE_RIGHT);
            else 
                ((GroundCharacterBox) mainCharacter.getPhysicalBody()).move(GroundCharacterBox.MOVE_NONE);
            if(timeDelay > 2) {
                setState(STATE_GAME_PLAY);
//                camera.unLock();
                addEnemy(getBoss());
                addWallPreventMainGoBack();
            }
        }
        physicalManager.update(deltaTime);
        if(mainCharacter != null) {
            mainCharacter.update(deltaTime);
            mainCharacter.getPhysicalBody().update(deltaTime);
            updateSkills(mainCharacter.getListSkills());
            
            updateTriggers();
        }
        listToRemove.clear();
        for(BodyCharacter enemy : enemies) {
            if(enemy.getIsDeath()) listToRemove.add(enemy);
            enemy.update(deltaTime);
            enemy.getPhysicalBody().update(deltaTime);
            updateSkills(enemy.getListSkills());
        }
        enemies.removeAll(listToRemove);
    }
    
    private void addWallPreventMainGoBack() {
        Box box = new Box();
        box.setTop(camera.getCenterY() - 1000);
        box.setLeft(camera.getCenterX() - camera.getWidth() / 2 - 10);
        box.setRight(camera.getCenterX() - camera.getWidth() / 2);
        box.setBottom(camera.getCenterY() + 1000);
        addStaticPhysicalObject(box);
    }
    
    private void updateTriggers() {
        // Update triggers
        boolean hasTriggerCollision = false;
        for (Map.Entry<String, PhysicalObject> entry : triggers.entrySet()) {
            if(isCollision(mainCharacter.getPhysicalBody().getRectBound(), 
                    entry.getValue().getRectBound())) {
                if(currentTrigger != entry.getValue()) {
                    currentTrigger = entry.getValue();
                    onTriggerReached(entry.getKey());
                }
                hasTriggerCollision = true;
                break;
            }
        }
        if(!hasTriggerCollision) currentTrigger = null;
    }
    
    // For deactive bullet out of screen or get collision with anything
    private void updateSkills(List<Skill> skills) {
        for(Skill skill : skills) {
            
            // Check for only active bullets
            if(skill.getActive()) {
                skill.update(timeDelay);
                // Check collision bullet from cobra
                if(skill.getOwner() == Skill.Owner.League) {
                    for(BodyCharacter enemy : enemies) {
                        if(isCollision(enemy.getPhysicalBody().getRectBound(), skill.getBound())) {
                            enemy.beAttack(skill.getDamage());
                        }
                    }
                } 
                // Check collision bullet from enemy
                else if(skill.getOwner() == Skill.Owner.Enemy) {
                    if(isCollision(mainCharacter.getBeAttackArea(), skill.getBound())) {
                        mainCharacter.beAttack(skill.getDamage());
                    }
                }
            }
        }
        
    }
    
    private boolean isCollision(Rectangle rectA, Rectangle rectB) {
        return (rectA.x < rectB.x + rectB.width && rectA.x + rectA.width > rectB.x &&
                    rectA.y < rectB.y + rectB.height && rectA.y + rectA.height > rectB.y);
    }
    
    public void render(Graphics g) {
        if(renderedImage == null) {
            this.width = context.getScreenWidth();
            this.height = context.getScreenHeight();
            camera.setWidth(width);
            camera.setHeigth(height);
            // Prevent user see render process:
            // using buffered image to avoid seeing under items
            // when drawing, it draws every layer, so user can see all layers when it
            // draw, if use a buffered image, user just see a picture of frame after it render
            renderedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        }
        Graphics bufGraphic = renderedImage.getGraphics();
        if(backgroundGame != null) {
            bufGraphic.drawImage(backgroundGame, 0, 0, renderedImage.getWidth(), 
                    renderedImage.getHeight(), null);
        } else {
            bufGraphic.setColor(Color.white);
            bufGraphic.fillRect(0, 0, width, height);
        }
        
        tileMap.draw(bufGraphic);
        
        physicalManager.drawPhysicalObjectForDebug(bufGraphic);
        
        // Draw Cobra
        if(!mainCharacter.getIsDeath()) {
            mainCharacter.draw(bufGraphic);
            mainCharacter.getPhysicalBody().draw(bufGraphic);
        }
        
        // Draw enemies
        for(BodyCharacter enemy : enemies) {
            if(!enemy.isInScreen())
                continue;
            
            enemy.draw(bufGraphic);
            enemy.getPhysicalBody().draw(bufGraphic);
            enemy.drawSkills(bufGraphic);
        }
        
        // Draw weapon
        mainCharacter.drawSkills(bufGraphic);
        
        if(state == STATE_BEGIN) {
            bufGraphic.setColor(Color.BLACK);
            bufGraphic.fillRect(0, 0, context.getScreenWidth(), context.getScreenHeight());
            bufGraphic.setColor(Color.white);
            bufGraphic.drawString("LEVEL " + getLevel(), 480, 300);
        } else if(state == STATE_GAME_OVER) {
            bufGraphic.setColor(Color.BLACK);
            bufGraphic.fillRect(0, 0, context.getScreenWidth(), context.getScreenHeight());
            bufGraphic.setColor(Color.white);
            bufGraphic.drawString("GAME OVER", 460, 300);
            bufGraphic.drawString("Press enter to replay", 440, 330);
        }
        
        // Draw triggers
        for (Map.Entry<String, PhysicalObject> entry : triggers.entrySet()) {
            entry.getValue().draw(bufGraphic);
        }
    }
    
    public BufferedImage getRenderImage() {
        return renderedImage;
    }
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                ((GroundCharacterBox) mainCharacter.getPhysicalBody()).jump();
                break;
            case KeyEvent.VK_LEFT:
                mainCharacter.move(GroundCharacterBox.MOVE_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                mainCharacter.move(GroundCharacterBox.MOVE_RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                mainCharacter.setDuck(true);
                break;
            case KeyEvent.VK_A:
                mainCharacter.attack(Skill.Owner.League, "small_bullet", 0);
                break;
            case KeyEvent.VK_S:
                mainCharacter.attack(Skill.Owner.League, "cannon", 1);
                break;
            case KeyEvent.VK_D:
                mainCharacter.attack(Skill.Owner.League, "cannon", 2);
                break;
            case KeyEvent.VK_ENTER:
                if(state == STATE_GAME_OVER) {
                    reset();
                    init();
                    state = STATE_BEGIN;
                }
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            mainCharacter.setDuck(false);
        }
        if((e.getKeyCode() == KeyEvent.VK_LEFT 
                && ((GroundCharacterBox) mainCharacter.getPhysicalBody()).getHorizontalDirection() == GroundCharacterBox.MOVE_LEFT) 
                || (e.getKeyCode() == KeyEvent.VK_RIGHT
                && ((GroundCharacterBox) mainCharacter.getPhysicalBody()).getHorizontalDirection() == GroundCharacterBox.MOVE_RIGHT)) {
            ((GroundCharacterBox) mainCharacter.getPhysicalBody()).move(GroundCharacterBox.MOVE_NONE);
        }
    }
    
    private void reset() {
        enemies.clear();
        physicalManager.removeAllObject();
    }
    
    public void setState(int state) {
        if(state == STATE_BOSS_INTRODUCE) {
            camera.lock();
            Offset offset = getBossCameraCenter();
            cameraMoveToBossSpeedX = (offset.getX() - camera.getCenterX()) / 2; // "2" is the time for introduce
            cameraMoveToBossSpeedY = (offset.getY() - camera.getCenterY()) / 2; // "2 seconds"
            
        }
        this.state = state;
        timeDelay = 0;
    }
    
    public void setEarchWake(boolean isEnable) {
        camera.setEarchWake(isEnable);
    }
    
    public void setLockCamera(boolean isLock) {
        if(isLock) 
            camera.lock();
        else 
            camera.unLock();
    }
    
    protected abstract void init();
    protected abstract int getLevel();
    
    protected abstract void onTriggerReached(String name);
    
    protected abstract Offset getBossCameraCenter();
    
    protected abstract BodyCharacter getBoss();
}
