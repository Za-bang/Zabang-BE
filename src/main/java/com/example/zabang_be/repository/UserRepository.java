package com.example.zabang_be.repository;

import com.example.zabang_be.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> { }

