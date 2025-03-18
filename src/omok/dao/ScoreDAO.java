package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Score;

public interface ScoreDAO {

	boolean selectBlackScore(@Param("blackScore")Score blackScore);

	boolean selectWhiteScore(@Param("whiteScore")Score whiteScore);

	boolean selectTotalScore(@Param("allScore")Score allScore);


}
