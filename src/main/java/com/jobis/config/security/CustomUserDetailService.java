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
  public UserDetails loadUserByUsername(String oauthId) throws UsernameNotFoundException {
    return userRepository.findByOauthIdAndActivated(oauthId, true)
        .map(LoginUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
  }

}
