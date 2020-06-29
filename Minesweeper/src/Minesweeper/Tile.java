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
	
	public void putMine() {
		mine = true;
		tileType = 9;
	}
	public Boolean isMine(){
		return mine;
	}
	
	public void setFlag(){
		flag = !flag;
	}
	public Boolean isFlag() {
		return flag;
	}
	
	public void reveal() {
		revealed = true;
	}
	public Boolean isRevealed(){
		return revealed;
	}
	
	public void increaseType() {
		if(!mine)
			++tileType;
	}
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
