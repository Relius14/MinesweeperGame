package Minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GridGenerator extends JPanel{
	
	private final int IMAGE_SIZE = 24;
	private final int NO_IMAGES = 13;
	private Image img[];
	private final JLabel statusbar, timerbar;
	protected int height, lenght, noMines, minesLeft, i;
	protected Tile[][] matrix;
	private Boolean inGame, won;
	private Timer time;
	
	public GridGenerator(int height, int lenght, int noMines, JLabel statusbar, JLabel timer){
		this.statusbar = statusbar;
		this.height = height;
		this.lenght = lenght;
		this.noMines = noMines;
		this.timerbar = timer;
		matrix = new Tile[height][lenght];
		
		InitGrid();
	}
	
	private void InitGrid(){
		setPreferredSize(new Dimension(IMAGE_SIZE * lenght + 1, IMAGE_SIZE * height + 1));
		img = new Image[NO_IMAGES];
		for (int i = 0; i < NO_IMAGES; i++) {
            var path = "src/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
		
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerbar.setText(Integer.toString(i / 60)+ " : "+ Integer.toString(i % 60));
				++i;
			}
		});
		
		addMouseListener(new MinesAdapter());
		generateMines();
	}
	
	private void generateMines(){
		inGame = true;
		won = false;
		minesLeft = i = 0;
		
		statusbar.setText("Flags left: " + Integer.toString(noMines));
		for(int row = 0; row < height; ++row)
			for(int col = 0; col < lenght; ++col)
				matrix[row][col] = new Tile();
		
		int col, row;
		while(minesLeft < noMines){
			Random rand = new Random(); 
			row = rand.nextInt(height);
			col = rand.nextInt(lenght);
			
			if(!matrix[row][col].isMine()){
				matrix[row][col].putMine();
				addNeighbors(row, col);
				++minesLeft;
			}
		}
		time.restart();
		
	}
	
	public void addNeighbors(int row, int col){
		for(int i = -1; i < 2; ++i)
			for(int j = -1; j < 2; ++j)
				if(row + i >= 0 && col + j >= 0 && row + i < height && col + j < lenght)
					matrix[row + i][col + j].increaseType();
	}
	
	private void findEmptyCell(int row, int col) {
		for(int i = -1; i < 2; ++i) {
			for(int j = -1; j < 2; ++j) {
				if(row + i >= 0 && col + j >= 0 && row + i < height && col + j < lenght && !matrix[row + i][col + j].isRevealed()){
					matrix[row + i][col + j].reveal();
					
					if(matrix[row + i][col + j].isFlag()) {
						matrix[row + i][col + j].setFlag();
						++minesLeft;
						String msg = Integer.toString(minesLeft);
                        statusbar.setText(msg);
					}
					if(matrix[row + i][col + j].getType() == 0)
						findEmptyCell(row + i, col + j);
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		int uncover = 0;
		
		for(int row = 0; row < height; ++row) {
			for(int col = 0; col < lenght; ++col) {
				if (inGame && matrix[row][col].isMine() && matrix[row][col].isRevealed() && !matrix[row][col].isFlag()) {
	                   inGame = false;
	            }
				if (!inGame) {
					if(matrix[row][col].isMine() ||  matrix[row][col].isFlag())
						matrix[row][col].reveal();
				}
				if (!matrix[row][col].isRevealed()) {
					++uncover;
	            }
				g.drawImage(img[matrix[row][col].getType()], col * IMAGE_SIZE, row * IMAGE_SIZE, this);
			}
		}
		if (inGame && uncover == noMines) {
            inGame = false;
            won = true;
            statusbar.setText("YOU WON!");
            time.stop();
        }
		else if (!inGame && !won) {
            statusbar.setText("YOU LOST!");
            time.stop();
        }
	}
	 
	private class MinesAdapter extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			
			int col = e.getX()/IMAGE_SIZE;
			int row = e.getY()/IMAGE_SIZE;
			Boolean toRepaint = false;
			
			if(col < lenght && row < height && inGame) {
				if(e.getButton() == MouseEvent.BUTTON3 && !matrix[row][col].isRevealed()) {
					toRepaint = true;

					if(matrix[row][col].isFlag()){
						++minesLeft;
						matrix[row][col].setFlag();
						String msg = Integer.toString(minesLeft);
                        statusbar.setText("Flags left: " + msg);
					}
					else
						if(minesLeft > 0) {
							--minesLeft;
							matrix[row][col].setFlag();
							String msg = Integer.toString(minesLeft);
                            statusbar.setText("Flags left: " + msg);
						} else {
							statusbar.setText("No flags left!");
						}
				}
				else if(e.getButton() == MouseEvent.BUTTON1 && !matrix[row][col].isFlag() && !matrix[row][col].isRevealed()) {
					toRepaint = true;
					
					matrix[row][col].reveal();
					if(matrix[row][col].isMine()) {
						inGame = false;
					}
					if(matrix[row][col].getType() == 0) {
						 findEmptyCell(row, col);
					}
				}
				if(toRepaint) {
					repaint();
				}
			}
			else if (!inGame) {
                generateMines();
                repaint();
            }
		}
	}
}
