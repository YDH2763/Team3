package mode.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stone implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8473424601842182858L;
	private int x, y;

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
