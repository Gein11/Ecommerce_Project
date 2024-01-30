package com.project.shopapp.controllers;

import com.project.shopapp.responses.coupon.CouponCaculateResponse;
import com.project.shopapp.services.coupon.CouponService;
import com.project.shopapp.services.coupon.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/calculate")
    public ResponseEntity<?> caculateCounponValue(
           @RequestParam("couponCode") String couponCode,
            @RequestParam("totalAmount") double totalAmount
    ) {


        try {
            double finalAmount = couponService.caculateCounponValue(couponCode, totalAmount);
            CouponCaculateResponse response =  CouponCaculateResponse
                    .builder()
                    .result(finalAmount)
                    .errorMessage("")
                    .build();

            return ResponseEntity.ok(response);
        }catch (Exception e){

            return ResponseEntity.badRequest().body(
                    CouponCaculateResponse.builder()
                            .result(totalAmount)
                            .errorMessage(e.getMessage())
                            .build()
            );
        }
    }

}
