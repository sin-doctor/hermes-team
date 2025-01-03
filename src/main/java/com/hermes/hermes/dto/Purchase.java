package com.hermes.hermes.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import java.util.Date;

@AllArgsConstructor    // 필수 생성자
@NoArgsConstructor     // 기본생성자
@Setter                // setter 줄임말로 사용
@Getter                // getter 줄임말로 사용
@ToString             //toString 줄임말로 사용

public class Purchase {
    @Id //primary key 표기로 id는 맨 위에 작성
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 숫자자동증가임을 설정
    private int purchase_reg_num;
    private int purchase_product_reg_num;
    private String purchase_order_id;
    private String purchase_product_size;
    private String purchase_user_id;
    private Date purchase_date;
    private String purchase_status;


}
