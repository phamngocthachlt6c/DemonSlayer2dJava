/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.texture;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Texture {
    private String name;
    private int width;
    private int height;
    private BufferedImage spriteImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(BufferedImage spriteImage) {
        this.spriteImage = spriteImage;
    }
    
    
}
