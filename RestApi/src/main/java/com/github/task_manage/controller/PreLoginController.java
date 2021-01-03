package com.github.task_manage.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "prelogin")
public class PreLoginController {

	@GetMapping
    public String preLogin(HttpServletRequest request, HttpServletResponse response) {
        DefaultCsrfToken token = (DefaultCsrfToken) request.getAttribute("_csrf");
        if (token == null) {
            throw new RuntimeException("could not get a token.");
        }

        // 下記、CookieにSameSite:NoneとXSRF-TOKEN:token.getToken()をセットし、HeaderにSameSiteとXSRF-TOKEをセットしないといけない理由がわからない。
        Cookie cookie = new Cookie("SameSite", "None");
        Cookie cookie2 = new Cookie("XSRF-TOKEN", token.getToken());
        response.addCookie(cookie);
        response.addCookie(cookie2);

        response.addHeader("Set-Cookie", "XSRF-TOKEN=" + token.getToken() + "; " + "secure; SameSite=None;");
        // 上記の式でもってログインできたわけだが、以下の式と同じ意味か？？
//         Cookie cookie3 = new Cookie("XSRF-TOKEN", token.getToken() + "; " + "secure; SameSite=None;");
//         response.addCookie(cookie3);

        return token.getToken();
    }
}
