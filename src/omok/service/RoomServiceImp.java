package omok.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import omok.dao.RoomDAO;
import omok.mode.vo.Room;

public class RoomServiceImp implements RoomService{
	
	private RoomDAO roomDao;
	
	public RoomServiceImp() {
		String resource = "omok/config/mybatis-config.xml";
	      InputStream inputStream;
	      SqlSession session;
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
		
		if(room == null || contains(room)) {
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
	public boolean contains(Room room) {
		Room dbroom = roomDao.selectRoom(room);
			
		if(dbroom != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Room> getRoomList() {
		return roomDao.selectRoomList();
	}


	
	public int getRoomId(int roomNum) {
		Room dbroom = roomDao.selectRoomNum(roomNum);
		
		
		return roomDao.selectRoomId(roomNum);
	}

	@Override
	public Room getRoomNum(int roomNum) {
		return roomDao.selectRoom1(roomNum);
	}

	
}