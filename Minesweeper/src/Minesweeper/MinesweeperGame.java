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
	
	public MinesweeperGame() {
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
