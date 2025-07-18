package com.koreait.restapi.service;

import com.koreait.restapi.entity.StudyApplication;

import java.util.List;

public interface StudyApplicationService {
    boolean apply(int memberId, int boardId);
    List<StudyApplication> getMyApplications(int memberId);
}
