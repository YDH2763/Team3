package omok.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import omok.dao.UserDAO;
import omok.mode.vo.User;

public class UserServiceImp implements UserService{

	private UserDAO userDao;
	
	public UserServiceImp() {
		String resource = "omok/config/mybatis-config.xml";
	      InputStream inputStream;
	      SqlSession session;
	      try {
	    	  inputStream = Resources.getResourceAsStream(resource);
	    	  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    	  session = sessionFactory.openSession(true);
	    	  userDao = session.getMapper(UserDAO.class);
	      } catch (IOException e) {
	    	  e.printStackTrace();
	      }
	}
	
	public boolean isNewId(User tmp) {
		   if(tmp == null) return false;
			
		   //닉네임 중복 확인
		   if(contains(tmp)) return false;
			
		   //닉네임 중복이 없으면 true반환
		   return true;
	}

	public boolean insertUser(User user) {
			
		if(user == null) return false;
		
		//유저 중복 확인
		if(contains(user)) return false;
		
		//유저가 중복되지 않으면 유저를 추가
		return userDao.insertUser(user);
	}

	public boolean contains(User user) {
		
		if(user.getPw() == "") {
			//중복된 아이디가 있는지 체크함
			String id = userDao.checkId(user);
			if(id != null) {
				return true;
			}
		}
		//DB에서 user를 이용하여 학생 정보를 가져옴
		User dbUser = userDao.selectUser(user);
		
		//DB에서 가져온 학생 정보가 있으면 중복 -> true를 반환
		if(dbUser != null) {
			return true;
		}
		
		return false;
	}

}
