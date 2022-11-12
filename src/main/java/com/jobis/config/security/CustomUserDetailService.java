package com.jobis.config.security;

import com.jobis.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // TODO nickname 없어서 권한 오류가 생기는지 exception 으로 분기시켜 반환하도록 CustomAuthProvider 생성
    return userRepository.findByEmail(email)
        .map(LoginUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
  }

}
