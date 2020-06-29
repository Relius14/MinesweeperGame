package Minesweeper;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



public class MinesweeperGame extends JFrame {
	
	private JLabel statusbar, timebar;
	JMenuBar menuBar;
	JMenu difficulty, settings, game, timer, audio;
	JMenuItem newGame, restartGame, randomCheck, easy, medium, hard, insane, timerOn, timerOff, audioOn, audioOff;
	
	public MinesweeperGame() {
        
        menuBar = new JMenuBar();
		difficulty = new JMenu("Difficulty");
		settings = new JMenu("Settings");
		game = new JMenu("Game");
		newGame = new JMenuItem("New Game");
		restartGame = new JMenuItem("Restart Game");
		randomCheck = new JMenuItem("Random Check");
		easy = new JMenuItem("Easy");
		medium = new JMenuItem("Medium");
		hard = new JMenuItem("Hard");
		insane = new JMenuItem("Insane");
		timer = new JMenu("Timer");
		audio = new JMenu("Audio");
		audioOn = new JMenuItem("Audio: On");
		audioOff = new JMenuItem("Audio: Off");
		timerOn = new JMenuItem("Timer: On");
		timerOff = new JMenuItem("Timer: Off");
		
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
		initUI();
    }
	
	private void initUI() {
		
		statusbar = new JLabel("");
		timebar = new JLabel(".....");
		add(statusbar, BorderLayout.AFTER_LAST_LINE);
        add(timebar,BorderLayout.NORTH);
       
		add(new GridGenerator(10,10,10,statusbar,timebar));
		
		setResizable(false);
		pack();
		setJMenuBar(menuBar);
		setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MinesweeperGame();
            ex.setVisible(true);
        });
    }
}
