package omok.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Gibo;

public interface GiboDAO {

	boolean insertGibo(@Param("gibo")Gibo gibo);

	List<Gibo> selectGiboList(@Param("s")int s);

	

}
