package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Integer>{

	Notice findByBoardNo(Integer boardNo);

}
