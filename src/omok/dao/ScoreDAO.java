package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Score;

public interface ScoreDAO {

	Score selectBlackScore(@Param("id")String id, @Param("black")String black);

	Score selectWhiteScore(@Param("id")String id, @Param("white")String white);



}
