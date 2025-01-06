package com.hermes.hermes.controller;

import com.hermes.hermes.dto.User;
import com.hermes.hermes.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/mypage")
    public String my_page(Model model, HttpSession session) {
        Object user = session.getAttribute("loggedInUser"); // 현재 세션에 로그인한 유저를 Object 자료형의 user 라는 이름으로 받아옴
        model.addAttribute("loggedInUser", user); // model 함수를 통해 user 객체를 loggedInUser 라는 이름으로 프론트에 전송함
        List<Map<String, Object>> purchases; // 구매내역 리스트를 보여주기 위해 List 선언

        if (user != null) { // 로그인 유저가 있다면(=not null) 이라면
            User currentUser = (User) session.getAttribute("loggedInUser"); // Session 에서 현재 로그인한 유저를 currentUser 라는 이름으로 받아옴
            String user_id = currentUser.getUser_id(); // 현재 로그인한 유저의 user_id를 String 형태로 user_id로 저장
            purchases = myPageService.myPageService_getUserPurchase(user_id); // List<Map> purchases 에 user_id를 넣어 해당 user_id 와 일치하는 PURCHASE 의 리스트를 가져옴
            model.addAttribute("purchases", purchases); // purchases 객체를 purchases 라는 이름으로 프론트에 전달
            return "mypage";
        }

        return "mypage";
    }

    @GetMapping("/myPage_infoCorrection")
    public String myPage_infoCorrection() {
        return "myPage_infoCorrection";
    }
    @ModelAttribute("loggedInUser")
    public Object addLoggedInUserToModel(HttpSession session) {
        return session.getAttribute("loggedInUser");
    }
    @PostMapping("/myPage_infoCorrection_success")
    public String myPage_infoCorrection_success(Model model,
                                                HttpSession session,
                                          /*      @RequestParam String user_id,*/
                                                @RequestParam String user_verification_answer,
                                                @RequestParam String user_pw) {
        Object user = session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", user);
        User userid = (User) session.getAttribute("loggedInUser");
        String user_id = userid.getUser_id();
        myPageService.myPage_infoCorrection(user_id, user_verification_answer, user_pw);

        return "myPage_infoCorrection_success";
    }
}
