package omok.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import omok.dao.ScoreDAO;
import omok.mode.vo.Score;

public class ScoreServiceImp implements ScoreService{
	
	private ScoreDAO scoreDao;
	
	public ScoreServiceImp() {
		String resource = "omok/config/mybatis-config.xml";
	    InputStream inputStream;
	    SqlSession session;
	    try {
	    	inputStream = Resources.getResourceAsStream(resource);
	    	SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    	session = sessionFactory.openSession(true);
	    	scoreDao = session.getMapper(ScoreDAO.class);
	    }catch(IOException e) {
	    	  e.printStackTrace();
	      }
	}

	@Override
	public Score getBlackScore(String id, String black) {
		return scoreDao.selectBlackScore(id,black);
	}

	@Override
	public Score getWhiteScore(String id, String white) {
		return scoreDao.selectWhiteScore(id,white);
	}

	@Override
	public Score getTotalScore(String id, String white) {
		return scoreDao.selectTotalScore(id,white);
	}

	
	
}
