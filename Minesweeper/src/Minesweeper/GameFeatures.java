package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameFeatures extends GridGenerator{
	
	public GameFeatures(int gridSize, int noMines, JLabel statusBar, TimeManager time, JLabel radarBar, AudioManager audio) {
		super(gridSize, noMines, statusBar, radarBar, time, audio);
		addMouseListener(new MinesAdapter());
	}
	
	//randomly checks an uncovered field
	public void randomCheck() {
		int col, row;
		if(!inGame) {
			return;
		}
		Random rand = new Random();
		do {
			row = rand.nextInt(gridSize);
			col = rand.nextInt(gridSize);
		}while(matrix[row][col].isRevealed() || matrix[row][col].isFlag());
		
		matrix[row][col].reveal();
		if(matrix[row][col].isMine()) {
			inGame = false;
			audio.playBomb();
			time.stopTime();
		}
		
		if(matrix[row][col].getType() == 0) {
			audio.playReveal();
			findEmptyCell(row, col);
		}
		repaint();
	}
	
	//shuffles all the fields
	public void shuffle() {
		audio.playShuffle();
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
	
	//used to mark or unmark a field with flag
	private void toMark(int row, int col) {
		if(matrix[row][col].isFlag()){
			++minesLeft;
			matrix[row][col].setFlag();
			String msg = Integer.toString(minesLeft);
            statusBar.setText("Flags left: " + msg);
            audio.playMark();
		}
		else
			if(minesLeft > 0) {
				--minesLeft;
				matrix[row][col].setFlag();
				String msg = Integer.toString(minesLeft);
                statusBar.setText("Flags left: " + msg);
                audio.playMark();
			} else {
				statusBar.setText("No flags left!");
			}
	}
	
	//reveals a field
	private void toReveal(int row, int col) {
		matrix[row][col].reveal();
		if(matrix[row][col].isMine()) {
			inGame = false;
			audio.playBomb();
			time.stopTime();
			return;
		}
		audio.playReveal();
		if(matrix[row][col].getType() == 0) {
			 findEmptyCell(row, col);
		}
	}
	
	//detects mines on a surface up to 3x3 fields
	private void toSearch(int row, int col) {
		audio.playSonar();
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
        statusBar.setText("Flags left: " + Integer.toString(minesLeft));
		--radarsLeft;
		radarBar.setText("Radars left: " + Integer.toString(radarsLeft));
	}
	
	//adds functionality to the mouse buttons
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
								radarBar.setText("No radars left!");
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
//listener for random reveal button
class RandomReveal implements ActionListener{
	MinesweeperGame game;
	RandomReveal(MinesweeperGame game){
		this.game = game;
	}
	public void actionPerformed(ActionEvent e) {
		if(game.getGameType().inGame) {
			game.audioButtons();
			game.getGameType().randomCheck();
		}
	}
}
