package com.koreait.restapi.service;

import com.koreait.restapi.entity.StudyApplication;
import com.koreait.restapi.entity.Member;
import com.koreait.restapi.entity.Study;
import com.koreait.restapi.repository.MemberRepository;
import com.koreait.restapi.repository.StudyRepository;
import com.koreait.restapi.mapper.StudyApplicationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudyApplicationServiceImpl implements StudyApplicationService {

    private final StudyApplicationMapper applicationMapper;
    private final StudyRepository studyRepository;
    private final MemberRepository memberRepository;

    public StudyApplicationServiceImpl(
            StudyApplicationMapper applicationMapper,
            StudyRepository studyRepository,
            MemberRepository memberRepository
    ) {
        this.applicationMapper = applicationMapper;
        this.studyRepository = studyRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean apply(int memberId, int studyId) {
        // 중복 신청 방지
        if (applicationMapper.findByMemberIdAndStudyId(memberId, studyId) != null) {
            return false;
        }

        // 정원 초과 확인
        Study study = studyRepository.findById((long) studyId).orElse(null);
        if (study == null) return false;

        int appliedCount = applicationMapper.countByStudyId(studyId);
        if (appliedCount >= study.getCapacity()) {
            return false;
        }

        // 신청 등록
        Member member = memberRepository.findById((long) memberId).orElse(null);
        if (member == null) return false;

        StudyApplication application = StudyApplication.builder()
                .study(study)
                .member(member)
                .appliedAt(LocalDateTime.now())
                .build();

        applicationMapper.insert(application);
        return true;
    }

    @Override
    public List<StudyApplication> getMyApplications(int memberId) {
        return applicationMapper.findByMemberId(memberId);
    }
}
