package omok.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Result;

public interface ResultDAO {
	
	boolean insertResult(@Param("winner")String winner, @Param("roomId")int id);

	List<Result> selectResultList(@Param("name")String name);
	
}
