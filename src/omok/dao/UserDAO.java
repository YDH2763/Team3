package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.User;

public interface UserDAO {

	User selectUser(@Param("user")User user);

	boolean insertUser(@Param("user")User user);

}
