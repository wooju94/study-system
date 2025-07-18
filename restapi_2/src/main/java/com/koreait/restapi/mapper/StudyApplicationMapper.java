package com.koreait.restapi.mapper;

import com.koreait.restapi.entity.StudyApplication;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyApplicationMapper {
    void insert(StudyApplication application);

    int countByStudyId(int studyId);  // 기존 countByBoardId → countByStudyId 로 변경

    StudyApplication findByMemberIdAndStudyId(int memberId, int studyId);  //  추가 또는 수정

    List<StudyApplication> findByMemberId(int memberId);
}

