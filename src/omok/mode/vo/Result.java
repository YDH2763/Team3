package omok.mode.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Result {
	
	
	private String winner;
	private int roomId;
	//private Date date;
	
	public Result(String winner, int roomId) {
		this.winner=winner;	//re_winner
		this.roomId=roomId;	//re_ro_id
	}
	
	public Result() {}
	

	@Override
	public String toString() {
		return "게임 번호 : "+roomId+" 이긴 사람 : "+ winner;
	}
	

}
