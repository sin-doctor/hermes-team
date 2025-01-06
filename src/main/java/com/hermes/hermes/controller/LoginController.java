package com.hermes.hermes.controller;


import com.hermes.hermes.dto.User;
import com.hermes.hermes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // invalidate 로그인된 정보를 초기화하면서 없애기
        return "redirect:/";    // 로그아웃 된 정보를 재설정하면서 index.html 돌아가기
    }

    @ModelAttribute("loggedInUser")
    public Object addLoggedInUserToModel(HttpSession session) {
        return session.getAttribute("loggedInUser");
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String user_id, @RequestParam String user_pw, HttpSession session, Model model) {
        User user = userService.login(user_id, user_pw);

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            System.out.println(session.getAttribute("loggedInUser"));
            return "redirect:/";
        } else {
            model.addAttribute("error", "유효하지 않은 유저이름 또는 비밀번호입니다.");
            return "login";
        }
    }

    @GetMapping("/login_search")
    public String loginBySearch() {
        return "login_search";
    }

    @GetMapping("/login_search_completed")
    public String login_search_completed(@RequestParam("user_name")String user_name,
                                         @RequestParam("user_verification_answer")String user_verification_answer,
                                         Model model) {
        User user = userService.login_search_completed(user_name, user_verification_answer);
        System.out.println("user : " + user);
        model.addAttribute("user", user);
        return "login_search_completed";
    }

}