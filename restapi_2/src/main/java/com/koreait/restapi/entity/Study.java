package com.koreait.restapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 스터디 제목
    private String title;

    // 스터디 설명
    private String description;

    // 모집 인원 수
    private int capacity;

    // 모집 마감일
    private LocalDateTime deadline;

    // 작성 시간
    private LocalDateTime createdAt;

    // 작성자 (회원)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    // 신청자 목록
    @Builder.Default
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyApplication> applications = new ArrayList<>();

    // 유틸 메서드
    public boolean isClosed() {
        return deadline.isBefore(LocalDateTime.now()) || applications.size() >= capacity;
    }
}
