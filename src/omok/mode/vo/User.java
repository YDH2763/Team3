package omok.mode.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2335537313541679887L;
	
	private String id, pw, del;
}
