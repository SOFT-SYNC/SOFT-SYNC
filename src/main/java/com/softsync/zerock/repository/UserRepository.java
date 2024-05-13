package com.softsync.zerock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softsync.zerock.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	// 필요한 경우 사용자 정의 쿼리 메소드를 추가할 수 있습니다
    User findByName(String userName);
    
    User findByEmployeeIdAndPassword(String userId, String userPw);
    
    User findByEmployeeId(String userId);

    User findByEmail(String userEmail);
}
