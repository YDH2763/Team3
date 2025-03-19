package omok.service;

import omok.mode.vo.User;

public interface UserService {
	
	boolean isNewId(User tmp);
	
	boolean insertUser(User user);
	
	boolean contains(User user);

	void setOnline(User user);

	String getOnline(User user);

	void setOffline(User user);
	
}
