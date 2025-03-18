package omok.service;

import omok.mode.vo.Room;

public interface RoomService {

	boolean containsOpeningRoom(Room room);

	boolean insertRoom(Room room);

	boolean enteredRoom(Room room, Room tmp);
	
	boolean closeRoom(int roomId);

	String getFull(Room room);
}