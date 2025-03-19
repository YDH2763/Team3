package omok.service;

import java.util.List;

import omok.mode.vo.Result;

public interface ResultService {

	boolean insertResult(String winner, int id);

	List<Result> getBlackResult(String id);

	List<Result> getWhiteResult(String id);

}
