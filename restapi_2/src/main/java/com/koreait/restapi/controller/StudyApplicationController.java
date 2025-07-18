package com.koreait.restapi.controller;

import com.koreait.restapi.dto.StudyApplicationDTO;
import com.koreait.restapi.entity.StudyApplication;
import com.koreait.restapi.security.MemberDetails;
import com.koreait.restapi.service.StudyApplicationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class StudyApplicationController {

    private final StudyApplicationService applicationService;

    public StudyApplicationController(StudyApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public String apply(@RequestBody StudyApplicationDTO dto,
                        @AuthenticationPrincipal MemberDetails user) {
        boolean result = applicationService.apply(user.getMember().getId(), dto.getBoardId());
        return result ? "신청 완료" : "중복 신청 또는 정원 초과입니다.";
    }

    @GetMapping("/me")
    public List<StudyApplication> myApplications(@AuthenticationPrincipal MemberDetails user) {
        return applicationService.getMyApplications(user.getMember().getId());
    }
}
