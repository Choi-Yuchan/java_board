package cyc.board.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import cyc.board.data.BoardDAO;

public class BoardHandler extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private DataOutputStream writer;
    private BoardDAO boardDAO;

    public BoardHandler(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new DataOutputStream(socket.getOutputStream());
            boardDAO = new BoardDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                processRequest(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(String request) {
        try {
            int choice = Integer.parseInt(request);
            switch (choice) {
                case 1:
                    boardDAO.boardInsert();
                    break;
                case 2:
                    sendBoardList();
                    break;
                case 3:
                    modifyBoard();
                    break;
                case 4:
                    deleteBoard();
                    break;
                case 5:
                    closeConnection();
                    break;
                default:
                    writer.writeUTF("잘못된 입력입니다.");
                    break;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBoardList() throws IOException {
        boardDAO.getBoardList();
    }

    private void modifyBoard() throws IOException {
        writer.writeUTF("수정할 게시글 번호를 선택하세요.");
        int select = Integer.parseInt(reader.readLine());
        boardDAO.boardUpdate(select);
    }

    private void deleteBoard() throws IOException {
        writer.writeUTF("삭제할 게시글 번호를 선택하세요.");
        int select = Integer.parseInt(reader.readLine());
        boardDAO.boardDelete(select);
    }

    private void closeConnection() throws IOException {
        socket.close();
        reader.close();
        writer.close();
    }
}
