package Minesweeper;



import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;

public class MinesweeperMenu extends JFrame{
	MinesweeperMenu(){
		JFrame f = new JFrame("MenuBar");
		MenuBar menuBar = new MenuBar();
		Menu difficulty = new Menu("Difficulty");
		Menu settings = new Menu("Settings");
		Menu game = new Menu("Game");
		MenuItem newGame = new MenuItem("New Game");
		MenuItem restartGame = new MenuItem("Restart Game");
		MenuItem randomCheck = new MenuItem("Random Check");
		MenuItem easy = new MenuItem("Easy");
		MenuItem medium = new MenuItem("Medium");
		MenuItem hard = new MenuItem("Hard");
		MenuItem insane = new MenuItem("Insane");
		Menu timer = new Menu("Timer");
		Menu audio = new Menu("Audio");
		MenuItem audioOn = new MenuItem("Audio: On");
		MenuItem audioOff = new MenuItem("Audio: Off");
		MenuItem timerOn = new MenuItem("Timer: On");
		MenuItem timerOff = new MenuItem("Timer: Off");
		menuBar.add(difficulty);
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(hard);
		difficulty.add(insane);
		
		menuBar.add(settings);
		settings.add(timer);
		timer.add(timerOn);
		timer.add(timerOff);
		settings.add(audio);
		audio.add(audioOn);
		audio.add(audioOff);
		
		menuBar.add(game);
		game.add(newGame);
		game.add(restartGame);
		game.add(randomCheck);
		
		f.setMenuBar(menuBar);
		f.setSize(400,100);
		f.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MinesweeperMenu();
	}
}
