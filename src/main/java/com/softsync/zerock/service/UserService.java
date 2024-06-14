package com.softsync.zerock.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.softsync.zerock.PasswordUtil;
import com.softsync.zerock.entity.User;
import com.softsync.zerock.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private EmailService emailService; // 이메일 서비스 추가 가정

	public void registerUser(User user) {
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		// 암호화된 비밀번호를 User 객체에 설정
		user.setPassword(encodedPassword);

		// 데이터베이스에 사용자 정보 저장
		userRepository.save(user);
	}

	public User authenticate(String userId, String userPw) {
		// 사용자 정보 검색
		User user = userRepository.findByEmployeeId(userId);

		// 사용자가 존재하고 비밀번호가 일치하는지 확인
		if (user != null && PasswordUtil.matches(userPw, user.getPassword())) {
			return user; // 인증 성공 시 사용자 반환
		}

		// 인증 실패 시 null 반환
		return null;
	}
	
	public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // 사용자명을 ID로 사용하는 경우
        }
        return null;
    }
    
    @Transactional
    public void updateUser(User updatedUser) {
        String userId = getCurrentUserId(); // 현재 사용자 ID 가져오기
        User user = userRepository.findByEmployeeId(userId);
        
        // 받은 객체에서 가져온 정보로 업데이트
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // 비밀번호 암호화하여 업데이트
        user.setPhone(updatedUser.getPhone());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user); // 변경된 정보 저장
    }
    
    public void deleteUser(String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with ID: " + userId));
        userRepository.delete(user);
    }

    // 임시 비밀번호 발급 및 이메일 발송 기능
    @Transactional
    public boolean recoverAccount(String email) {
        User user = userRepository.findByEmail(email); // 이메일로 사용자 검색 (findByEmail 메소드 추가 필요)
        if (user == null) {
            return false;
        }

        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        // 이메일 발송 로직
        String subject = "Your account recovery details";
        String text = "Dear " + user.getName() + ",\nYour username is " + user.getEmployeeId() + 
                      " and your temporary password is " + tempPassword + 
                      ". Please change your password upon login.";
        emailService.send(user.getEmail(), subject, text);
        return true;
    }	
    

 
    
}
