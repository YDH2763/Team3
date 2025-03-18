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
	public boolean getBlackScore(Score blackScore) {
		return scoreDao.selectBlackScore(blackScore);
	}

	@Override
	public boolean getWhiteScore(Score whiteScore) {
		return scoreDao.selectWhiteScore(whiteScore);
	}

	@Override
	public boolean getTotalScore(Score allScore) {
		return scoreDao.selectTotalScore(allScore);
	}
	
	
}
