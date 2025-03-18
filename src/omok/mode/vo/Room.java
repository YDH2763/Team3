package omok.mode.vo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import omok.mode.vo.Gibo;
import omok.mode.vo.Result;
import omok.service.GiboService;
import omok.service.GiboServiceImp;
import omok.service.OmokProgram;
import omok.service.ResultService;
import omok.service.ResultServiceImp;
import omok.service.RoomService;
import omok.service.RoomServiceImp;

@Data
@AllArgsConstructor
public class Room {
   
   private int id;
   private int roomNum;
   private String winner;
   private int count=0;
   
   
   private RoomService roomService = new RoomServiceImp();
   private ResultService resultService = new ResultServiceImp();
   private GiboService giboService = new GiboServiceImp();
   
   private List<ObjectOutputStream> oosList = new ArrayList<ObjectOutputStream>();
   private List<ObjectInputStream> oisList = new ArrayList<ObjectInputStream>();
   
   private boolean full = false;
   private boolean allOut = false;
   
   private String bName;
   private String wName;
   
   private OmokProgram omok;
   
   public Room(int roomNum, String bName, String wName, ObjectOutputStream oos, ObjectInputStream ois) {
      this.roomNum = roomNum;
      this.bName= bName;
      this.wName= wName;
      oosList.add(oos);
      oisList.add(ois);
   }
   
   public Room() {}
   
   public void setClient(ObjectOutputStream oos, ObjectInputStream ois, String id) {
      oosList.add(oos);
      oisList.add(ois);
      wName = id;
      if(oosList.size() == 2) full = true;
   }

   public String gameStart(int roomNum, ObjectOutputStream oos, ObjectInputStream ois) {
      ObjectOutputStream player1 = oosList.get(0);
      ObjectOutputStream player2 = oosList.get(1);
      
      omok = new OmokProgram(player1, player2);
      try {
         //첫 필드는 모두에게 보여준다.
         oos.writeUTF(omok.startField);
         oos.flush();
         //선 턴이면 입력을 받고, 프로그램에 넘기고, 후턴의 입력을 기다리고, 필드를 보여준다.
         //후 턴이면 선 턴의 입력을 기다리고, 필드를 보여주고, 입력을 받고, 프로그램에 넘긴다.
         
         while(true) {
            
            if(oos == player2) {
               oos.writeUTF("[흑의 턴이 진행중입니다]");
               oos.flush();
               boolean blackWin = ois.readBoolean();      // 선턴 입력 기다리기
               if(blackWin) break;
               System.out.println("흑의 턴을 받음");
            }
            
            while(true) {
               int x = ois.readInt();
               int y = ois.readInt();
               omok.input(oos, x, y);
               boolean ternEnd = ois.readBoolean();
               if(ternEnd) {
            	   count++;
            	   Gibo gibo=new Gibo(count,x,y,id);
            	   giboService.insertGibo(gibo);
            	   break;
               }
            }
            
            if(omok.gameOver) {
            	break;
            }
            
            if(oos == player1) {
               oos.writeUTF("[백의 턴이 진행중입니다]");
               oos.flush();
               boolean whiteWin = ois.readBoolean();      // 후턴 입력 기다리기
               if(whiteWin) break;
               System.out.println("백의 턴을 받음");   
               
            }
         }
         oos.writeUTF("[" + omok.winner + "이 승리하였습니다]");
         count = 0;
         System.out.println(count);
         oos.flush();
         
         if(omok.winner.equals("흑")) return "BLACK";
         else if(omok.winner.equals("백")) return "WHITE";
         else return "DRAW";
         
         
      } catch(Exception e){
    	  e.printStackTrace();
    	  return "";
      }
   }
   
   
   

@Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Room other = (Room) obj;
      return roomNum == other.roomNum;
   }

   @Override
   public String toString() {
	      return "방번호 : "+roomNum +", 방장 : " + bName +", 유저 : " + wName;
   }
   
   
   
}