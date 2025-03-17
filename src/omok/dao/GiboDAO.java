package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Gibo;

public interface GiboDAO {

	boolean insertGibo(@Param("gibo")Gibo gibo);

}
