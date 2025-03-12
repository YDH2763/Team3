package omok.mode.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Chat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7828400043856453623L;
	private String id;
	private String chat;
	
	public Chat(String id, String chat) {
		this.id = id;
		this.chat = chat;
	}

	@Override
	public String toString() {
		return id + " : " + chat;
	}

}