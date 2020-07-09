package Minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GridGenerator extends JPanel{
	protected final int IMAGE_SIZE = 24;
	protected final int NO_IMAGES = 13;
	private Image img[];
	protected final JLabel statusBar, radarBar;
	protected int gridSize, noMines, minesLeft, radarsLeft;
	protected Tile[][] matrix;
	protected Boolean inGame, won;
	protected TimeManager time;
	protected AudioManager audio;
	
	public GridGenerator(int gridSize, int noMines, JLabel statusBar, JLabel radarBar, TimeManager time, AudioManager audioManager){
		this.statusBar = statusBar;
		this.radarBar = radarBar;
		this.gridSize = gridSize;
		this.noMines = noMines;
		this.time = time;
		this.audio = audioManager;
		matrix = new Tile[gridSize][gridSize];
		InitUI();
		generateMines();
	}
	
	//sets dimensions of the grid and gets images from files
	private void InitUI(){
		setPreferredSize(new Dimension(IMAGE_SIZE * gridSize + 1, IMAGE_SIZE * gridSize + 1));
		img = new Image[NO_IMAGES];
		for (int i = 0; i < NO_IMAGES; i++) {
            var path = "src/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
	}
	
	//generates the mines for a new game and initializes the labels
	protected void generateMines(){
		inGame = true;
		won = false;
		minesLeft = 0;
		radarsLeft = 3;
		radarBar.setText("Radars left: 3");
		statusBar.setText("Flags left: " + Integer.toString(noMines));
		int col, row;
		for(row = 0; row < gridSize; ++row)
			for(col = 0; col < gridSize; ++col)
				matrix[row][col] = new Tile();

		Random rand = new Random(); 
		while(minesLeft < noMines){
			do {
				row = rand.nextInt(gridSize);
				col = rand.nextInt(gridSize);
			}while(matrix[row][col].isMine());
			
			matrix[row][col].putMine();
			addNeighbors(row, col);
			++minesLeft;
		}
		
		time.resetTime();
		repaint();
	}
	
	//adds value to hints for fields adjacent to mines
	protected void addNeighbors(int row, int col){
		for(int i = -1; i < 2; ++i) {
			for(int j = -1; j < 2; ++j) {
				if(row + i >= 0 && col + j >= 0 && row + i < gridSize && col + j < gridSize) {
					matrix[row + i][col + j].increaseType();
				}
			}
		}
	}
	//recursively reveals all fields adjacent to the empty cell revealed
	protected void findEmptyCell(int row, int col) {
		for(int i = -1; i < 2; ++i) {
			for(int j = -1; j < 2; ++j) {
				if(row + i >= 0 && col + j >= 0 && row + i < gridSize && col + j < gridSize && !matrix[row + i][col + j].isRevealed()){
					matrix[row + i][col + j].reveal();
					if(matrix[row + i][col + j].isFlag()) {
						matrix[row + i][col + j].setFlag();
						++minesLeft;
						String msg = Integer.toString(minesLeft);
                        statusBar.setText(msg);
					}
					if(matrix[row + i][col + j].getType() == 0) {
						findEmptyCell(row + i, col + j);
					}
				}
			}
		}
	}
	
	//used to repaint the UI for the fields at every change
	public void paintComponent(Graphics g) {
		int toUncover = 0;
		for(int row = 0; row < gridSize; ++row) {
			for(int col = 0; col < gridSize; ++col) {
				if (inGame && matrix[row][col].isMine() && matrix[row][col].isRevealed() && !matrix[row][col].isFlag()) {
	                   inGame = false;
	                   time.stopTime();
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
            statusBar.setText("YOU WON!");
            time.stopTime();
            audio.playWin();
        }
		else if (!inGame && !won) {
            statusBar.setText("YOU LOST!");
        }
	}
}
