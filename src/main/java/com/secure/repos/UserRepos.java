package com.secure.repos;

import com.secure.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByNd(String nd);
}
