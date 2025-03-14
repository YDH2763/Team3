package omok.service;

import java.util.List;

import omok.mode.vo.Room;

public interface RoomService {

	boolean contains(Room room);

	boolean insertRoom(Room room);

	boolean enteredRoom(Room room, Room tmp);
	
	boolean closeRoom(int roomId);

	List<Room> getRoomList();

	int getRoomId(int roomNum);

	Room getRoomNum(int roomNum);






	



}