package omok.service;

import omok.mode.vo.Score;

public interface ScoreService {

	Score getBlackScore(String id, String black);

	Score getWhiteScore(String id, String white);





}
