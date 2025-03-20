package omok.service;

import omok.mode.vo.Gibo;

public interface GiboService {

	boolean insertGibo(Gibo gibo);

	int getX(int giboCount, int roomId);

	int getY(int giboCount, int roomId);

	int getMaxCount(int roomId);
	
	
}
