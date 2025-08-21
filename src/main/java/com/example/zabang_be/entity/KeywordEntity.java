package com.example.zabang_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "keywords",
        uniqueConstraints= {
                @UniqueConstraint(  // 여러 컬럼에 대한 Unique 제약
                        // 하나의 roomId에는 하나의 키워드만 저장 가능 (중복 저장 X)
                        name = "keyword_unique_room",
                        columnNames = {"roomid", "keyword"}
                )
        })
@NoArgsConstructor
@Setter
public class KeywordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // keywordId가 자동으로 증가 (AUTO_INREMENT)
    @Column(name = "keywordid")
    private Long keywordId;

    @Column(name = "keyword", nullable = false)  // 키워드가 null이면 쓸 이유가 없음
    private String keyword;
    
    // roomId로 해당 방의 키워드를 조회하기 위해 roomEntity를 외래키로 가져온다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid")    // 가져오는 외래키
    private RoomEntity room;

    public KeywordEntity(String keyword, RoomEntity room) {
        this.keyword = keyword;
        this.room = room;
    }
}
