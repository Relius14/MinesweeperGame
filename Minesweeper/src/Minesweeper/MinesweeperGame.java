package Minesweeper;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MinesweeperGame extends JFrame {
	
	private JLabel statusBar, timeBar, radarBar;
	private JPanel labelPanel, gamePanel, buttonsPanel;
	private TimeManager timeManager;
	private JButton button;
	private JMenuBar menuBar;
	private JMenu difficulty;
	private JMenuItem easy, medium, hard, insane;
	private GameFeatures gameType;
	
	public MinesweeperGame() {
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
	
	private void setGamePanel() {
		timeManager = new TimeManager(timeBar);
		gameType = new GameFeatures(10,15,statusBar,timeManager, radarBar);
		timeManager.setGame(gameType);
		
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.add(gameType,BorderLayout.CENTER);
		add(gameType);
	}

	private void setButtonsPanel() {
		buttonsPanel =  new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		button = new JButton("Audio");
		//button.addActionListener(new SameGame(this));
		buttonsPanel.add(button, c);
		
		c.fill = GridBagConstraints.PAGE_START;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		button = new JButton("New Game");
		button.addActionListener(new SameGame(this));
		buttonsPanel.add(button, c);
		
		c.fill = GridBagConstraints.FIRST_LINE_END;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		button = new JButton("Random");
		button.addActionListener(new RandomReveal(this));
		buttonsPanel.add(button, c);
		
		add(buttonsPanel, BorderLayout.NORTH);
	}

	private void setLabelPanel() {
		labelPanel = new JPanel(new BorderLayout());
		
		timeBar = new JLabel();
		labelPanel.add(timeBar,BorderLayout.NORTH);
		
		radarBar = new JLabel("Radars left: 3");
		labelPanel.add(radarBar,BorderLayout.CENTER);
		
		statusBar = new JLabel("");
		labelPanel.add(statusBar, BorderLayout.SOUTH);
        
        add(labelPanel,BorderLayout.SOUTH);
	}
	
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
	
	public GameFeatures getGameType() {
		return gameType;
	}
	
	public void resetGameType(GameType newType) {
		setResizable(true);
		remove(gameType);
		gameType = new GameFeatures(newType.getSize(), newType.getMines(), statusBar, timeManager, radarBar);
		add(gameType);
		timeManager.setGame(gameType);
		timeManager.setInsane(newType.getInsane());
		pack();
		setResizable(false);
	}

	
	//jaxb
	public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MinesweeperGame();
            ex.setVisible(true);
        });
    }
}
