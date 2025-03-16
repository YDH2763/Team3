package omok.mode.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Gibo {
	
	private int id;
	private int count;
	private int x,y;
	private int roomId;
	
	public Gibo(int count, int x, int y, int roomId) {
		this.count=count;
		this.x=x;
		this.y=y;
		this.roomId=roomId;
	}
	
	public Gibo() {}

	@Override
	public String toString() {
		return "방아이디: "+roomId+" "+count+"수 "+"("+x+", "+y+")";
	}
	
	

}
