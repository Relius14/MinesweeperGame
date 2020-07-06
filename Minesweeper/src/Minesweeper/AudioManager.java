package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager {
	private Clip revealSound, markSound, winSound, bombSound, sonarSound, tickingSound, buttonSound, shuffleSound;
	private AudioInputStream sound;
	private Boolean isAudio;
	AudioManager(){
		isAudio = true;
		InitAudio();
	}
	private void InitAudio() {
		try {
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Woosh.wav"));
			revealSound = AudioSystem.getClip();
			revealSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Bomb.wav"));
			bombSound = AudioSystem.getClip();
			bombSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Sonar.wav"));
			sonarSound = AudioSystem.getClip();
			sonarSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Fireworks.wav"));
			winSound = AudioSystem.getClip();
			winSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Mark.wav"));
			markSound = AudioSystem.getClip();
			markSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Tick Tock.wav"));
			tickingSound = AudioSystem.getClip();
			tickingSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Button.wav"));
			buttonSound = AudioSystem.getClip();
			buttonSound.open(sound);
			
			sound = AudioSystem.getAudioInputStream(new File("src/resources/Shuffle.wav"));
			shuffleSound = AudioSystem.getClip();
			shuffleSound.open(sound);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void playReveal() {
		if(!isAudio)
			return;
		revealSound.setFramePosition(0);
		revealSound.start();
	}
	public void playBomb() {
		if(!isAudio)
			return;
		bombSound.setFramePosition(0);
		bombSound.start();
	}
	public void playWin() {
		if(!isAudio)
			return;
		winSound.setFramePosition(0);
		winSound.start();
	}
	public void playTick() {
		if(!isAudio)
			return;
		tickingSound.setFramePosition(0);
		tickingSound.start();
	}
	public void playMark() {
		if(!isAudio)
			return;
		markSound.setFramePosition(0);
		markSound.start();
	}
	public void playSonar() {
		if(!isAudio)
			return;
		sonarSound.setFramePosition(0);
		sonarSound.start();
	}
	public void playButton() {
		if(!isAudio)
			return;
		buttonSound.setFramePosition(0);
		buttonSound.start();
	}
	public void playShuffle() {
		if(!isAudio)
			return;
		shuffleSound.setFramePosition(0);
		shuffleSound.start();
	}
	public void setAudio() {
		isAudio = !isAudio;
	}
}
class ToggleAudio implements ActionListener{
	MinesweeperGame minesweeperGame;
	ToggleAudio(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.toggleAudio();
	}
}