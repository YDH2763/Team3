package omok.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import omok.mode.vo.Room;


public interface RoomDAO {

	Room selectRoom(@Param("room")Room room);

	boolean insertRoom(@Param("room")Room room);

	boolean enteredRoom(@Param("room")Room room, @Param("tmp")Room tmp);

	boolean closeRoom(@Param("roomId")int roomId);

	List<Room> selectRoomList();

	int selectRoomId(int roomNum);

	Room selectRoom1(int roomNum);

	Room selectRoomNum(int roomNum);
}
    