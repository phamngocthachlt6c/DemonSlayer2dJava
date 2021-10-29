/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.asset;

import tphamgamecoding.demonslayer.tilemap.TileMapModel;
import tphamgamecoding.demonslayer.tilemap.TileMapReader;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class TileMapAssets {
    public static TileMapModel scene1TileMap;
    
    public static void loadTileMaps() {
        scene1TileMap = new TileMapReader().loadTileMap("data/tilemap/tilemap1.json");
    }
}
