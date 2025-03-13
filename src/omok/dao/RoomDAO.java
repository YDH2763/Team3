package omok.dao;

import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Room;





public interface RoomDAO {

	Room selectRoom(@Param("room")Room room);

	boolean insertRoom(@Param("room")Room room);

	boolean updateRoom(@Param("room")Room room, @Param("room")Room tmp);

	List<Room> selectRoomList();

	
	

}
    