package six.gui;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
	
	private AudioInputStream clickAudioIn, bgAudioIn, winAudioIn;
	private Clip clickClip, bgClip, winClip;
	
	public void playClick() {
		
		try {
			
			clickAudioIn = AudioSystem.getAudioInputStream(new File("src/sounds/click.wav"));
			clickClip = AudioSystem.getClip();
			clickClip.open(clickAudioIn);
			clickClip.start();
			
		} catch (Exception e) {;} 
		
	}
	
	public void playBG() {
		
		try {
			
			bgAudioIn = AudioSystem.getAudioInputStream(new File("src/sounds/music.wav"));
			bgClip = AudioSystem.getClip();
			bgClip.open(bgAudioIn);
			bgClip.start();
			bgClip.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch (Exception e) {;}
		
	}
	
	public void playWin() {
		
		try {
			
			winAudioIn = AudioSystem.getAudioInputStream(new File("src/sounds/game_complete.wav"));
			winClip = AudioSystem.getClip();
			winClip.open(winAudioIn);
			winClip.start();
			
		} catch (Exception e) {;}
		
	}
	
}