package omok.mode.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {
	
	private int num;
	private String position;
	private int count;
	private int win;
	private int lose;
	private int draw;
	private String id;
	
	public Score(String id, String position, int count, int win, int lose,int draw) {
		
	}
	
	
}
