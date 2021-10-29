/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.physical;

import tphamgamecoding.demonslayer.common.BuildConfig;
import tphamgamecoding.demonslayer.object.Camera;
import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Box extends PhysicalObject {

    public Box(Camera camera) {
        super(camera);
    }
    
    public Box() {
        
    }

    @Override
    public boolean canGoDownThrough() {
        return false;
    }

    @Override
    public boolean canGoUpThrought() {
        return false;
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
