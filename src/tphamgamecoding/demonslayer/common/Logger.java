/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tphamgamecoding.demonslayer.common;

/**
 * 
 * @author Thach Pham
 * @website: https://tpgamecoding.com
 * @tutorial: on Youtube channel tphamgamecoding
 */
public class Logger {
    
    public static void log(String logContent) {
        if(!BuildConfig.DEBUG) return;
        
        System.out.println(System.currentTimeMillis() + " : " + logContent);
    }
}
