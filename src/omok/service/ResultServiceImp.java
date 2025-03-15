package omok.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import omok.dao.ResultDAO;

public class ResultServiceImp implements ResultService {
	
	private ResultDAO resultDao;
	
	public ResultServiceImp() {
		String resource = "omok/config/mybatis-config.xml";
	      InputStream inputStream;
	      SqlSession session;
	      try {
	    	  inputStream = Resources.getResourceAsStream(resource);
	    	  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    	  session = sessionFactory.openSession(true);
	    	  resultDao = session.getMapper(ResultDAO.class);
	      } catch (IOException e) {
	    	  e.printStackTrace();
	      }
	}

	@Override
	public boolean insertResult(String winner, int id) {
		
		return resultDao.insertResult(winner, id);
	}
}
