
package omok.mode.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	
	private int id;
	private String winner;
	private int roomId;
	private String result;
	private Date date;
	
	private Room room;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
//	public Result(String winner, int roomId) {
//		this.winner=winner;	//re_winner
//		this.roomId=roomId;	//re_ro_id
//	}
	
	
	public Result(String winner, String result, Date date) {
		this.winner = winner;
		this.result = result;
		this.date = date;
	}
	
	
	@Override
	public String toString() {
		return "상대: "+ winner + "\n   승패: "+result+"\n   일시: "+simpleDateFormat.format(date) + "\n   방아이디 : " + roomId;
	}
	

}
