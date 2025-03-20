package omok.mode.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3254412008636174922L;
	private int num;
	private String position;
	private int count;
	private int win;
	private int lose;
	private int draw;
	private String id;
	
	public Score(String id, String position, int count, int win, int lose,int draw) {
		this.id=id;
		this.position=position;
		this.count=count;
		this.win=win;
		this.lose=lose;
		this.draw=draw;
	}
	
	
	public Score() {}

	
	@Override
	public String toString() {
		double rate = ((double)win/(double)count)*100;
		double roundedRate = Math.round(rate * 100) / 100.0;
		return count+"전 "+win+"승 "+lose+"패 "+draw+"무 승률 : "+roundedRate+"%";
	}
	
	
	
	
	
}
