package omok.service;

import omok.mode.vo.Score;

public interface ScoreService {

	boolean getBlackScore(Score blackScore);

	boolean getWhiteScore(Score whiteScore);

	boolean getTotalScore(Score allScore);

}
