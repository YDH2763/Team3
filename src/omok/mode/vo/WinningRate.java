package omok.mode.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WinningRate {
	private int num;
	private String id;
	private double black;
	private double white;
	private double all;
	
	public WinningRate(String id, double black, double white, double all) {
		this.id=id;
		this.black=black;
		this.white=white;
		this.all=all;
	}
	
}
