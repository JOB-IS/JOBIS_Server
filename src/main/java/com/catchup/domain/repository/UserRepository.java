package com.catchup.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.catchup.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
