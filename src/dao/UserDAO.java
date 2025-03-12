package dao;

import org.apache.ibatis.annotations.Param;

import mode.vo.User;

public interface UserDAO {

	User selectStudent(@Param("user")User user);

	boolean insertStudent(@Param("user")User user);

}
