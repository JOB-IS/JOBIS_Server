package com.catchup.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.catchup.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String oAuthId);
  Optional<User> findByNickName(String nickName);

}
