package com.catchup.config.security;

import com.catchup.domain.repository.UserRepository;
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
  public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
    return userRepository.findByNickName(nickName)
        .map(LoginUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
  }

}
