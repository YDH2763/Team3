package mode.vo;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2335537313541679887L;
	private String id;
	private String pw;
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
}
