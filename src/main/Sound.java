package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){

        soundURL[0] = getClass().getResource("/sounds/silent.wav");
        soundURL[1] = getClass().getResource("/sounds/titletheme.wav");
        soundURL[2] = getClass().getResource("/sounds/drawKnife1.wav");
        soundURL[3] = getClass().getResource("/sounds/footstep00.wav");
        soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/sounds/weapons/sword/26_sword_hit_1.wav");
        soundURL[6] = getClass().getResource("/sounds/weapons/sword/27_sword_miss_1.wav");
        soundURL[7] = getClass().getResource("/sounds/weapons/sword/11_human_damage_3.wav");

    }
    public void setFile(int i){

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
