/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.physical;

import tphamgamecoding.demonslayer.object.Camera;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Line extends PhysicalObject {

    public Line(Camera camera) {
        super(camera);
    }

    @Override
    public boolean canGoDownThrough() {
        return false;
    }
    
    @Override
    public boolean canGoUpThrought() {
        return true;
    }

    @Override
    public boolean canGoThroughLeftSide() {
        return false;
    }

    @Override
    public boolean canGoThroughRighSide() {
        return false;
    }
    
}
