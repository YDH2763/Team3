package omok.dao;

import org.apache.ibatis.annotations.Param;

public interface ResultDAO {
	
	boolean insertResult(@Param("winner")String winner, @Param("roomId")int id);
	
}
