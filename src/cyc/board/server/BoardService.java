package cyc.board.server;

import java.util.Scanner;

import cyc.board.data.BoardDAO;

public class BoardService {
	public static void main(String[] args) throws Exception {
		Scanner scanner = null;
		scanner = new Scanner(System.in);
		BoardDAO dao = new BoardDAO();

		while (true) {
			dao.getBoardList();
			System.out.println("번호를 입력해주세요. >>");
			int answer = Integer.parseInt(scanner.nextLine());

			if (answer == 1) {
				dao.boardInsert();
			} else if (answer == 2) {
				System.out.println("보고 싶은 게시글 번호를 선택하세요. >>");
				int select = Integer.parseInt(scanner.nextLine());
				dao.boardDetail(select);
			} else if (answer == 3) {
				System.out.println("수정할 게시글 번호를 선택하세요. >>");
				int select = Integer.parseInt(scanner.nextLine());
				dao.boardUpdate(select);
			} else if (answer == 4) {
				System.out.println("삭제할 게시글 번호를 선택하세요. >>");
				int select = Integer.parseInt(scanner.nextLine());
				dao.boardDelete(select);
			} else if (answer == 5) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
		System.out.println("프로그램이 종료되었습니다.");
		scanner.close();
	}
}
