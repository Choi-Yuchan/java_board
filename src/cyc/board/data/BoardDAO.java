package cyc.board.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BoardDAO {
	Scanner scanner = new Scanner(System.in);
	List<Post> boardList = new ArrayList<>();

	public void getBoardList() {
		System.out.println("[  게시판  ]");
		System.out.println("번호    제목     작성자    작성일");
		System.out.println("============================================");
		if (boardList.isEmpty()) {
			System.out.println("게시글이 없습니다.");
		} else {
			for (Post list : boardList) {
				System.out.printf(" %-3d| %-15s | %3s | %-1s\n", boardList.indexOf(list) + 1, list.getTitle(),
						list.getAuthor(), list.getRegisterDate());
			}
		}
		System.out.println("============================================");
		System.out.println("1. 새글 작성 2. 게시글 보기 3. 수정 4. 삭제 5. 종료 ");
	}
	
	public void boardInsert() {
		Post board = new Post();
		
		System.out.println("글제목: ");
		String title = scanner.nextLine();
		
		System.out.println("작성자: ");
		String author = scanner.nextLine();
		
		System.out.println("내용: ");
		String content = scanner.nextLine();
		
		board.setId(boardList.indexOf(board));
		board.setTitle(title);
		board.setAuthor(author);
		board.setContent(content);
		
		//현재 날짜 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String registerDate = sdf.format(new Date());
		board.setRegisterDate(registerDate);
		
		boardList.add(board);
		System.out.println("새 글을 작성하였습니다.");
		;
	}
	
	public void boardDetail(int select) {
		if(boardList.isEmpty()) {
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}
		Post board = new Post();
		board = boardList.get(select-1);
		System.out.println("No." + select);
		System.out.println("제목 : " + board.getTitle());
		System.out.println("작성자 : " + board.getAuthor());
		System.out.println("===========================================");
		System.out.println(board.getContent());
		System.out.println("===========================================");
		System.out.println("1.수정  2.삭제  3.목록  ");

		select = Integer.parseInt(scanner.nextLine());
		if(select == 1) {
			boardUpdate(boardList.indexOf(board)+1);
		} else if (select == 2) {
			boardDelete(boardList.indexOf(board)+1);
		} else if (select == 3) {
			return;
		}
	}
	
	public void boardUpdate(int select) {
		if(boardList.isEmpty()) {
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}
		
		Post board = new Post();
		board = boardList.get(select-1);
		
		System.out.println("글제목: ");
		String title = scanner.nextLine();
		
		System.out.println("작성자: ");
		String author = scanner.nextLine();
		
		System.out.println("내용: ");
		String content = scanner.nextLine();
		
		board.setId(boardList.indexOf(board));
		board.setTitle(title);
		board.setAuthor(author);
		board.setContent(content);
		
		//현재 날짜 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String registerDate = sdf.format(new Date());
		board.setRegisterDate(registerDate);
		
		boardList.set(boardList.indexOf(board), board);
		System.out.println("글이 수정되었습니다.");
	}
	
	public void boardDelete(int select) {
		if(boardList.isEmpty()) {
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}
		Post board = new Post();
		board = boardList.get(select-1);
		boardList.remove(boardList.indexOf(board));
		System.out.println("글이 삭제되었습니다.");
	}
}
