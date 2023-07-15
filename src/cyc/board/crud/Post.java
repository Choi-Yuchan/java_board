package cyc.board.crud;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime timestamp;

    public Post(int id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
    }

    

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public LocalDateTime getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}



	@Override
    public String toString() {
        return "게시물 ID: " + id +
                "\n작성자: " + author +
                "\n날짜: " + timestamp +
                "\n제목: " + title +
                "\n내용: " + content;
    }
}