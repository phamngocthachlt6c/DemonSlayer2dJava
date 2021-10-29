/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.tilemap;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class TileMapModel {
    private LayerModel[] layers;
    private TileSetModel[] tilesets;
    private MapCanvas canvas;

    public LayerModel[] getLayers() {
        return layers;
    }

    public void setLayers(LayerModel[] layers) {
        this.layers = layers;
    }

    public TileSetModel[] getTilesets() {
        return tilesets;
    }

    public void setTilesets(TileSetModel[] tilesets) {
        this.tilesets = tilesets;
    }

    public MapCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(MapCanvas canvas) {
        this.canvas = canvas;
    }
    
}
