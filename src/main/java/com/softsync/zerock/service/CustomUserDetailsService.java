package com.softsync.zerock.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.softsync.zerock.entity.User;
import com.softsync.zerock.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	 if (userId == null || userId.trim().isEmpty()) {
             throw new UsernameNotFoundException("User ID is empty");
         }

         System.out.println("Attempting to load user: " + userId);  // 로그 추가

         // 일반 사용자 조회
         User user = userRepository.findByEmployeeId(userId);
         if (user != null) {
             return new org.springframework.security.core.userdetails.User(
                 user.getEmployeeId(),
                 user.getPassword(),
                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
             );
         }

         
      // 두 리포지토리 모두에서 사용자를 찾을 수 없는 경우
         throw new UsernameNotFoundException("User not found with userId: " + userId);       
         
    }
}

