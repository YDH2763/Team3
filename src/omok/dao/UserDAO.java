package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.User;

public interface UserDAO {

	User selectUser(@Param("user")User user);
	
	String checkId(@Param("user")User user);

	boolean insertUser(@Param("user")User user);

	void setOnline(@Param("userName")String userName);

	String getOnline(@Param("userName")String userName);

	void setOffline(@Param("userName")String userName);
}
