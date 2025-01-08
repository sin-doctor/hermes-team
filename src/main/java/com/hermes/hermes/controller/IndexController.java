package com.hermes.hermes.controller;

import com.hermes.hermes.dto.Product;
import com.hermes.hermes.dto.Purchase;
import com.hermes.hermes.dto.User;
import com.hermes.hermes.service.ProductService;
import com.hermes.hermes.service.PurchaseService;
import com.hermes.hermes.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseService purchaseService;

    /**
     * ModelAttribute: 모든 메서드 호출 전에 공통적으로 실행
     * 세션에서 로그인된 사용자 정보를 가져와 모델에 추가
     * @param session HttpSession 객체를 통해 사용자 세션 관리
     * @param model HTML 렌더링에 필요한 데이터를 추가하는 Model 객체
     */
    @ModelAttribute
    public void addLoggedInUserToModel(HttpSession session, Model model) {
        Object user = session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", user);
    }
    /**
     * List<Product>  products   =   productService.getAllIndexProducts(); //productService 에 있는 getAllIndexProducts 가져오기
     * @param model html에 보내기 위한 model
     * @return index.html 페이지에 products로 불러온 모든 제품리스트를 전달
     */
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Object user = session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", user);

        List<Product> products = productService.getAllIndexProducts();
        List<Product> recommendedProducts = products.stream()
                .limit(6)
                .toList();
        model.addAttribute("products", recommendedProducts);
        System.out.println(recommendedProducts);

        return "index";
    }
    /**
     * @param product_category 카테고리에 해당하는 리스트만 가져오기 위해 카테고리 변수 문자열로 선언
     * @param model html thymeleaf 에 보내기 위한 model
     * List<Product>  products   =   productService.getCategoryList(product_category); //productService 에 있는 getCategoryList 가져오기
     * @return category_page.html 페이지에 카테고리별 해당 상품 리스트를 전달
     */
    @GetMapping("/category_page/{product_category}")
    public String CategoryPage(@PathVariable String product_category, Model model) {
        List<Product>  products   =   productService.getCategoryList(product_category);
        model.addAttribute("products", products);
        return "category_page";
    }

    /**
     * 회원가입 페이지로 이동
     * @return signup.html 페이지 반환
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    /**
     * 회원가입 완료 처리
     * @param user 사용자 정보가 포함된 User 객체
     * @return 회원가입 성공 페이지 반환
     * @throws IllegalArgumentException 필수 정보가 누락된 경우
     */
    @PostMapping("/signup-success")
    public String signup(@ModelAttribute User user) {
        if (user.getUser_id() == null || user.getUser_id().isEmpty()) {
            throw new IllegalArgumentException("User ID는 필수 항목입니다.");
        }
        userService.insertUser(user);
        return "signup-success";
    }

    /**
     * 아이디 중복 확인 AJAX 요청 처리
     * @param user_id 중복 확인할 사용자 ID
     * @param response HttpServletResponse 객체로 JSON 응답 전송
     * @throws IOException 응답 작성 실패 시 예외 발생
     */

    @PostMapping("/check-user_id")
    public void checkDuplicatedUserId(@RequestParam("user_id") String user_id,
                                      HttpServletResponse response
    ) throws IOException {
        boolean isDuplicate = userService.checkDuplicatedUserId(user_id);
        response.setContentType("application/json");
        response.getWriter().write("{\"isDuplicate\" : " + isDuplicate + "}");
    }

    /**
     * 제품 상세 페이지로 이동
     * @param product_reg_num 제품 등록 번호 (URL 경로에서 추출)
     * @param model HTML 렌더링에 필요한 데이터를 추가하는 Model 객체
     * @return product_details_page.html 페이지 반환
     */

    @GetMapping("/product_details_page/{product_reg_num}")
    public String productDetailsPage(@PathVariable int product_reg_num, Model model) {
        Product product = productService.getProduct(product_reg_num);
        if (product == null) {
            model.addAttribute("error", "상품 정보를 찾을 수 없습니다.");
            return "error";
        }

        List<String> sizes = Arrays.asList("250", "260", "270", "280");
        model.addAttribute("product", product);
        model.addAttribute("sizes", sizes);

        return "product_details_page";
    }

    /**
     * 구매 완료 페이지로 이동
     * 구매 정보를 생성하고 저장한 뒤 페이지에 전달
     * @param productRegNum 구매한 제품 등록 번호
     * @param selectedSize 선택한 제품 크기
     * @param session HttpSession 객체로 로그인된 사용자 정보 관리
     * @param model HTML 렌더링에 필요한 데이터를 추가하는 Model 객체
     * @return purchase_completed_page.html 페이지 반환
     */

    @PostMapping("/purchase_completed_page")
    public String purchaseCompletedPage(
            @RequestParam("productId") int productRegNum,
            @RequestParam("size") String selectedSize,
            HttpSession session,
            Model model
    ) {
        // 로그인된 사용자 정보 확인
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        String userId = loggedInUser.getUser_id(); // 로그인된 사용자 ID 가져오기

        Product product = productService.getProduct(productRegNum);
        if (product == null) {
            return "redirect:/error"; // 상품 정보가 없는 경우 에러 페이지로 리다이렉트
        }

        // 구매 객체 생성 및 세팅
        Purchase purchase = new Purchase();
        purchase.setPurchase_product_reg_num(productRegNum);
        purchase.setPurchase_order_id(generateOrderId());
        purchase.setPurchase_product_size(selectedSize);
        purchase.setPurchase_user_id(userId);
        purchase.setPurchase_date(new java.util.Date());
        purchase.setPurchase_status("완료");

        // 구매 정보 저장
        purchaseService.savePurchase(purchase);

        // 모델에 제품 정보 및 구매 정보 추가
        model.addAttribute("product", product);
        model.addAttribute("selectedSize", selectedSize);
        model.addAttribute("purchase", purchase);

        return "purchase_completed_page"; // 구매 완료 페이지로 리턴
    }

    /**
     * 주문 번호 생성
     * 현재 시간과 랜덤 숫자를 조합해 고유한 주문 번호 생성
     * @return 생성된 주문 번호 문자열
     */

    private String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String randomStr = String.format("%04d", (int)(Math.random() * 10000));
        return "ORD-" + timestamp + "-" + randomStr;
    }
}
