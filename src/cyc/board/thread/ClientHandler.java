package cyc.board.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import cyc.board.crud.Post;
import cyc.board.server.BoardServer;

public class ClientHandler extends Thread {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private BoardServer server;

	public ClientHandler(Socket socket, BoardServer server) {
		this.clientSocket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String input;
			while ((input = in.readLine()) != null) {
				processInput(input);
			}

			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("클라이언트 핸들러 오류: " + e.getMessage());
		}
	}

	public void processInput(String input) throws IOException {
		switch (input) {
		case "1":
			createPost();
			break;
		case "2":
			viewPosts();
			break;
		case "3":
			updatePost();
			break;
		case "4":
			deletePost();
			break;
		default:
			out.println("잘못된 입력입니다. 다시 입력해주세요.");
			out.flush();
			break;
		}
	}

	private void createPost() throws IOException {
		out.println("게시물 작성을 선택하셨습니다. 제목을 입력하세요:");
		out.flush();
		String title = in.readLine();

		out.println("내용을 입력하세요:");
		out.flush();
		String content = in.readLine();

		out.println("작성자를 입력하세요:");
		out.flush();
		String author = in.readLine();

		int postId = server.generateId();
		Post post = new Post(postId, title, content, author);
		server.addPost(post);

		out.println("게시물이 작성되었습니다.");
		out.flush();
	}

	private void viewPosts() {
		StringBuilder response = new StringBuilder();
		response.append("게시물 목록\n");
		response.append("==========================\n");
		for (Post post : server.getPosts()) {
			response.append("게시물 ID: ").append(post.getId()).append("\t");
			response.append("제목: ").append(post.getTitle()).append("\t");
			response.append("작성자: ").append(post.getAuthor()).append("\t");
			response.append("날짜: ").append(post.getTimestamp());
		}
		response.append("\n==========================\n");

		out.println(response.toString());
		out.flush();
	}

	private void updatePost() throws IOException {
		out.println("게시물 수정을 선택하셨습니다. 수정할 게시물의 ID를 입력하세요:");
		out.flush();
		int postId = Integer.parseInt(in.readLine());

		Post targetPost = server.findPostById(postId);
		if (targetPost == null) {
			out.println("해당 ID의 게시물을 찾을 수 없습니다.");
			out.flush();
			return;
		}

		out.println("새로운 제목을 입력하세요:");
		out.flush();
		String title = in.readLine();

		out.println("새로운 내용을 입력하세요:");
		out.flush();
		String content = in.readLine();

		targetPost.setTitle(title);
		targetPost.setContent(content);

		out.println("게시물이 수정되었습니다.");
		out.flush();
	}

	private void deletePost() throws IOException {
		out.println("게시물 삭제를 선택하셨습니다. 삭제할 게시물의 ID를 입력하세요:");
		out.flush();
		int postId = Integer.parseInt(in.readLine());

		Post targetPost = server.findPostById(postId);
		if (targetPost == null) {
			out.println("해당 ID의 게시물을 찾을 수 없습니다.");
			out.flush();
			return;
		}

		server.getPosts().remove(targetPost);

		out.println("게시물이 삭제되었습니다.");
		out.flush();
	}
}
