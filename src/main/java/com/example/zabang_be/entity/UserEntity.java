package com.example.zabang_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserEntity {
    @Id
    @Column(name = "userid")
    Long userId;

    @Column(name = "nickname", nullable = false, unique = true)    // 닉네임
    private String nickName;

    @Column(name = "userName", nullable = false)    // 사용자 이름
    private String userName;

    @Column(name = "e_Mail", nullable = false)  // 사용자 이메일
    private String e_Mail;

    public UserEntity(String nickName, String userName, String e_Mail) {
        this.nickName = nickName;
        this.userName = userName;
        this.e_Mail = e_Mail;
    }
}