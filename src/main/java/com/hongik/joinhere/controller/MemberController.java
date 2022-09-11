package com.hongik.joinhere.controller;

import com.hongik.joinhere.domain.Member;
import com.hongik.joinhere.dto.member.CreateMemberRequest;
import com.hongik.joinhere.dto.member.CreateMemberResponse;
import com.hongik.joinhere.dto.member.LoginMemberRequest;
import com.hongik.joinhere.dto.member.LoginMemberResponse;
import com.hongik.joinhere.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<CreateMemberResponse> create(@RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(request));
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginMemberRequest request, HttpServletResponse response) {
        LoginMemberResponse loginMemberResponse= memberService.login(request);

        if (loginMemberResponse == null) {
            return;
        }

        Cookie idCookie = new Cookie("id", loginMemberResponse.getId());
        Cookie nameCookie = new Cookie("name", loginMemberResponse.getName());
        response.addCookie(idCookie);
        response.addCookie(nameCookie);
    }

//    @PostMapping("/logout")
//    public String logout() {
//        return "";
//    }
}
