package omok.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import omok.mode.vo.Chat;

/*
 * Client 클래스를 이용하여
 * 서버와 클라이언트가 문자열을 주고받는 예제
 */

public class Client{

	private Socket s;
	private String id;
	private String pw;
	private final static String EXIT = "q";
	private Scanner sc = new Scanner(System.in);
	
	public Client(Socket s) {
		this.s = s;
	}

	public void connection() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			
			int menu = 0;
			do {
				printLoginMenu();
				
				try{
					menu = sc.nextInt();
					sc.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("[입력이 올바르지 않습니다]");
					sc.nextLine();
					continue;
				}
				
				oos.writeInt(menu);
				oos.flush();
				
				runLoginMenu(menu, ois, oos);
				
			}while(menu != 3);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void runLoginMenu(int menu, ObjectInputStream ois, ObjectOutputStream oos) {
		switch (menu) {
		case 1:
			if(!logIn(oos, ois)) break;
			try {
				int mainMenu = 0;
				do {
					printMenu();
					
					try{
						mainMenu = sc.nextInt();
						sc.nextLine();
					} catch(InputMismatchException e) {
						System.out.println("[입력이 올바르지 않습니다]");
						sc.nextLine();
						continue;
					}
					
					oos.writeInt(mainMenu);
					oos.flush();
					
					runMenu(mainMenu, ois, oos);
					
				}while(mainMenu != 4);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			signIn(oos, ois);
			break;
		default:
			break;
		}
	}

	private boolean logIn(ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			System.out.print("닉네임 : ");
			id = sc.nextLine();
			oos.writeUTF(id);
			oos.flush();
			System.out.print("비밀번호 : ");
			pw = sc.nextLine();
			oos.writeUTF(pw);
			oos.flush();
			
			boolean success = ois.readBoolean();
			
			if(success) {
				System.out.println("\n***** " + id + "님 환영합니다. *****\n");
				return true;
			}
			else {
				System.out.println("아이디 또는 비밀번호를 확인하세요.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void signIn(ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			System.out.print("닉네임 : ");
			id = sc.nextLine();
			oos.writeUTF(id);
			oos.flush();
			
			boolean success = ois.readBoolean();
			if(!success) {
				System.out.println("이미 등록된 닉네임입니다.");
				return;
			}
			System.out.print("비밀번호 : ");
			pw = sc.nextLine();
			oos.writeUTF(pw);
			oos.flush();
			
			success = ois.readBoolean();
			
			if(success) System.out.println("회원가입 되었습니다.");
			else System.out.println("이미 등록된 회원입니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void runMenu(int menu, ObjectInputStream ois, ObjectOutputStream oos) {
		switch (menu) {
		case 1:
			chat(oos, ois);
			break;
		case 2:
			makeRoom(oos, ois);
			break;
		case 3:
			enterRoom(oos, ois);
			break;
		case 4:
			
			break;

		default:
			break;
		}
	}

	private void enterRoom(ObjectOutputStream oos, ObjectInputStream ois) {
		// TODO Auto-generated method stub
		try {
			while(true) {
				System.out.print("입장할 방의 번호를 입력하세요: ");
				int roomNum;
				try{
					roomNum = sc.nextInt();
					sc.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("[입력이 올바르지 않습니다]");
					sc.nextLine();
					continue;
				}
				oos.writeInt(roomNum);
				oos.writeUTF(id);
				oos.flush();
				
				if(ois.readBoolean()) {
					//입장 공지 수령
					System.out.println(ois.readUTF());
					//시작 공지 수령
					System.out.println(ois.readUTF());
					//첫 필드 수령
					System.out.println(ois.readUTF());
					boolean blackWin;
					boolean myWin;
					while(true) {
						String blackTurn = ois.readUTF();
						System.out.println(blackTurn);
						String blackstone = ois.readUTF();
						System.out.println(blackstone);
						blackWin = ois.readBoolean();
						oos.writeBoolean(blackWin);
						oos.flush();
						if(blackWin) break;
						
						while(true) {
							System.out.print("좌표 입력:");
							int x = 0, y = 0;
							try {
								x = sc.nextInt();
								y = sc.nextInt();
								sc.nextLine();
							} catch(InputMismatchException e) {
								System.out.println("[입력이 올바르지 않습니다]");
								sc.nextLine();
								continue;
							}
							oos.writeInt(x);
							oos.writeInt(y);
							oos.flush();
							boolean ternEnd = ois.readBoolean();
							if(ternEnd) {
								oos.writeBoolean(ternEnd);
								oos.flush();
								break;
							}
							else {
								oos.writeBoolean(ternEnd);
								oos.flush();
								String reInput = ois.readUTF();
								System.out.println(reInput);
							}
						}
						
						String myField = ois.readUTF();
						System.out.println(myField);
						myWin = ois.readBoolean();
						if(myWin) break;
					}
					String gameOver = ois.readUTF();
					System.out.println(gameOver);
					break;
				} else {
					//입장 불가 공지 수령
					System.out.println(ois.readUTF());
					continue;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void makeRoom(ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			while(true) {
				System.out.print("개설할 방의 번호를 입력하세요: ");
				int roomNum;
				try{
					roomNum = sc.nextInt();
					sc.nextLine();
				} catch(InputMismatchException e) {
					System.out.println("[입력이 올바르지 않습니다]");
					sc.nextLine();
					continue;
				}
				oos.writeInt(roomNum);	
				oos.writeUTF(id);
				oos.flush();
				boolean success = ois.readBoolean();
				if(success) {
					//개설 완료 공지 수령
					System.out.println(ois.readUTF());
					boolean findOther = ois.readBoolean();
					if(findOther) {
						//상대 서버로부터 입장 공지 수령
						System.out.println(ois.readUTF());
						//상대 서버로부터 게임 시작 공지 수령
						System.out.println(ois.readUTF());
						//자신의 서버로 입장을 알림
						oos.writeBoolean(true);
						oos.flush();
						
						/*
						 * 오목 시작
						 */
						// 첫 필드 수령
						System.out.println(ois.readUTF());
						boolean whiteWin;
						boolean myWin;
						while(true) {
							
							while(true) {
								
								System.out.print("좌표 입력:");
								int x = 0, y = 0;
								try {
									x = sc.nextInt();
									y = sc.nextInt();
									sc.nextLine();
								} catch(InputMismatchException e) {
									System.out.println("[입력이 올바르지 않습니다]");
									sc.nextLine();
									continue;
								}
								oos.writeInt(x);
								oos.writeInt(y);
								oos.flush();							//입력 좌표를 서버로 전송
								boolean ternEnd = ois.readBoolean();	//금수 여부 수령
								if(ternEnd) {							//가능한 수일 시
									oos.writeBoolean(ternEnd);
									oos.flush();						//턴 종료 true 전송
									break;								//좌표 입력 종료
								}
								else {									//금수일 시
									oos.writeBoolean(ternEnd);
									oos.flush();						//턴 종료 false 전송
									String reInput = ois.readUTF();		//금수 메세지 수령
									System.out.println(reInput);		//금수 메세지 출력
								}
							}
							
							String myField = ois.readUTF();				//착수가 업데이트 된 필드 수령
							System.out.println(myField);				//필드 출력
							myWin = ois.readBoolean();					//승리 여부 수령
							if(myWin) break;							//승리 시 종료
							String whiteTurn = ois.readUTF();			//상대 턴 시작 메세지 수령
							System.out.println(whiteTurn);				//메세지 출력
							String whitestone = ois.readUTF();			//상대 착수 완료 메세지 수령
							System.out.println(whitestone);				//메세지 출력
							whiteWin = ois.readBoolean();				//상대 승리 여부 수령
							oos.writeBoolean(whiteWin);
							oos.flush();								//상대 승리 여부 서버로 전송
							if(whiteWin) break;							//상대 승리 시 종료
						}
						String gameOver = ois.readUTF();				//게임 종료 메세지 수령
						System.out.println(gameOver);					//메세지 출력
						
					}
					break;
				} else {
					//개설 불가 공지 수령
					System.out.println(ois.readUTF());
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void chat(ObjectOutputStream oos, ObjectInputStream ois) {
		try{
			oos.writeUTF(id);
			oos.flush();
		} catch(Exception e) {
			System.out.println("대기실 입장 중 오류 발생");
			e.printStackTrace();
		}
		sendChat(oos);
		receiveChat(ois);
	}

	private void sendChat(ObjectOutputStream oos) {
		Thread th = new Thread(()->{
			
			try {
				Thread.sleep(10);
				while(true) {
					System.out.print("채팅 입력(종료q): ");
					String chat = sc.nextLine();
					oos.writeObject(new Chat(id, chat));
					oos.flush();
					
					if(chat.equals(EXIT)) break;
				}
				
				System.out.println("[대기실을 나갑니다]");
				
			} catch (Exception e) {
				System.out.println("[송신 중 오류 발생]");
			}
		});
		th.start();
	}

	private void receiveChat (ObjectInputStream ois) {
		try {
			while(true) {
				String s = ois.readUTF();
				if(s.equals(EXIT)) break;
				System.out.println(s);
			}
		} catch (Exception e) {
			System.out.println("[수신 중 오류 발생]");
		}
	}

	private void printLoginMenu() {
		System.out.println("--------메뉴--------");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료하기"); 
		System.out.println("-------------------");
		System.out.print("메뉴 입력: ");
	}
	
	private void printMenu() {
		System.out.println("--------메뉴--------");
		System.out.println("1. 대기실 입장하기");
		System.out.println("2. 오목 게임 방 만들기");
		System.out.println("3. 오목 게임 방 들어가기");
		System.out.println("4. 로그아웃");
		System.out.println("-------------------");
		System.out.print("메뉴 입력: ");
	}
}
