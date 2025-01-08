package com.hermes.hermes.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor    // 필수 생성자
@NoArgsConstructor     // 기본생성자
@Setter                // setter 줄임말로 사용
@Getter                // getter 줄임말로 사용
@ToString             //toString 줄임말로 사용

public class PurchaseProductJoin {
    private String product_image_path;
    private String product_name;
    private String product_price;
    private int product_size;
    private Date purchase_date;
    private String purchase_order_id;
    private int purchase_product_size;
    private String purchase_status;
}
