package omok.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import omok.dao.RoomDAO;
import omok.mode.vo.Room;

public class RoomServiceImp implements RoomService{
	
	private RoomDAO roomDao;
	
	SqlSession session;
	public RoomServiceImp() {
		String resource = "omok/config/mybatis-config.xml";
	      InputStream inputStream;
	      try {
	    	  inputStream = Resources.getResourceAsStream(resource);
	    	  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    	  session = sessionFactory.openSession(true);
	    	  roomDao = session.getMapper(RoomDAO.class);
	      } catch (IOException e) {
	    	  e.printStackTrace();
	      }
	}

	@Override
	public boolean insertRoom(Room room) {
		
		if(room == null || containsOpeningRoom(room)) {
			return false;
		}
		return roomDao.insertRoom(room);
	}

	@Override
	public boolean enteredRoom(Room room, Room tmp) {
		if(room == null || tmp == null) {
			return false;
		}
		
		return roomDao.enteredRoom(room, tmp);
	}

	@Override
	public boolean closeRoom(int roomId) {
		return roomDao.closeRoom(roomId);
	}

	@Override
	public boolean containsOpeningRoom(Room room) {
		session.clearCache();
		Room dbroom = roomDao.selectOpeningRoom(room);
			
		if(dbroom != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getFull(Room room) {
		session.clearCache();
		return roomDao.getFull(room);
	};


	
}