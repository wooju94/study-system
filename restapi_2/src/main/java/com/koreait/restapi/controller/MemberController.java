package com.koreait.restapi.controller;

import com.koreait.restapi.dto.MemberDTO;
import com.koreait.restapi.jwt.JwtUtil;
import com.koreait.restapi.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody MemberDTO member) {
        return memberService.signup(member)
                ? "회원가입 성공" : "이미 존재하는 아이디입니다";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        MemberDTO member = memberService.login(request.get("username"), request.get("password"));
        if (member == null) {
            result.put("success", false);
            result.put("message", "아이디 또는 비밀번호가 틀렸습니다.");
            return result;
        }

        String token = jwtUtil.generateToken(member.getUsername(), member.getId());
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        result.put("success", true);
        return result;
    }

    @GetMapping("/me")
    public Map<String, Object> getMyInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer userId = jwtUtil.getUserIdFromRequest(request);
            MemberDTO member = memberService.findById(userId);
            result.put("success", true);
            result.put("data", member);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PutMapping("/me")
    public Map<String, Object> updateMyInfo(@RequestBody MemberDTO reqMember, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer userId = jwtUtil.getUserIdFromRequest(request);
            reqMember.setId(userId);
            boolean updated = memberService.updateMember(reqMember);
            result.put("success", updated);
            result.put("message", updated ? "수정 완료" : "수정 실패");
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
