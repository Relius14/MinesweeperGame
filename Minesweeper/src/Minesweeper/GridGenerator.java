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
	private final JLabel statusbar;
	protected int height, lenght, noMines, minesLeft;
	protected Tile[][] matrix;
	private Boolean inGame, won, insane;
	private Timer time;
	private int radarsLeft;
	
	public GridGenerator(int height, int lenght, int noMines, JLabel statusbar, Timer timer){
		this.statusbar = statusbar;
		this.height = height;
		this.lenght = lenght;
		this.noMines = noMines;
		this.time = timer;
		this.radarsLeft = 3;
		matrix = new Tile[height][lenght];
		insane = true;
		InitGrid();
	}
	
	private void InitGrid(){
		setPreferredSize(new Dimension(IMAGE_SIZE * lenght + 1, IMAGE_SIZE * height + 1));
		img = new Image[NO_IMAGES];
		for (int i = 0; i < NO_IMAGES; i++) {
            var path = "src/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
		
		addMouseListener(new MinesAdapter());
		generateMines();
	}
	
	public void generateMines(){
		inGame = true;
		won = false;
		minesLeft = 0;
		radarsLeft = 3;
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
	}
	public void randomCheck() {
		int col, row;
		Random rand = new Random();
		do {
			row = rand.nextInt(height);
			col = rand.nextInt(lenght);
		}while(matrix[row][col].isRevealed() || matrix[row][col].isFlag());
		
		matrix[row][col].reveal();
		if(matrix[row][col].isMine()) {
			inGame = false;
		}
		if(matrix[row][col].getType() == 0) 
			 findEmptyCell(row, col);
		repaint();
	}
	public void addNeighbors(int row, int col){
		for(int i = -1; i < 2; ++i)
			for(int j = -1; j < 2; ++j)
				if(row + i >= 0 && col + j >= 0 && row + i < height && col + j < lenght)
					matrix[row + i][col + j].increaseType();
	}
	public void shuffle() {
		int row1, row2, col1, col2;
		Tile aux = new Tile();
		Random rand = new Random();
		for(int i = 0; i < height; ++i)
			for(int j = 0; j < lenght; ++j) {
				matrix[i][j].resetType();
			}
		for(int i = 0; i< lenght*height; ++i) {
			row1 = rand.nextInt(height);
			col1 = rand.nextInt(lenght);
			row2 = rand.nextInt(height);
			col2 = rand.nextInt(lenght);
			aux = matrix[row1][col1];
			matrix[row1][col1] = matrix[row2][col2];
			matrix[row2][col2] = aux;
		}
		for(int i = 0; i < height; ++i)
			for(int j = 0; j < lenght; ++j) {
				if(matrix[i][j].isMine())
					addNeighbors(i,j);
			}
		for(int row = 0; row < height; ++row)
			for(int col = 0; col < lenght; ++col) {
				if(matrix[row][col].isRevealed()){
					if(matrix[row][col].getType()==0)
						findEmptyCell(row,col);
					else {
						Boolean ok = true;
						for(int i = -1; i < 2; ++i)
							for(int j = -1; j < 2; ++j)
								if(row + i >= 0 && col + j >= 0 && row + i < height && col + j < lenght)
									if(matrix[row + i][col + j].getType() == 0) {
										ok = false;
									}
						if(ok)
							matrix[row][col].reveal();
					}
				}
			}
		repaint();
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
		int toUncover = 0;
		
		for(int row = 0; row < height; ++row) {
			for(int col = 0; col < lenght; ++col) {
				if (inGame && matrix[row][col].isMine() && matrix[row][col].isRevealed() && !matrix[row][col].isFlag()) {
	                   inGame = false;
	            }
				if (!inGame) {
					if((matrix[row][col].isMine() ||  matrix[row][col].isFlag())&& !matrix[row][col].isRevealed())
						matrix[row][col].reveal();
				}
				if (!matrix[row][col].isRevealed()) {
					++toUncover;
	            }
				g.drawImage(img[matrix[row][col].getType()], col * IMAGE_SIZE, row * IMAGE_SIZE, this);
			}
		}
		if (inGame && toUncover == noMines) {
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
				} else if(e.getButton() == MouseEvent.BUTTON2) {
					if(radarsLeft > 0) {
						toRepaint = true;
						if(matrix[row][col].isMine())
							matrix[row][col].setFlag();
						else {
							for(int i = -1; i < 2; ++i)
								for(int j = -1; j < 2; ++j)
									if(row + i >= 0 && col + j >= 0 && row + i < height && col + j < lenght)
										if(!matrix[row + i][col + j].isRevealed() && matrix[row + i][col + j].isMine() && !matrix[row + i][col + j].isFlag()) {
											matrix[row + i][col + j].setFlag();
											--minesLeft;
										}
										else
											if(!matrix[row + i][col + j].isMine() && !matrix[row + i][col + j].isRevealed())
												 matrix[row + i][col + j].reveal();
						}
						--radarsLeft;
						statusbar.setText("Radars left: " + Integer.toString(radarsLeft));
					}
					else {
						  statusbar.setText("No radars left!");
					}
						
				}
				if(toRepaint) {
					repaint();
				}
			}
		}
	}
}
