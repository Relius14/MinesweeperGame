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
		//watch that changes the value of i every second
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//updates the time displayed
				timebar.setText("Time: " + Integer.toString(i / 60)+ " : "+ Integer.toString(i % 60));
				++i;
				//shuffles the fields in insane mode
				if(i % 60 == 1 && i != 1 && insane)
					game.shuffle();
				//starts ticking 5 seconds before shuffling in insane mode
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
	//resets the watch at the beginning of the game
	public void resetTime() {
		timebar.setText("Time: 0 : 0");
		i = 1;
		time.restart();
	}
}
