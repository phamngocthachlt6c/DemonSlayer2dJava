/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.tilemap;

import tphamgamecoding.demonslayer.common.BuildConfig;
import tphamgamecoding.demonslayer.ui.GameFrame;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class MapLayer {
    private MapCanvas canvas;
    private TileSetModel tileSetModel;
    private LayerModel layerModel;
    
    public MapLayer(MapCanvas canvas, TileSetModel tileSet, LayerModel layerModel) {
        this.canvas = canvas;
        this.layerModel = layerModel;
        tileSetModel = tileSet;
    }
    
    public void draw(Graphics g, int offSetX, int offSetY) {
        g.setColor(Color.black);
        int rowNumber = (int) (canvas.getHeight() / tileSetModel.getTileHeight());
        int colNumber = (int) (canvas.getWidth() / tileSetModel.getTileWidth());
        for(int row = 0; row < rowNumber; row ++) {
            for(int col = 0; col < colNumber; col++) {
                int index = row * colNumber + col;
                if(index >= layerModel.getData().length) continue;
                
                int tileIndex = (int) layerModel.getData()[index];
                
                int x = offSetX + col * (int) tileSetModel.getTileWidth();
                int y = offSetY + row * (int) tileSetModel.getTileHeight();
                
                // Optimize performance by ignore tile set out of screen
                if(x > GameFrame.SCREEN_WIDTH || x + tileSetModel.getTileWidth() < 0
                        || y > GameFrame.SCREEN_HEIGHT || y + tileSetModel.getTileHeight() < 0)
                    continue;
                
                if(tileIndex != -1) {
                    g.drawImage(tileSetModel.getTiles()[tileIndex], 
                            x, 
                            y, null);
                }
                if(BuildConfig.DEBUG) {
                    g.drawRect(
                            x, 
                            y, 
                            (int) tileSetModel.getTileWidth(), (int) tileSetModel.getTileHeight());
                }
            }
        }
    }
}
