package omok.service;

import omok.mode.vo.User;

public interface UserService {
	
	boolean isNewId(User tmp);
	
	boolean insertUser(User user);
	
	boolean contains(User user);

	void setOnline(String userName);

	String getOnline(String userName);

	void setOffline(String userName);
	
}
