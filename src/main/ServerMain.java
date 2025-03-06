package main;

import java.net.ServerSocket;
import java.net.Socket;

import service.Server;

public class ServerMain {

	public static void main(String[] args) {
		
		int port = 5001;
		
		try {
			//서버 소켓 생성
			ServerSocket ss = new ServerSocket(port);
			
			System.out.println("[Server Open]");
			
			while(true) {
				//연결 대기, 요청 수락 후 소켓 객체 생성
				
				Socket s = ss.accept(); 
				
				System.out.println("[연결 성공!]");
				
				Server server = new Server(s);
				
				server.connected();
			}
			
		} catch (Exception e) {
			System.out.println("클라이언트 연결 중 오류 발생");
			e.printStackTrace();
		}
	}

}
