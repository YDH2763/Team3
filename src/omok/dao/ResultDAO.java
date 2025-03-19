package omok.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Result;

public interface ResultDAO {
	
	boolean insertResult(@Param("winner")String winner, @Param("roomId")int id);

	List<Result> selectBlackResult(@Param("id")String id);

	List<Result> selectWhiteResult(@Param("id")String id);
	
}
