package Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//stores the game type
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
//listener for easy difficulty button
class EasyGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	EasyGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.audioButtons();
		minesweeperGame.resetGameType(new GameType(10,15, false));
	}
}
//listener for medium difficulty button
class MediumGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	MediumGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.audioButtons();
		minesweeperGame.resetGameType(new GameType(15,40, false));
	}
}
//listener for hard difficulty button
class HardGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	HardGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.audioButtons();
		minesweeperGame.resetGameType(new GameType(20,80, false));
	}
}
//listener for insane difficulty button
class InsaneGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	InsaneGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.audioButtons();
		minesweeperGame.resetGameType(new GameType(25,200, true));
	}
}
//listener for new game button
class SameGame implements ActionListener{
	MinesweeperGame minesweeperGame;
	SameGame(MinesweeperGame m){
		minesweeperGame = m;
	}
	public void actionPerformed(ActionEvent e) {
		minesweeperGame.audioButtons();
		minesweeperGame.getGameType().generateMines();
	}
}