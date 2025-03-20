package omok.service;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import omok.mode.vo.Chat;
import omok.mode.vo.Result;
import omok.mode.vo.Room;
import omok.mode.vo.Score;
import omok.mode.vo.User;

public class Server {

   //클라이언트들에게 메세지를 보내기 위한 리스트
   private static List<ObjectOutputStream> list =
         new ArrayList<ObjectOutputStream>();
   private static List<ObjectOutputStream> chatList =
         new ArrayList<ObjectOutputStream>();
   private static List<Room> roomList = new ArrayList<Room>();
   private Socket s;
   private final static String EXIT = "q";
   private String id;
   private UserService userService = new UserServiceImp();
   private RoomService roomService = new RoomServiceImp();
   private ResultService resultService = new ResultServiceImp();
   private ScoreService scoreService = new ScoreServiceImp();
   
   
   public Server(Socket s) {
      this.s = s;
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
            System.out.println("[로그인 메뉴 수신 중 예기치 못한 오류 발생]");
            e.printStackTrace();
         }
      });
      
      th.start();
   }

   private void runLoginMenu(int menu, ObjectOutputStream oos, ObjectInputStream ois) {
      switch (menu) {
      case 1:
         if(!logIn(oos, ois)) break;
         try {
            while(true) {
               int mainMenu = ois.readInt();
               if(mainMenu == 5) {
                  System.out.println(oos + "메인 메뉴 종료, 로그인 메뉴로 복귀");
                  break;
               }
               System.out.println("[" + oos + "메인 메뉴 " + mainMenu + " 입력 받음]");
               runMenu(mainMenu, oos, ois);
            }
         } catch (SocketException e) {
             System.out.println("[클라이언트 강제 종료]");
          } catch (EOFException e) {
              System.out.println("[클라이언트 강제 종료]");
           } catch (Exception e) {
            System.out.println("[메인 메뉴 수신 중 예기치 못한 오류 발생]");
            e.printStackTrace();
         }
         break;
      case 2:
         signIn(oos, ois);
         System.out.println(oos + "회원가입 종료, 로그인 메뉴로 복귀");
         break;
      default:
         break;
      }
   }

   private boolean logIn(ObjectOutputStream oos, ObjectInputStream ois) {
	   String pw;
	   try {
		   id = ois.readUTF();
		   System.out.println("닉네임 "  + id + " 수신");
		   pw = ois.readUTF();
		   System.out.println("비밀번호 "  + pw + " 수신");
		   
		   User user = new User(id, pw, "");
		   
		   boolean existsUser = userService.contains(user);
		   
		   if(existsUser) {
			   String online = userService.getOnline(id);
			   if(online.equals("Y")) {
				   oos.writeInt(3);
				   oos.flush();
				   return false;   
			   }
			   oos.writeInt(1);
			   oos.flush();
			   return true;
		   } else {
			   oos.writeInt(2);
			   oos.flush();
			   return false;
		   }
	   } catch (SocketException e) {
           System.out.println("[클라이언트 강제 종료]");
           return false;
        }catch (Exception e) {
		   System.out.println("[로그인 수신 중 예기치 못한 오류 발생]");
		   e.printStackTrace();
		   return false;
	   }
   }

   private void signIn(ObjectOutputStream oos, ObjectInputStream ois) {

	   String pw;
	   try {
		   id = ois.readUTF();
		   System.out.println("닉네임 "  + id + " 수신");
		   
		   User tmp = new User(id, "", "");
		   
		   boolean uniqueId = userService.isNewId(tmp);
		   if(!uniqueId) {
			   System.out.println("[중복된 닉네임]");
			   oos.writeBoolean(false); // success
			   oos.flush();
			   return;
		   } else {
			   System.out.println("[사용가능한 닉네임]");
			   oos.writeBoolean(true);
			   oos.flush();			   
		   }
		   
		   pw = ois.readUTF();
		   System.out.println("비밀번호 "  + pw + " 수신");
         
		   User user = new User(id, pw, "");
		   //생성된 유저 정보를 등록 요청한 후 성공 여부 받아옴
		   
		   boolean insertUser = userService.insertUser(user);
		   if(!insertUser) {
			   System.out.println("[중복된 회원]");
			   oos.writeBoolean(false);
			   oos.flush();
		   } else {
			   System.out.println("[회원 등록 성공]");
			   oos.writeBoolean(true);
			   oos.flush();			   
		   }
	   } catch (Exception e) {
		   System.out.println("로그인 수신 중 예기치 못한 오류 발생");
		   e.printStackTrace();
	   }
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
      case 4:
    	  try {
    		  while(true) {
    			  int resultMenu = ois.readInt();
    			  if(resultMenu == 3) break;
    			  runResultMenu(resultMenu, oos,ois);
    		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
          System.out.println(oos + "전적 보기 종료, 메뉴로 복귀");
          break;
   
      default:
         break;
      }
   }

   private void makeRoom(ObjectOutputStream oos, ObjectInputStream ois) {
      System.out.println("[생성할 방 번호 입력 대기 중]");
      Room room = null;
      try {
         while(true) {
            int roomNum = ois.readInt();
            id = ois.readUTF();
            System.out.println("[" + oos + " 생성방 번호 " + roomNum + " 입력 받음]");
            room = new Room(roomNum, id, null, oos, ois);
            //room1 =
            //룸이 있는지 없는지 확인 => 룸리스트(dbx) => db를 통해서 있는지 없는지 확인 
            boolean exist = roomService.containsOpeningRoom(room);
            //boolean exist = roomList.contains(room);
            
            if(exist) {
               oos.writeBoolean(false);
               send(oos, "[이미 존재하는 방입니다]");
            }
            else {
               oos.writeBoolean(true);
               roomList.add(room);				//리스트에 추가
               roomService.insertRoom(room);	//db에 추가
               System.out.println("개설번호: " + room.getId());
               System.out.println(room.toString());
               send(oos, "[상대를 기다리는 중입니다]");
               if(ois.readBoolean()) {
                  /*
                   * 오목게임 진행
                   */
                  String winner = room.gameStart(roomNum, oos, ois);
                  roomList.remove(room);										//리스트에서 삭제
                  boolean gameOver = roomService.closeRoom(room.getId());		//DB에서 RO_CLOSED를 Y로 설정
                  if(gameOver) System.out.println("[게임이 종료되어 방을 폐쇄합니다]");
                  
                  if(resultService.insertResult(winner, room.getId()))
                	  System.out.println("[결과를 저장합니다]");
                  else System.out.println("[결과 저장에 실패했습니다]");
                  
                  break;   
               } else {
                  send(oos, "[대기를 중단합니다]");
                  roomList.remove(room);										//리스트에서 삭제
                  boolean gameOver = roomService.closeRoom(room.getId());		//DB에서 RO_CLOSED를 Y로 설정
                  if(gameOver) System.out.println("[대기를 중단하여 방을 폐쇄합니다]");
                  break;
               }
            }
         }
         
      } catch (IOException e) {
    	  if(room != null) {
    		  roomList.remove(room);										//리스트에서 삭제
    		  boolean gameOver = roomService.closeRoom(room.getId());		//DB에서 RO_CLOSED를 Y로 설정
    		  if(gameOver) System.out.println("[클라이언트와 연결이 끊겨 방을 폐쇄합니다]");    		  
    	  }
      }
   }


	private void searchRoom(ObjectOutputStream oos, ObjectInputStream ois) {
	      System.out.println("[들어갈 방 번호 입력 대기 중]"); 
	      
	      try {
	         while(true) {
	            int roomNum = ois.readInt();
	            id = ois.readUTF();
	            System.out.println("[" + oos + " 입장방 번호 " + roomNum + " 입력 받음]");
	            Room tmp = new Room(roomNum, null, id, oos, ois);
	                  
	            boolean exist = roomService.containsOpeningRoom(tmp);
	            
	            if(!exist) {
	            	oos.writeBoolean(false);
	                send(oos, "[존재하지 않는 방입니다]");
	                continue;
	            }
	            
	            for (Room room : roomList) {
	               if(room.equals(tmp)) {
	                  if(roomService.getFull(room).equals("N")) {
	                     room.setClient(oos, ois, id);			//리스트에 상대방 추가
	                     roomService.enteredRoom(room, tmp);	//DB에 상대방 추가
	                     for (ObjectOutputStream client : room.getOosList()) {
	                        client.writeBoolean(true);
	                        if(client == oos) {
	                           send(client, "[" + roomNum + "번 방에 입장하였습니다]");
	                           System.out.println(room.toString());
	                        } else {
	                           send(client, "[상대가 입장하였습니다]");
	                        }
	                        
	                        send(client, "[게임이 시작됩니다]");
	                     }
	                     room.gameStart(roomNum, oos, ois);
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
	
	 
	
	
	
	private void runResultMenu(int resultMenu, ObjectOutputStream oos, ObjectInputStream ois) {
		 switch(resultMenu) {
			 case 1:
				 showMyResult(oos, ois);
		         System.out.println(oos + "전적 보기 완료");
		         break;
			 case 2:
				 showMyGibo(oos, ois);
		         System.out.println(oos + "결과 및 기보 보기 완료");
		         break;
		         
		     default:
		    	 break;
			 }
	}


	private void showMyResult(ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			String black="BLACK";
			String white="WHITE";
			//흑전적(승,패,무,승률)
			Score blackScore =
			scoreService.getBlackScore(id,black);
			//백전적(승,패,무,승률)
			Score whiteScore =
			scoreService.getWhiteScore(id,white);
			//전체전적(승,패,무,승률)
			Score allScore=new Score(id,black,
					(blackScore.getCount()+whiteScore.getCount()),
					(blackScore.getWin()+whiteScore.getWin()),
					(blackScore.getLose()+whiteScore.getLose()),
					(blackScore.getDraw()+whiteScore.getDraw()));
			
			oos.writeUTF("흑 전적: " + blackScore.toString());
			oos.writeUTF("백 전적: " + whiteScore.toString());
			oos.writeUTF("전체 전적: " + allScore.toString());
			oos.flush();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}


	private void showMyGibo(ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			//게임 결과를 출력
			int count=1;
			String resultListUTF = "";
			List<Result> resultList=resultService.getResultList(id);
			System.out.println(resultList.size());
			if(resultList.size() == 0) {
				resultListUTF += "플레이 내역이 없습니다.";
				oos.writeUTF(resultListUTF);
				oos.flush();
				return;
			}
			for(Result re : resultList) {
				resultListUTF += count + ". " + re.toString() + "\n";
				count++;
			}
			oos.writeUTF(resultListUTF);
			oos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
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
}