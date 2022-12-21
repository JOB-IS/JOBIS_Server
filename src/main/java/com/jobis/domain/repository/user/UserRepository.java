package com.jobis.domain.repository.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jobis.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String oAuthId);

  Optional<User> findByEmail(String email);

  boolean existsByNickName(String nickName);

}
