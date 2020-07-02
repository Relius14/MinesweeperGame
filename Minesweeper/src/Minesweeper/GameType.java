package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameType {
	private int size;
	private int noMines;
	private Boolean insane;
	GameType(int size, int noMines, Boolean insane){
		this.size = size;
		this.noMines = noMines;
		this.insane = insane;
	}
	public int getSize() {
		return size;
	}
	public int getMines() {
		return noMines;
	}
	public Boolean getInsane() {
		return insane;
	}
}
class EasyGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	EasyGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.resetGameType(new GameType(10,15, false));
	}
}
class MediumGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	MediumGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.resetGameType(new GameType(15,40, false));
	}
}
class HardGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	HardGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.resetGameType(new GameType(20,80, false));
	}
}
class InsaneGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	InsaneGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.resetGameType(new GameType(25,150, true));
	}
}
class SameGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	SameGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.getGameType().generateMines();
	}
}