package com.project.shopapp.services.coupon;

import com.project.shopapp.models.Coupon;
import com.project.shopapp.models.CouponCondition;
import com.project.shopapp.repositories.CouponConditionRepository;
import com.project.shopapp.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CouponService implements ICouponService{
    private final CouponRepository counponRepository;
    private final CouponConditionRepository counponConditionRepository;
    @Override
    public double caculateCounponValue(String couponCode, double totalAmount) {
        Coupon coupon = counponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));

        if(!coupon.isActive()) {
            throw new IllegalArgumentException("Coupon is not active");
        }
        double discount = caculateDiscount(coupon, totalAmount);
        double finalAmount = totalAmount - discount;
        return finalAmount;
    }
    private double caculateDiscount(Coupon coupon, double totalAmount) {
        List<CouponCondition> conditions =
                counponConditionRepository.findByCouponId(coupon.getId());
        double discount = 0;
        double updatedTotalAmount = totalAmount;
        for(CouponCondition condition : conditions){
            String attribute = condition.getAttribute();
            String operator = condition.getOperator();
            String value = condition.getValue();
            double percentDiscount = Double.valueOf(String.valueOf(condition.getDiscount_amount()));

            if(attribute.equals("minimum_amount")) {
                if(operator.equals(">") && updatedTotalAmount > Double.parseDouble(value)) {
                   discount += updatedTotalAmount * percentDiscount / 100;
                }
            } else if (attribute.equals("applicable_date")) {
                LocalDate applicableDate = LocalDate.parse(value);
                LocalDate currentDate = LocalDate.now();

                if(operator.equalsIgnoreCase("BETWEEN")
                && currentDate.equals(applicableDate)) {
                    discount += updatedTotalAmount * percentDiscount / 100;
                }
            }
            updatedTotalAmount = updatedTotalAmount - discount;
        }
        return discount;
    }
}
