package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public void loginGET(String error, String logout){ //로그인 과정 처리, 로그아웃 처리
        log.info("login get...........");
        log.info("logout: " + logout);

        if(logout != null){
            log.info("user logout.........");
        }

    }



}
