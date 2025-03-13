package omok.service;

import java.util.List;

import omok.mode.vo.Room;

public interface RoomService {

	boolean contains(Room room);

	boolean insertRoom(Room room);

	boolean updateRoom(Room room, Room tmp);

	List<Room> getRoomList();




	



}
