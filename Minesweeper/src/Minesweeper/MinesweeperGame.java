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
	private Boolean insaneMode;
	JMenuBar menuBar;
	JMenu difficulty, settings, game;
	JMenuItem newGame, randomCheck, easy, medium, hard, insane, timerToggle, audioToggle, audioOff;
	private GridGenerator gameType;
	public MinesweeperGame() {
        
		setMenuBar();
		statusbar = new JLabel("");
		timebar = new JLabel();
		insaneMode = false;
		add(statusbar, BorderLayout.AFTER_LAST_LINE);
        add(timebar,BorderLayout.NORTH);
        
        time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timebar.setText("Time: " + Integer.toString(i / 60)+ " : "+ Integer.toString(i % 60));
				++i;
				if(i % 60 == 0 && i != 0 && insaneMode)
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
			insane.addActionListener(new insaneGame());
			settings = new JMenu("Settings");
			menuBar.add(settings);
			
			timerToggle = new JMenuItem("Timer Toggle");
			settings.add(timerToggle);
			timerToggle.addActionListener(new TimerToggle());
			
			audioToggle = new JMenuItem("Audio Toggle");
			settings.add(audioToggle);

			
			game = new JMenu("Game");
			menuBar.add(game);
			
			newGame = new JMenuItem("New Game");
			randomCheck = new JMenuItem("Random Check");
			game.add(newGame);
			game.add(randomCheck);
			newGame.addActionListener(new anotherGame());
			randomCheck.addActionListener(new randomReveal());
	}
	private void resetGameType() {
		remove(gameType);
		
	}
	private void initUI() {
		
		setResizable(true);
		timebar.setText("Time: 0 : 0");
		i = 1;
		time.restart();
		add(gameType);
		
		pack();
		setResizable(false);
		
	}
	class TimerToggle implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			timebar.setVisible(!timebar.isVisible());
		}
		
	}
	class easyGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resetGameType();
			gameType = new GridGenerator(8,8,10,statusbar,time);
			insaneMode = false;
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
			insaneMode = false;
			initUI();
		}
	}
	
	class hardGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resetGameType();
			gameType = new GridGenerator(25,25,100,statusbar,time);
			insaneMode = false;
			initUI();
		}
	}
	class insaneGame implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			resetGameType();
			gameType = new GridGenerator(30,30,100,statusbar,time);
			insaneMode = true;
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
