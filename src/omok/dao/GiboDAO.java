package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Gibo;

public interface GiboDAO {

	Gibo insertGibo(@Param("gibo")Gibo gibo);

}
