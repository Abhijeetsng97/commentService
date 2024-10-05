package com.abhijeet.commentsService.repository;

import com.abhijeet.commentsService.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
