package com.roovies.member.controller;

import com.roovies.member.domain.dto.MemberCreateRequest;
import com.roovies.member.domain.dto.MemberCreateResponse;
import com.roovies.member.repository.MemberRepository;
import com.roovies.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping(value = "/login")
    public ResponseEntity<MemberCreateResponse> login(@RequestBody MemberCreateRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> register(@RequestBody MemberCreateRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<MemberCreateResponse> getMemberByEmail(@RequestParam String email) throws Exception {
        return new ResponseEntity<>( memberService.getMemberByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<MemberCreateResponse> getMemberForAdmin(@RequestParam String email) throws Exception {
        return new ResponseEntity<>( memberService.getMemberByEmail(email), HttpStatus.OK);
    }
}
