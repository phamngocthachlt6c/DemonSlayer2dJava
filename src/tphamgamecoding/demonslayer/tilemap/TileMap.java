/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.tilemap;

import tphamgamecoding.demonslayer.object.Camera;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class TileMap {
    private Camera camera;
    private TileMapModel tileMapModel;
    private List<MapLayer> layers;
    
    public TileMap(TileMapModel tileMapModel, Camera camera) {
        this.camera = camera;
        this.tileMapModel = tileMapModel;
        layers = new ArrayList<>();
        for(LayerModel layerModel : tileMapModel.getLayers()) {
            TileSetModel tileSetModel = findTileSet(layerModel.getTileSet());
            if(tileMapModel == null) continue;
            MapLayer layer = new MapLayer(tileMapModel.getCanvas(), tileSetModel, layerModel);
            layers.add(layer);
        }
    }
    
    private TileSetModel findTileSet(String name) {
        for(TileSetModel tileSetModel : tileMapModel.getTilesets()) {
            if(tileSetModel.getName().equals(name))
                return tileSetModel;
        }
        return null;
    }
    
    public void draw(Graphics g) {
        for(MapLayer layer : layers) {
            layer.draw(g, camera.getXOnScreen(0), camera.getYOnScreen(0));
        }
//        layers.get(0).draw(g, camera.getXOnScreen(0), camera.getYOnScreen(0));
    }
}
