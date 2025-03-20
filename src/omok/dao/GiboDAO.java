package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Gibo;

public interface GiboDAO {

	boolean insertGibo(@Param("gibo")Gibo gibo);

	int getX(@Param("giboCount")int giboCount, @Param("roomId")int roomId);

	int getY(@Param("giboCount")int giboCount, @Param("roomId")int roomId);

	int getMaxCount(@Param("roomId")int roomId);

}
