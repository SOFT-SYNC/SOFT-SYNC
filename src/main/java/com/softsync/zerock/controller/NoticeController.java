package com.softsync.zerock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.softsync.zerock.entity.Notice;
import com.softsync.zerock.service.NoticeService;

@Controller
public class NoticeController {
	
    @Autowired
    NoticeService noticeService;
    
	@GetMapping("/noticelist")
	public String notice(Model model) {
		
		System.out.println("[NoticeContoller] getNoticeList()");
		
		List<Notice> notices = noticeService.getAllNotices();
		model.addAttribute("notices", notices); // 모델 속성명 변경
		return "/common/noticeList";
	}
	
	@GetMapping("/read/{boardNo}")
	public String read(@PathVariable("boardNo") Integer boardNo, Model model) {

		Notice notice = noticeService.getNoticeDetail(boardNo);
		model.addAttribute("notice", notice);
		
		return "/common/read";
	}
}
