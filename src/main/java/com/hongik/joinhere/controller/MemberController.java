package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.club.ShowMyClubResponse;
import com.hongik.joinhere.dto.member.MemberRequest;
import com.hongik.joinhere.dto.member.MemberResponse;
import com.hongik.joinhere.dto.member.LoginMemberRequest;
import com.hongik.joinhere.dto.member.LoginMemberResponse;
import com.hongik.joinhere.service.BelongService;
import com.hongik.joinhere.service.ClubService;
import com.hongik.joinhere.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final BelongService belongService;

    @Autowired
    public MemberController(MemberService memberService, BelongService belongService) {
        this.memberService = memberService;
        this.belongService = belongService;
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(request));
    }

    @PatchMapping
    public void update(@RequestBody MemberRequest request) {
        memberService.updateMemberInfo(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest request) {
        LoginMemberResponse response = memberService.login(request);

        if (response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie idCookie = new Cookie("id", null);
        Cookie nameCookie = new Cookie("name", null);
        idCookie.setMaxAge(0);
        nameCookie.setMaxAge(0);
        response.addCookie(idCookie);
        response.addCookie(nameCookie);
    }

    @GetMapping("/{member-id}")
    public MemberResponse showMemberInfo(@PathVariable(name = "member-id") String memberId) {
        return memberService.findMember(memberId);
    }

    @GetMapping("/{member-id}/clubs")
    public ResponseEntity<List<ShowMyClubResponse>> showMyClubs(@PathVariable("member-id") String memberId) {
        System.out.println(memberId);
        List<ShowMyClubResponse> responses = belongService.findBelongByMemberId(memberId);
        if (responses != null)
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
