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
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                break;
        }
    }

    private void createPost() throws IOException {
        String title = in.readLine();
        String content = in.readLine();
        String author = in.readLine();

        int postId = server.generateId();
        Post post = new Post(postId, title, content, author);
        server.addPost(post);

        out.println("게시물이 작성되었습니다.");
    }

    private void viewPosts() {
        StringBuilder response = new StringBuilder();
        for (Post post : server.getPosts()) {
            response.append("==========================\n")
                    .append(post.toString())
                    .append("\n==========================\n");
        }

        out.println(response.toString());
    }

    private void updatePost() throws IOException {
        int postId = Integer.parseInt(in.readLine());

        Post targetPost = server.findPostById(postId);
        if (targetPost == null) {
            out.println("해당 ID의 게시물을 찾을 수 없습니다.");
            return;
        }

        String title = in.readLine();
        String content = in.readLine();

        targetPost.setTitle(title);
        targetPost.setContent(content);

        out.println("게시물이 수정되었습니다.");
    }

    private void deletePost() throws IOException {
        int postId = Integer.parseInt(in.readLine());

        Post targetPost = server.findPostById(postId);
        if (targetPost == null) {
            out.println("해당 ID의 게시물을 찾을 수 없습니다.");
            return;
        }

        server.getPosts().remove(targetPost);

        out.println("게시물이 삭제되었습니다.");
    }
}
