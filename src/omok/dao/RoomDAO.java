package omok.dao;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Room;


public interface RoomDAO {

	Room selectOpeningRoom(@Param("room")Room room);

	boolean insertRoom(@Param("room")Room room);

	boolean enteredRoom(@Param("room")Room room, @Param("tmp")Room tmp);

	boolean closeRoom(@Param("roomId")int roomId);

	String getFull(@Param("room")Room room);
}
    