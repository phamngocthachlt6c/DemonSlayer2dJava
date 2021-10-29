/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.asset;

import tphamgamecoding.demonslayer.texture.Texture;
import tphamgamecoding.demonslayer.texture.TextureReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class TextureAsssets {
    public static Map<String, Texture> tanjiroTextures;
    public static Map<String, Texture> bulletsTextureMap;
    
    public static List<Texture> gunmanTextures;
    public static List<Texture> vestmanTextures;
    public static List<Texture> gokuTextures;
    
    public static BufferedImage backgroundGame1;
    
    public static void loadTextures() {
        tanjiroTextures = new TextureReader().readTextureDataMap("data/tanjiro_sprite.json");
        
        try {
            backgroundGame1 = ImageIO.read(new File("data/tilemap/background_game.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(TextureAsssets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
