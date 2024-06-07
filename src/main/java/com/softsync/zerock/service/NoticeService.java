package com.softsync.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.Notice;
import com.softsync.zerock.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	NoticeRepository noticeRepository;
	
	public List<Notice> getAllNotices() {
		return noticeRepository.findAll();
	}

	public Notice getNoticeDetail(Integer boardNo) {
		return noticeRepository.findByBoardNo(boardNo);
	}

}
