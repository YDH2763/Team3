package service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import dao.ScoreDAO;
import dao.UserDAO;
import mode.vo.Chat;
import mode.vo.Room;
import mode.vo.User;

public class Server {

   //클라이언트들에게 메세지를 보내기 위한 리스트
   private static List<ObjectOutputStream> list =
         new ArrayList<ObjectOutputStream>();
   private static List<ObjectOutputStream> chatList =
         new ArrayList<ObjectOutputStream>();
   private static List<Room> roomList = new ArrayList<Room>();
   private Socket s;
   private final static String EXIT = "q";
   private UserDAO userDao;
   
   public Server(Socket s) {
      this.s = s;
      String resource = "config/mybatis-config.xml";
      InputStream inputStream;
      SqlSession session;
      try {
    	  inputStream = Resources.getResourceAsStream(resource);
    	  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    	  session = sessionFactory.openSession(true);
    	  userDao = session.getMapper(UserDAO.class);
      } catch (IOException e) {
    	  e.printStackTrace();
      }
   }

   
   public void connected() {
      Thread th = new Thread(()->{
         ObjectOutputStream oos = null;
         ObjectInputStream ois = null;
         try {
            //list에 연결된 클라이언트를 추가
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            list.add(oos); //모든 클라이언트들의 출력스트림
            System.out.println("접속 중 인원: " + list.size());
            while(true) {
               //메뉴를 입력 받음
               int menu = ois.readInt();
               System.out.println("[" + oos + "로그인 메뉴 " + menu + " 입력 받음]");
               //입력받은 메뉴에 맞는 기능을 실행
               runLoginMenu(menu, oos, ois);
            }
            
         } catch (IOException e) {
            list.remove(oos);
            System.out.println("접속 중 인원: " + list.size());
            System.out.println("대기실 인원: " + chatList.size());
            System.out.println("[연결 끊김]");
         } catch (Exception e) {
            System.out.println("로그인 메뉴 수신 중 예기치 못한 오류 발생");
            e.printStackTrace();
         }
      });
      
      th.start();
   }

   private void runLoginMenu(int menu, ObjectOutputStream oos, ObjectInputStream ois) {
      switch (menu) {
      case 1:
         logIn(oos, ois);
         System.out.println(oos + "로그인 진행");
         try {
            while(true) {
               int mainMenu = ois.readInt();
               if(mainMenu == 4) {
                  System.out.println(oos + "메인 메뉴 종료, 로그인 메뉴로 복귀");
                  break;
               }
               System.out.println("[" + oos + "메인 메뉴 " + mainMenu + " 입력 받음]");
               runMenu(mainMenu, oos, ois);
            }
         } catch (Exception e) {
            System.out.println("메인 메뉴 수신 중 예기치 못한 오류 발생");
            e.printStackTrace();
         }
         break;
      case 2:
         signIn(oos, ois);
         System.out.println(oos + "회원가입 진행");
         System.out.println(oos + "회원가입 종료, 로그인 메뉴로 복귀");
         break;
      default:
         break;
      }
   }

   private void logIn(ObjectOutputStream oos, ObjectInputStream ois) {
	   String id;
	   String pw;
	   try {
		   id = ois.readUTF();
		   System.out.println("닉네임 "  + id + " 수신");
		   pw = ois.readUTF();
		   System.out.println("비밀번호 "  + pw + " 수신");
	   } catch (Exception e) {
		   System.out.println("로그인 수신 중 예기치 못한 오류 발생");
	   }
   }

   private void signIn(ObjectOutputStream oos, ObjectInputStream ois) {
	   String id;
	   String pw;
	   try {
		   id = ois.readUTF();
		   System.out.println("닉네임 "  + id + " 수신");
		   pw = ois.readUTF();
		   System.out.println("비밀번호 "  + pw + " 수신");
         
		   User user = new User(id, pw);
		   //생성된 학생 정보를 매니저에게 주면서 등록하라고 요청한 후 성공 여부를 알려달라고 함
		   if(!insertUser(user)) {
			   System.out.println("[중복된 회원]");
			   oos.writeBoolean(false);
			   oos.flush();
			   return;
		   }
		   System.out.println("[회원 등록 성공]");
		   oos.writeBoolean(true);
		   oos.flush();
	   } catch (Exception e) {
		   System.out.println("로그인 수신 중 예기치 못한 오류 발생");
		   e.printStackTrace();
	   }
   }

   private boolean insertUser(User user) {
	   if(user == null) return false;
		
	   //학생 중복 확인
	   if(contains(user)) return false;
		
	   //학생이 중복되지 않으면 학생을 추가
	   return userDao.insertStudent(user);
   }

	private boolean contains(User user) {
		//DB에서 user를 이용하여 학생 정보를 가져옴
		User dbUser = userDao.selectStudent(user);
		
		//DB에서 가져온 학생 정보가 있으면 중복 -> true를 반환
		if(dbUser != null) {
			return true;
		}
		return false;
	}

private void runMenu(int menu, ObjectOutputStream oos, ObjectInputStream ois) {
      switch (menu) {
      case 1:
         chat(oos, ois);
         System.out.println(oos + "채팅 종료, 메뉴로 복귀");
         break;
      case 2:
         makeRoom(oos, ois);
         System.out.println(oos + "방 만들기 종료, 메뉴로 복귀");
         break;
      case 3:
         searchRoom(oos, ois);
         System.out.println(oos + "방 입장 종료, 메뉴로 복귀");
         break;
   
      default:
         break;
      }
   }

   private void searchRoom(ObjectOutputStream oos, ObjectInputStream ois) {
      System.out.println("[들어갈 방 번호 입력 대기 중]"); 
      try {
         while(true) {
            int roomNum = ois.readInt();
            System.out.println("[" + oos + " 입장방 번호 " + roomNum + " 입력 받음]");
            Room tmp = new Room(roomNum, oos, ois);
            if(roomList.isEmpty() || !roomList.contains(tmp)) {
               oos.writeBoolean(false);
               send(oos, "[존재하지 않는 방입니다]");
               continue;
            }
            for (Room room : roomList) {
               if(room.equals(tmp)) {
                  if(!room.isFull()) {
                     room.setClient(oos, ois);
                     for (ObjectOutputStream client : room.getOosList()) {
                        client.writeBoolean(true);
                        if(client == oos) {
                           send(client, "[" + roomNum + "번 방에 입장하였습니다]");
                        } else {
                           send(client, "[상대가 입장하였습니다]");
                        }
                        send(client, "[게임이 시작됩니다]");
                     }
                     System.out.println(roomList);
                     room.gameStart(oos, ois);
                     return;
                  } else {
                     oos.writeBoolean(false);
                     send(oos, "[방의 정원이 가득 찼습니다]");
                  }
               }
            }
         }
      } catch (IOException e) {}
   }

   private void makeRoom(ObjectOutputStream oos, ObjectInputStream ois) {
      System.out.println("[생성할 방 번호 입력 대기 중]");
      try {
         Room room;
         while(true) {
            int roomNum = ois.readInt();
            System.out.println("[" + oos + " 생성방 번호 " + roomNum + " 입력 받음]");
            room = new Room(roomNum, oos, ois);
            boolean exist = roomList.contains(room);
            if(exist) {
               oos.writeBoolean(false);
               send(oos, "[이미 존재하는 방입니다]");
            }
            else {
               oos.writeBoolean(true);
               roomList.add(room);
               System.out.println(roomList);
               send(oos, "[상대를 기다리는 중입니다]");
               if(ois.readBoolean()) {
                  /*
                   * 오목게임 진행
                   */
                  room.gameStart(oos, ois);
                  roomList.remove(room);
                  break;
               } else {
                  send(oos, "[대기를 중단합니다]");
                  roomList.remove(room);
                  break;
               }
            }
         }
         
      } catch (IOException e) {}
   }

   private void chat(ObjectOutputStream oos, ObjectInputStream ois) {
      try{
         String user = ois.readUTF();
         System.out.println("[" + user + "님이 대기실에 입장했습니다]");
         receive(user, oos, ois);
      } catch(Exception e) {
         System.out.println("대기실 입장 중 예기치 못한 오류 발생");
      }
   }

   private void receive(String user, ObjectOutputStream oos, ObjectInputStream ois) {
      try {
         chatList.add(oos);
         System.out.println("접속 중 인원: " + list.size());
         System.out.println("대기실 인원: " + chatList.size());
         synchronized(chatList) {
            for (ObjectOutputStream client : chatList) {
               //메세지를 쓴 클라이언트에겐 메세지를 보내지 않음
               if(client == oos) {
                  send(client, "[대기실에 입장했습니다"
                        + "(현재 대기실 인원" + chatList.size() + "명)]");
               } else {
                  send(client, "[" + user + "님이 대기실에 입장했습니다]");
               }
            }            
         }
         //무한루프로 반복
         while(true) {
            Chat c = (Chat)ois.readObject();
            
            System.out.println(c); // 확인용
            
            boolean flag = false;
            synchronized (chatList) {
               for (ObjectOutputStream client : chatList) {
                  //메세지를 쓴 클라이언트에겐 메세지를 보내지 않음
                  if(client != oos && !c.getChat().equals(EXIT)) {
                     send(client, c.toString());
                  } 
                  //연결된 클라이언트가 EXIT를 입력했을 때
                  else if(c.getChat().equals(EXIT)) {
                     if(client == oos) {
                        //클라이언트의 receive에 있는 쓰레드를 종료하기 위해서
                        send(client, EXIT);
                        flag = true;
                     } else {
                        send(client, "[" + c.getId() + "님이 대기실을 나갔습니다]");
                     }
                  }
               }
            }
            if(flag) {
               chatList.remove(oos);
               System.out.println("접속 중 인원: " + list.size());
               System.out.println("대기실 인원: " + chatList.size());
               break;
            }
         }
      } catch (IOException e) {
         chatList.remove(oos);
         synchronized(chatList) {
            for (ObjectOutputStream client : chatList) {
               //대기실 잔여 인원에게만 전송
               if(client != oos) {
                  send(client, "[" + user + "님이 대기실에서 나갔습니다]");
               }
            }            
         }
      } catch (Exception e) {
         System.out.println("대기실 수신 중 예기치 못한 오류 발생");
      }
   }

   private void send(ObjectOutputStream oos, String s) {
      if(oos == null || s == null) {
         return;
      }
      try {
         /*
          * 하나의 클라이언트에 여러 메세지를 보내려 하면
          * 순서가 꼬이거나 예상치못한 문제 발생 가능성 있음
          * 동기화를 통해 먼저 들어온 작업부터 실행되도록 해줌
          */
         synchronized(oos) {
            oos.writeUTF(s);
            oos.flush();
         }
         
      } catch (Exception e) {
         System.out.println("메세지 송신 중 예기치 못한 오류 발생");
      }
   }
   
   private void send(ObjectOutputStream oos, Object obj) {
      if(oos == null || obj == null) {
         return;
      }
      try {
            oos.writeObject(obj);
            oos.flush();
      } catch (Exception e) {
         System.out.println("Object 송신 중 예기치 못한 오류 발생");
      }
   }
}