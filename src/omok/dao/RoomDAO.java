package omok.dao;

import java.io.ObjectOutputStream;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Room;





public interface RoomDAO {

	Room selectRoom(@Param("room")Room room);

	

	boolean insertRoom(@Param("room")int roomNum, @Param("room")ObjectOutputStream player1, @Param("room")ObjectOutputStream player2);

	
	

}
    