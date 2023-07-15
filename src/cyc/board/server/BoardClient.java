package cyc.board.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BoardClient {
	private static Socket clientSocket;
	private static PrintWriter out;
	private static BufferedReader in;
	private static Scanner scanner;

	private static void displayMenu() {
		System.out.println("원하는 작업을 선택해주세요:");
		System.out.println("1. 게시물 작성");
		System.out.println("2. 게시물 조회");
		System.out.println("3. 게시물 수정");
		System.out.println("4. 게시물 삭제");
		System.out.println("5. 종료");
		System.out.print(">> ");
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		try {
			clientSocket = new Socket("localhost", 8080);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("서버에 연결되었습니다.");

			String input;
			do {
				displayMenu();
				input = scanner.nextLine();
//				processInput(input);
			} while (!input.equals("5"));

			System.out.println("프로그램을 종료합니다.");

			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("클라이언트 오류: " + e.getMessage());
		}
	}
}
