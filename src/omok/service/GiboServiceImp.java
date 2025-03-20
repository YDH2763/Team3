package omok.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import omok.dao.GiboDAO;
import omok.mode.vo.Gibo;

public class GiboServiceImp implements GiboService{
	private GiboDAO giboDao;
	
	SqlSession session;
	public GiboServiceImp() {
		String resource = "omok/config/mybatis-config.xml";
	      InputStream inputStream;
	      try {
	    	  inputStream = Resources.getResourceAsStream(resource);
	    	  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    	  session = sessionFactory.openSession(true);
	    	  giboDao = session.getMapper(GiboDAO.class);
	      } catch (IOException e) {
	    	  e.printStackTrace();
	      }
	      
	      
	}

	
	public boolean insertGibo(Gibo gibo) {
		return giboDao.insertGibo(gibo);
	}

}
