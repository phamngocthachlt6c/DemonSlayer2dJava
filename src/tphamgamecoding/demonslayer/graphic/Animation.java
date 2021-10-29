/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.graphic;

import tphamgamecoding.demonslayer.texture.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Animation {
    private List<Frame> frames;
    private List<Float> frameTimes;
    
    private float timeLapsed = 0;
    private boolean isOneShot;
    
    private int currentFrameIndex = 0;
    private Frame currentFrame;
    private float currentFrameTime;
    
    public Animation() {
        frames = new ArrayList<Frame>();
        frameTimes = new ArrayList<Float>();
    }
    
    public void addFrame(Frame frame, float frameTime) {
        frames.add(frame);
        frameTimes.add(frameTime);
        
        if(currentFrame == null) {
            currentFrame = frame;
            currentFrameTime = frameTime;
        }
    }
    
    public void update(float deltaTime) {
        if(isOneShot && currentFrameIndex == frames.size() - 1) {
            return;
        }
        timeLapsed += deltaTime;
        if(timeLapsed >= currentFrameTime) {
            timeLapsed = 0;
            currentFrameIndex += 1;
            if(currentFrameIndex >= frames.size()) {
                currentFrameIndex = 0;
            }
            currentFrame = frames.get(currentFrameIndex);
            currentFrameTime = frameTimes.get(currentFrameIndex);
        }
    }
    
    public Frame getCurrentFrame() {
        return currentFrame;
    }
    
    public void draw(Graphics g, int centerX, int centerY) {
        if(currentFrame == null) {
            return;
        }
        g.drawImage(currentFrame.getImage(), 
                centerX - currentFrame.getWidth() / 2, 
                centerY - currentFrame.getHeight() / 2,
                currentFrame.getWidth(), currentFrame.getHeight(), null);
    }
    
    public void reset() {
        currentFrameIndex = 0;
        currentFrame = frames.get(currentFrameIndex);
        currentFrameTime = frameTimes.get(currentFrameIndex);
    }
    
    public void setOneShot(boolean isOneShot) {
        this.isOneShot = isOneShot;
    }
    
    public void setIndex(int index) {
        if(index >= frames.size()) {
            return;
        }
        currentFrameIndex = index;
        currentFrame = frames.get(currentFrameIndex);
        currentFrameTime = frameTimes.get(currentFrameIndex);
    }
    
    public int getIndex() {
        return currentFrameIndex;
    }
    
    public boolean isEnded() {
        return isOneShot && currentFrameIndex == frames.size() - 1;
    }
}
