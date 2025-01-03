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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
     * List<Product>  products   =   productService.getAllIndexProducts(); //productService 에 있는 getAllIndexProducts 가져오기
     * @param model html에 보내기 위한 model
     * @return index.html 페이지에 products로 불러온 모든 제품리스트를 전달
     */
    @GetMapping("/")
    public String index(HttpSession session,  Model model) {
        Object user = session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", user);
        List<Product>  products   =   productService.getAllIndexProducts();
        model.addAttribute("products", products);
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

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // 아이디 중복확인
    @PostMapping("/check-user_id")
    public void checkDuplicatedUserId(@RequestParam("user_id") String user_id,
                                        HttpServletResponse response
    ) throws IOException {
        boolean isDuplicate = userService.checkDuplicatedUserId(user_id);
        response.setContentType("application/json");
        response.getWriter().write("{\"isDuplicate\" : " + isDuplicate + "}");
    }

    // 회원가입 완료 처리
    @PostMapping("/signup-success")
    public String signup(@ModelAttribute User user) {
        if (user.getUser_id() == null || user.getUser_id().isEmpty()) {
            throw new IllegalArgumentException("User ID는 필수 항목입니다.");
        }
        userService.insertUser(user);
        return "signup-success";
    }

    // 제품 상세 페이지
    @GetMapping("/product_details_page/" +
            "{product_reg_num}")
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
    // 구매 완료 페이지
    @PostMapping("/purchase_completed_page")
    public String purchaseCompletedPage(
            @RequestParam("productId") int productRegNum,
            @RequestParam("size") String selectedSize,
            HttpSession session,
            Model model
    ) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        Product product = productService.getProduct(productRegNum);
        if (product == null) {
            return "redirect:/error";
        }
        Purchase purchase = new Purchase();
        purchase.setPurchase_product_reg_num(productRegNum);
        purchase.setPurchase_order_id(generateOrderId());
        purchase.setPurchase_product_size(selectedSize);
        purchase.setPurchase_user_id(userId);
        purchase.setPurchase_date(new java.util.Date());
        purchase.setPurchase_status("완료");

        purchaseService.savePurchase(purchase);

        model.addAttribute("product", product);
        model.addAttribute("selectedSize", selectedSize);
        model.addAttribute("purchase", purchase);

        return "purchase_completed_page";
    }

    private String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String randomStr = String.format("%04d", (int)(Math.random() * 10000));
        return "ORD-" + timestamp + "-" + randomStr;
    }
}
