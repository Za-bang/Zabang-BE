package com.example.zabang_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "userid")
    Long userId;

    @Column(name = "nickname", nullable = false, unique = true)    // 닉네임
    private String nickname;

    @Column(name = "userName", nullable = false)    // 사용자 이름
    private String username;

    @Column(name = "e_Mail", nullable = false)  // 사용자 이메일
    private String email;

}