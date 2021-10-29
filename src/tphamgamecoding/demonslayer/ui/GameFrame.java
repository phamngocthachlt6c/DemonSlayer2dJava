/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.ui;

import tphamgamecoding.demonslayer.asset.TextureAsssets;
import tphamgamecoding.demonslayer.asset.TileMapAssets;
import tphamgamecoding.demonslayer.texture.TextureReader;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class GameFrame extends JFrame {

    BufferedImage image = null;
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 720;
    public GameFrame() {
        
        // TODO: move to loading screen
        TextureAsssets.loadTextures();
        TileMapAssets.loadTileMaps();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        GamePanel gp = new GamePanel();
        addKeyListener(gp);
        add(gp);
        setVisible(true);
    }
    
    
    
}
