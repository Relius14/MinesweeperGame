package Minesweeper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameFeatures extends GridGenerator{

	public GameFeatures(int gridSize, int noMines, JLabel statusbar, TimeManager timer) {
		super(gridSize, noMines, statusbar, timer);
		addMouseListener(new MinesAdapter());
	}
	public void randomCheck() {
		int col, row;
		Random rand = new Random();
		do {
			row = rand.nextInt(gridSize);
			col = rand.nextInt(gridSize);
		}while(matrix[row][col].isRevealed() || matrix[row][col].isFlag());
		
		matrix[row][col].reveal();
		if(matrix[row][col].isMine()) {
			inGame = false;
		}
		
		if(matrix[row][col].getType() == 0) {
			 findEmptyCell(row, col);
		}
		repaint();
	}
	
	public void shuffle() {
		int row1, row2, col1, col2;
		Tile aux = new Tile();
		Random rand = new Random();
		
		for(int i = 0; i < gridSize; ++i) {
			for(int j = 0; j < gridSize; ++j) {
				matrix[i][j].resetType();
			}
		}
		for(int i = 0; i < gridSize * gridSize; ++i) {
			row1 = rand.nextInt(gridSize);
			col1 = rand.nextInt(gridSize);
			row2 = rand.nextInt(gridSize);
			col2 = rand.nextInt(gridSize);
			aux = matrix[row1][col1];
			matrix[row1][col1] = matrix[row2][col2];
			matrix[row2][col2] = aux;
		}
		for(int i = 0; i < gridSize; ++i) {
			for(int j = 0; j < gridSize; ++j) {
				if(matrix[i][j].isMine()) {
					addNeighbors(i,j);
				}
			}
		}
		for(int row = 0; row < gridSize; ++row) {
			for(int col = 0; col < gridSize; ++col) { 
				if(matrix[row][col].isRevealed() && matrix[row][col].getType()==0) {
					findEmptyCell(row,col);
				}
			}
		}
		repaint();
	}
	private void toMark(int row, int col) {
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
	private void toReveal(int row, int col) {
		matrix[row][col].reveal();
		if(matrix[row][col].isMine()) {
			inGame = false;
			time.stopTime();
		}
		if(matrix[row][col].getType() == 0) {
			 findEmptyCell(row, col);
		}
	}
	
	private void toSearch(int row, int col) {
		if(matrix[row][col].isMine()) {
			matrix[row][col].setFlag();
		}
		else {
			for(int i = -1; i < 2; ++i) {
				for(int j = -1; j < 2; ++j) {
					if(row + i >= 0 && col + j >= 0 && row + i < gridSize && col + j < gridSize) {
						if(!matrix[row + i][col + j].isRevealed() && matrix[row + i][col + j].isMine() && !matrix[row + i][col + j].isFlag()) {
							matrix[row + i][col + j].setFlag();
							--minesLeft;
						}
						else {
							if(!matrix[row + i][col + j].isMine() && !matrix[row + i][col + j].isRevealed()) {
								 matrix[row + i][col + j].reveal();
							}
						}
					}
				}
			}
		}
		--radarsLeft;
		statusbar.setText("Radars left: " + Integer.toString(radarsLeft));
	}
	
	public class MinesAdapter extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			
			int col = e.getX()/IMAGE_SIZE;
			int row = e.getY()/IMAGE_SIZE;
			Boolean toRepaint = false;
			
			if(col < gridSize && row < gridSize && inGame) {
				if(e.getButton() == MouseEvent.BUTTON3 && !matrix[row][col].isRevealed()) {
					toRepaint = true;
					toMark(row,col);
				}else 
					if(e.getButton() == MouseEvent.BUTTON1 && !matrix[row][col].isFlag() && !matrix[row][col].isRevealed()) {
						toRepaint = true;
						toReveal(row,col);
					} else { 
						if(e.getButton() == MouseEvent.BUTTON2) {
							if(radarsLeft > 0) {
								toRepaint = true;
								toSearch(row,col);
							}
							else {
								statusbar.setText("No radars left!");
							}
						}
					}
				if(toRepaint) {
					repaint();
				}
			}
		}
	}
}