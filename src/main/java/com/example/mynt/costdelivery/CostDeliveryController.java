package com.example.mynt.costdelivery;

import com.example.mynt.common.exception.CostDeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class CostDeliveryController {

    private final CostDeliveryService service;

    @Autowired
    public CostDeliveryController(CostDeliveryService costDeliveryService) {
        this.service = costDeliveryService;
    }

    @GetMapping("/costDelivery")
    public BigDecimal getCostDelivery(@RequestParam("wt") BigDecimal weight, @RequestParam("h")BigDecimal height,
                                  @RequestParam("w")BigDecimal width, @RequestParam("l")BigDecimal length,
                                  @RequestParam(required = false, value = "vc")Optional<String> voucherCode)
            throws CostDeliveryException {
        CostDeliveryParams params = new CostDeliveryParams(weight, height, width, length, voucherCode);
        return service.getCostDelivery(params);
    }
}
