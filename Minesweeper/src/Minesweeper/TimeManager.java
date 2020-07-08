package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class TimeManager {
	private JLabel timebar;
	private Timer time;
	private GameFeatures game;
	private Boolean insane = false;
	private int i;
	TimeManager(JLabel timebar, AudioManager audioManager){
		i = 0;
		this.timebar = timebar;
		timebar.setText("Time: 0 : 0");
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timebar.setText("Time: " + Integer.toString(i / 60)+ " : "+ Integer.toString(i % 60));
				++i;
				if(i % 60 == 1 && i != 1 && insane)
					game.shuffle();
				if(i % 60 == 56 && insane)
					audioManager.playTick();
			}
		});
	}
	public void setInsane(Boolean insane) {
		this.insane = insane;
	}
	public void setGame(GameFeatures game) {
		this.game = game;
	}
	public void stopTime() {
		time.stop();
	}
	public void resetTime() {
		timebar.setText("Time: 0 : 0");
		i = 1;
		time.restart();
	}
}
