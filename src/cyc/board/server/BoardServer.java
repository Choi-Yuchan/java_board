package cyc.board.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cyc.board.crud.Post;
import cyc.board.thread.ClientHandler;

public class BoardServer {
    private List<Post> posts;
    private int nextId;
    private static ClientHandler clientHandler;

    public BoardServer() {
        posts = new ArrayList<>();
        nextId = 1;
        clientHandler = null;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("서버가 시작되었습니다. 클라이언트 연결을 대기 중...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);

                if (clientHandler == null || !clientHandler.isAlive()) {
                    clientHandler = new ClientHandler(clientSocket, this);
                    clientHandler.start();
                }
            }
        } catch (IOException e) {
            System.err.println("서버 오류: " + e.getMessage());
        }
    }

    public synchronized int generateId() {
        return nextId++;
    }

    public synchronized void addPost(Post post) {
        posts.add(post);
    }

    public synchronized List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    public synchronized Post findPostById(int postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        BoardServer server = new BoardServer();
        server.run();
    }
}
