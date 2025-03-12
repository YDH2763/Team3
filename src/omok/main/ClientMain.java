package omok.main;

import java.net.Socket;
import java.util.Scanner;

import omok.service.Client;

public class ClientMain {

	public static void main(String[] args) {
		int port = 5001;
//		String ip = "192.168.40.74";
		String ip = "127.0.0.1";
		Scanner sc = new Scanner(System.in);
		
		try {
			
			//소켓 생성
			Socket s = new Socket(ip, port);
			
			Client c = new Client(s);
			
			System.out.println("[서버에 접속하였습니다]");
			c.connection();
			
			System.out.println("[종료합니다]");
			
		} catch (Exception e) {
			System.out.println("서버 연결 중 오류 발생");
			e.printStackTrace();
		}
	}
}
