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
public class LayerModel {
    private String name;
    private String tileSet;
    private long[] data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTileSet() {
        return tileSet;
    }

    public void setTileSet(String tileSet) {
        this.tileSet = tileSet;
    }

    public long[] getData() {
        return data;
    }

    public void setData(long[] data) {
        this.data = data;
    }
    
}
