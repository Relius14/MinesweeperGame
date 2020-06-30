package Minesweeper;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;



public class MinesweeperGame extends JFrame {
	
	private JLabel statusbar, timebar;
	private int i;
	private Timer time;
	JMenuBar menuBar;
	JMenu difficulty, settings, game, timer, audio;
	JMenuItem newGame, restartGame, randomCheck, easy, medium, hard, insane, timerOn, timerOff, audioOn, audioOff;
	private GridGenerator gameType;
	public MinesweeperGame() {
        
		setMenuBar();
		statusbar = new JLabel("");
		timebar = new JLabel("0 : 0");
		
		add(statusbar, BorderLayout.AFTER_LAST_LINE);
        add(timebar,BorderLayout.NORTH);
        
        time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timebar.setText(Integer.toString(i / 60)+ " : "+ Integer.toString(i % 60));
				++i;
				if(i % 60 == 0 && i != 0)
					gameType.shuffle();
			}
		});
        gameType = new GridGenerator(8,8,10,statusbar,time);
		
		setJMenuBar(menuBar);
		setTitle("Minesweeper");
		initUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	private void setMenuBar() {
		 menuBar = new JMenuBar();
			difficulty = new JMenu("Difficulty");
			menuBar.add(difficulty);
			
			easy = new JMenuItem("Easy");
			medium = new JMenuItem("Medium");
			hard = new JMenuItem("Hard");
			insane = new JMenuItem("Insane");
			difficulty.add(easy);
			difficulty.add(medium);
			difficulty.add(hard);
			difficulty.add(insane);
			easy.addActionListener(new easyGame());
			medium.addActionListener(new mediumGame());
			hard.addActionListener(new hardGame());
			
			settings = new JMenu("Settings");
			menuBar.add(settings);
			
			timer = new JMenu("Timer");
			settings.add(timer);
			timerOn = new JMenuItem("Timer: On");
			timerOff = new JMenuItem("Timer: Off");
			timer.add(timerOn);
			timer.add(timerOff);
			
			audio = new JMenu("Audio");
			settings.add(audio);
			audioOn = new JMenuItem("Audio: On");
			audioOff = new JMenuItem("Audio: Off");
			audio.add(audioOn);
			audio.add(audioOff);
			
			game = new JMenu("Game");
			menuBar.add(game);
			
			newGame = new JMenuItem("New Game");
			restartGame = new JMenuItem("Restart Game");
			randomCheck = new JMenuItem("Random Check");
			game.add(newGame);
			game.add(restartGame);
			game.add(randomCheck);
			newGame.addActionListener(new anotherGame());
			randomCheck.addActionListener(new randomReveal());
	}
	private void resetGameType() {
		remove(gameType);
		
	}
	private void initUI() {
		
		setResizable(true);
		timebar.setText("0 : 0");
		i = 1;
		time.restart();
		add(gameType);
		
		pack();
		setResizable(false);
		
	}
	class easyGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resetGameType();
			gameType = new GridGenerator(8,8,10,statusbar,time);
			initUI();
		}
	}
	class randomReveal implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			gameType.randomCheck();
		}
	}
	class mediumGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resetGameType();
			gameType = new GridGenerator(12,12,20,statusbar,time);
			initUI();
		}
	}
	
	class hardGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resetGameType();
			gameType = new GridGenerator(25,25,100,statusbar,time);
			initUI();
		}
	}
	class anotherGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			i = 1;
			timebar.setText("0 : 0");
			time.restart();
			
			gameType.generateMines();
			gameType.repaint();
		}
	}
	
	public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MinesweeperGame();
            ex.setVisible(true);
        });
    }
}
