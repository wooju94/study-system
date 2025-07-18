package com.koreait.restapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신청한 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 신청한 스터디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")  // 실제 테이블 컬럼명과 맞춰야 함
    private Study study;

    private LocalDateTime appliedAt;

    // ✅ MyBatis와 호환을 위한 setter 추가 (타입 일치)
    public void setMemberId(int memberId) {
        this.member = new Member();
        this.member.setId(memberId); // 🔁 여기 Integer로 받는 경우 일치
    }

    public void setStudyId(int studyId) {
        this.study = new Study();
        this.study.setId((long) studyId); // Study는 Long id 유지
    }
}
