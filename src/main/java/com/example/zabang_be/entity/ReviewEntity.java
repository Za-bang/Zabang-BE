package com.example.zabang_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews",
        uniqueConstraints = {
                // 사용자는  해당집에 대한 리뷰를 한번만 작성할 수 있다.
                @UniqueConstraint(
                        name = "review_room_author",
                        columnNames = {"roomid", "author"})
        })
@NoArgsConstructor
@Getter
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // reviewId가 자동으로 증가 (AUTO_INCREMENT)
    @Column(name = "reviewid")
    private Long reviewId;

    // roomId로 review를 조회하기 위해 외래키로 가져옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid", nullable = false)    // 외래키에서 사용하는 컬럼
    private RoomEntity room;

    @Column(name = "grade")
    private double grade;

    // 작성 당시 날짜를 저장하기 위한 LocalDateTime
    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    // 리뷰는 한번만
    @Column(name = "author", nullable = false)
    private String author;

    // 게시물에는 무조건 글이 작성되어 있어야 한다
    @Column(name = "texts", nullable = false)
    @Size(max = 2000)
    private String texts;

    // 게시글에 이미지가 필수는 아님
    @Column(name = "image")
    private String imagePath;

    @Column(name = "keywords")
    private String keyword;

    public ReviewEntity(RoomEntity room, double grade, LocalDateTime date, String author, String texts, String imagePath, String keyword) {
        this.room = room;
        this.grade = grade;
        this.date = date;
        this.author = author;
        this.texts = texts;
        this.imagePath = imagePath;
        this.keyword = keyword;
    }
}
