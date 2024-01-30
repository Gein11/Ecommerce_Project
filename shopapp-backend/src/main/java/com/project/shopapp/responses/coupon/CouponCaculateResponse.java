package com.project.shopapp.responses.coupon;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponCaculateResponse {
    @JsonProperty("result")
    private Double result;

    @JsonProperty("errorMessage")
    private String errorMessage;

}

