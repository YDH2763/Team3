package omok.mode.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2335537313541679887L;
	
	private String id;
	private String pw;
	private String del;
	
	public User(String id) {
		this.id = id;
	}
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
}
