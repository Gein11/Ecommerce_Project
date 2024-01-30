package com.project.shopapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "coupon_conditions")
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attribute", nullable = false)
    private String attribute;


    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discount_amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    @JsonBackReference
    private Coupon coupon;
}
/*
    INSERT INTO coupons(id,code) VALUES (1,'HEAVEN');
    INSERT INTO coupons(id,code) VALUES (2,'DISCOUNT20');
    INSERT INTO coupon_conditions (id,coupon_id,attribute, operator, value, discount_amount)
    VALUES (1,1,'minimum_amount','>','100',10);

        INSERT INTO coupon_conditions (id,coupon_id,attribute, operator, value, discount_amount)
    VALUES (2,1,'applicable_date','BETWEEN','2023-12-25',5)

    đơn hàng lớn hơn 200 đô giảm 20%
     INSERT INTO coupon_conditions (id,coupon_id,attribute, operator, value, discount_amount)
    VALUES (3,2,'minimum_amount','>','200',10);
 */
