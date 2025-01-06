package com.hermes.hermes.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage != null ? errorMessage : "요청 처리 중 문제가 발생했습니다.");
        return "error";
    }
    @RequestMapping("/test-error")
    public String testError() {
        throw new RuntimeException("테스트 에러 발생");
    }
}
