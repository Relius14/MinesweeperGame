package Minesweeper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class MinesweeperGame extends JFrame {
	
	private JLabel statusBar, timeBar, radarBar;
	private JPanel labelPanel, gamePanel, buttonsPanel;
	private TimeManager timeManager;
	private AudioManager audioManager;
	private JButton button;
	private JMenuBar menuBar;
	private JMenu difficulty;
	private JMenuItem easy, medium, hard, insane;
	private GameFeatures gameType;
	
	public MinesweeperGame() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setTitle("Minesweeper");
		setLabelPanel();
		setGamePanel();
		setButtonsPanel();
		setMenuBar();
		
		pack();
		setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	//sets the UI for the main game panel
	private void setGamePanel() {
		audioManager = new AudioManager();
		timeManager = new TimeManager(timeBar, audioManager);
		gameType = new GameFeatures(10,15,statusBar,timeManager, radarBar, audioManager);
		timeManager.setGame(gameType);
		
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.add(gameType,BorderLayout.CENTER);
		add(gameType);
	}
	//sets the UI panel for buttons
	private void setButtonsPanel() {
		buttonsPanel =  new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		button = new JButton("Audio");
		button.addActionListener(new ToggleAudio(this));
		button.setBackground(Color.GREEN);
		button.setFont(new Font("Verdana", Font.PLAIN, 12));
		buttonsPanel.add(button, c);
		
		c.fill = GridBagConstraints.PAGE_START;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		button = new JButton("New Game");
		button.addActionListener(new SameGame(this));
		button.setBackground(Color.YELLOW);
		button.setFont(new Font("Verdana", Font.PLAIN, 12));
		buttonsPanel.add(button, c);
		
		c.fill = GridBagConstraints.FIRST_LINE_END;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		button = new JButton("Random");
		button.addActionListener(new RandomReveal(this));
		button.setBackground(Color.RED);
		button.setFont(new Font("Verdana", Font.PLAIN, 12));
		buttonsPanel.add(button, c);
		
		add(buttonsPanel, BorderLayout.NORTH);
	}
	//sets the UI panel for timer, radars and flags labels
	private void setLabelPanel() {
		labelPanel = new JPanel(new BorderLayout());
		
		timeBar = new JLabel();
		timeBar.setForeground(Color.BLACK);
		timeBar.setFont(new Font("Verdana", Font.PLAIN, 12));
		labelPanel.add(timeBar,BorderLayout.NORTH);
		
		radarBar = new JLabel("Radars left: 3");
		radarBar.setForeground(Color.BLUE);
		radarBar.setFont(new Font("Verdana", Font.PLAIN, 12));
		labelPanel.add(radarBar,BorderLayout.CENTER);
		
		statusBar = new JLabel("");
		statusBar.setForeground(Color.RED);
		statusBar.setFont(new Font("Verdana", Font.PLAIN, 12));
		labelPanel.add(statusBar, BorderLayout.SOUTH);
        
        add(labelPanel,BorderLayout.SOUTH);
	}
	//sets the UI menu bar for difficulty settings
	private void setMenuBar() {
		 	menuBar = new JMenuBar();
		 	setJMenuBar(menuBar);
		 	
		 	difficulty = new JMenu("Difficulty");
			menuBar.add(difficulty);
			
			easy = new JMenuItem("Easy");
			easy.addActionListener(new EasyGame(this));
			difficulty.add(easy);
			
			medium = new JMenuItem("Medium");
			medium.addActionListener(new MediumGame(this));
			difficulty.add(medium);
			
			hard = new JMenuItem("Hard");
			hard.addActionListener(new HardGame(this));
			difficulty.add(hard);
			
			insane = new JMenuItem("Insane");
			insane.addActionListener(new InsaneGame(this));
			difficulty.add(insane);
	}
	
	protected GameFeatures getGameType() {
		return gameType;
	}
	
	protected void toggleAudio() {
		audioManager.setAudio();
	}
	
	protected void audioButtons() {
		audioManager.playButton();
	}
	
	//changes the game type and resets the game
	protected void resetGameType(GameType newType) {
		setResizable(true);
		remove(gameType);
		gameType = new GameFeatures(newType.getSize(), newType.getMines(), statusBar, timeManager, radarBar, audioManager);
		add(gameType);
		timeManager.setGame(gameType);
		timeManager.setInsane(newType.getInsane());
		pack();
		setResizable(false);
	}

	public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MinesweeperGame();
            ex.setVisible(true);
        });
    }

	
}
