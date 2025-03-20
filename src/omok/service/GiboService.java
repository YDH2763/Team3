package omok.service;

import java.util.List;

import omok.mode.vo.Gibo;

public interface GiboService {

	boolean insertGibo(Gibo gibo);

	List<Gibo> getGiboList(int s);
	
	
}
