package Minesweeper;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MinesweeperGame extends JFrame {
	
	private JLabel statusbar, timebar;
	private TimeManager timeManager;
	private JMenuBar menuBar;
	private JMenu difficulty, game;
	private JMenuItem newGame, randomCheck, easy, medium, hard, insane, audioToggle;
	private GameFeatures gameType;
	
	public MinesweeperGame() {
		setMenuBar();
		setMenuListeners();
		setLabels();
		
        //gridbaglayout
        timeManager = new TimeManager(timebar);
        gameType = new GameFeatures(10,15,statusbar,timeManager);
		timeManager.setGame(gameType);
		
		add(gameType);
		pack();
		setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
	private void setLabels() {
		setTitle("Minesweeper");
		statusbar = new JLabel("");
		timebar = new JLabel();
		add(statusbar, BorderLayout.AFTER_LAST_LINE);
        add(timebar,BorderLayout.NORTH);
	}
	
	private void setMenuBar() {
		 	menuBar = new JMenuBar();
		 	setJMenuBar(menuBar);
		 	
		 	difficulty = new JMenu("Difficulty");
			menuBar.add(difficulty);
			
			easy = new JMenuItem("Easy");
			difficulty.add(easy);
			
			medium = new JMenuItem("Medium");
			difficulty.add(medium);
			
			hard = new JMenuItem("Hard");
			difficulty.add(hard);
			
			insane = new JMenuItem("Insane");
			difficulty.add(insane);
			
			audioToggle = new JMenuItem("Audio Toggle");
			menuBar.add(audioToggle);
			
			game = new JMenu("Game");
			menuBar.add(game);
			
			newGame = new JMenuItem("New Game");
			game.add(newGame);
			
			randomCheck = new JMenuItem("Random Check");
			game.add(randomCheck);
	}
	
	private void setMenuListeners() {
		easy.addActionListener(new EasyGame(this));
		medium.addActionListener(new MediumGame(this));
		hard.addActionListener(new HardGame(this));
		insane.addActionListener(new InsaneGame(this));
		
		newGame.addActionListener(new SameGame(this));
		randomCheck.addActionListener(new randomReveal());
	}
	
	public GridGenerator getGameType() {
		return gameType;
	}
	
	public void resetGameType(GameType newType) {
		setResizable(true);
		remove(gameType);
		gameType = new GameFeatures(newType.getSize(), newType.getMines(), statusbar, timeManager);
		add(gameType);
		timeManager.setGame(gameType);
		timeManager.setInsane(newType.getInsane());
		pack();
		setResizable(false);
	}

	class randomReveal implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			gameType.randomCheck();
		}
	}
	
	//jaxb
	public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MinesweeperGame();
            ex.setVisible(true);
        });
    }
}
