package com.example.mynt.costdelivery;

import java.math.BigDecimal;
import java.util.Optional;

public record CostDeliveryParams(
        BigDecimal weight,
        BigDecimal height,
        BigDecimal width,
        BigDecimal length,
        Optional<String> voucherCode
) {
}
