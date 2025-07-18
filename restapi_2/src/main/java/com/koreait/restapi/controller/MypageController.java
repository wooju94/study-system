package com.koreait.restapi.controller;

import com.koreait.restapi.dto.BoardDTO;
import com.koreait.restapi.entity.StudyApplication;
import com.koreait.restapi.security.MemberDetails;
import com.koreait.restapi.service.BoardService;
import com.koreait.restapi.service.StudyApplicationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final BoardService boardService;
    private final StudyApplicationService applicationService;

    public MypageController(BoardService boardService, StudyApplicationService applicationService) {
        this.boardService = boardService;
        this.applicationService = applicationService;
    }

    // 내가 만든 스터디 목록
    @GetMapping("/my-studies")
    public List<BoardDTO> myStudies(@AuthenticationPrincipal MemberDetails user) {
        int memberId = user.getMember().getId();
        return boardService.findByWriterId(memberId);
    }

    // 내가 신청한 스터디 목록
    @GetMapping("/my-applications")
    public List<StudyApplication> myApplications(@AuthenticationPrincipal MemberDetails user) {
        return applicationService.getMyApplications(user.getMember().getId());
    }
}
