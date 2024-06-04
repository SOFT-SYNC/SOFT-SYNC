package com.softsync.zerock.DTO;
 
public class NoticeDTO {
	
	private Integer boardNo;
	private String writer;
	private String title;
	private String content; 
	
	
	public NoticeDTO() {
		
	}


	public NoticeDTO(Integer boardNo, String writer, String title, String content) {
		super();
		this.boardNo = boardNo;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}


}
