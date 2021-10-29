/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.scene;

import tphamgamecoding.demonslayer.asset.TextureAsssets;
import tphamgamecoding.demonslayer.character.Tanjiro;
import tphamgamecoding.demonslayer.common.Logger;
import tphamgamecoding.demonslayer.object.BodyCharacter;
import tphamgamecoding.demonslayer.object.Offset;
import tphamgamecoding.demonslayer.physical.Box;
import tphamgamecoding.demonslayer.physical.PhysicalObject;
import java.awt.Color;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Level1Scene extends Scene {
    
    public Level1Scene(Context context) {
        super(context);
        init();
    }

    @Override
    protected void init() {
        setBackgroundGame(TextureAsssets.backgroundGame1);
        
        Box box = new Box();
        box.setTop(450);
        box.setLeft(0);
        box.setRight(1390);
        box.setBottom(1000);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(545);
        box.setLeft(1390);
        box.setRight(2150);
        box.setBottom(570);
        addStaticPhysicalObject(box);
       
        box = new Box();
        box.setTop(680);
        box.setLeft(2110);
        box.setRight(2260);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(735);
        box.setLeft(2300);
        box.setRight(2730);
        box.setBottom(900);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(690);
        box.setLeft(2740);
        box.setRight(3110);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(640);
        box.setLeft(3120);
        box.setRight(3800);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(590);
        box.setLeft(3750);
        box.setRight(3800);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(540);
        box.setLeft(3800);
        box.setRight(3880);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(640);
        box.setLeft(3880);
        box.setRight(3930);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(595);
        box.setLeft(3940);
        box.setRight(3980);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(640);
        box.setLeft(3980);
        box.setRight(4080);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(590);
        box.setLeft(4040);
        box.setRight(4550);
        box.setBottom(800);
        addStaticPhysicalObject(box);
        
        box = new Box();
        box.setTop(227);
        box.setLeft(4550);
        box.setRight(4650);
        box.setBottom(800);
        addTrigger(box, "trigger_boss");
        
        BodyCharacter character = new Tanjiro.Builder()
                .setX(800)
                .setY(345)
                .build();
        
        addMainCharacter(character);
    }

    @Override
    protected int getLevel() {
        return 1;
    }

    @Override
    protected void onTriggerReached(String name) {
        Logger.log("Trigger " + name);
        setEarchWake(true);
        setState(STATE_BOSS_INTRODUCE);
    }
    
    private Offset cameraForBoss = new Offset(15880, 550);

    @Override
    protected Offset getBossCameraCenter() {
        return cameraForBoss;
    }

    @Override
    protected BodyCharacter getBoss() {
        BodyCharacter gunman1 = new Tanjiro.Builder()
                .setX(16000)
                .setY(200)
                .build();
         return gunman1;
    }
}
