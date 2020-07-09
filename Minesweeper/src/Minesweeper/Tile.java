package Minesweeper;



import javax.swing.JButton;

@SuppressWarnings("serial")
public class Tile extends JButton{
	private Boolean flag, mine, revealed;
	int tileType;
	
	public Tile(){
		flag = false;
		mine = false;
		revealed = false;
	}
	//sets mine on this tile
	public void putMine() {
		mine = true;
		tileType = 9;
	}
	
	public Boolean isMine(){
		return mine;
	}
	//marks and unmarks this tile
	public void setFlag(){
		flag = !flag;
	}
	public Boolean isFlag() {
		return flag;
	}
	//reveals the current tile
	public void reveal() {
		revealed = !revealed;
	}
	public Boolean isRevealed(){
		return revealed;
	}
	//increase the hint value of the tile
	public void increaseType() {
		if(!mine)
			++tileType;
	}
	//resets tile hint
	public void resetType() {
		if(!mine)
			tileType = 0;
	}
	
	//returns the type of the tile for correct image output
	public int getType() {
		if((flag && !revealed) || (flag && revealed && mine))
			return 11;			//right flag mark
		if(flag && revealed && !mine)
			return 12;			//wrong flag mark
		if(revealed)
			return tileType;	//neighbors mark
		else
			return 10;			//unchecked mark
	}
}
