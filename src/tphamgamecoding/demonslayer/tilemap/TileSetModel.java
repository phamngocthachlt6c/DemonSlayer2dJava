/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.tilemap;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class TileSetModel {
    private String name;
    private String image;
    private long imageWidth;
    private long imageHeight;
    private long tileWidth;
    private long tileHeight;
    private BufferedImage[] tiles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(long imageWidth) {
        this.imageWidth = imageWidth;
    }

    public long getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(long imageHeight) {
        this.imageHeight = imageHeight;
    }

    public long getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(long tileWidth) {
        this.tileWidth = tileWidth;
    }

    public long getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(long tileHeight) {
        this.tileHeight = tileHeight;
    }

    public BufferedImage[] getTiles() {
        return tiles;
    }

    public void setTiles(BufferedImage[] tiles) {
        this.tiles = tiles;
    }
    
    
}
