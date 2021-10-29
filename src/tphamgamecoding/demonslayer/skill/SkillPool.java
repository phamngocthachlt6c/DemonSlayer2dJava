/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.skill;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class SkillPool {
    private int maximumSize;
    private List<Skill> listSkill;
    private SkillBuilder builder;
    
    public SkillPool(int maximum, SkillBuilder builder) {
        listSkill = new ArrayList<>();
        this.builder = builder;
        maximumSize = maximum;
    }
    
    public Skill getSkill() {
        if(listSkill.isEmpty()) {
            return builder.create();
        } 
        for(Skill b : listSkill) {
            if(!b.getActive()) {
                return b;
            }
        }
        if(listSkill.size() < maximumSize) {
            return builder.create();
        }
        return null;
    }
}
