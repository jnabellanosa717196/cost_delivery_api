package com.example.mynt.costdelivery;

import com.example.mynt.common.exception.CostDeliveryException;
import com.example.mynt.common.http.ApiHttpClient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Component
public class CostDeliveryApi {

    private final ApiHttpClient apiHttpClient;

    @Autowired
    public CostDeliveryApi(ApiHttpClient apiHttpClient) {
        this.apiHttpClient = apiHttpClient;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    static class VoucherApiResponse {
        private String code;
        private float discount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
        private Date expiry;
    }

    public BigDecimal getVoucherDiscount(String voucherCode) throws CostDeliveryException {
        String url = "https://mynt-exam.mocklab.io/voucher/" + voucherCode + "?key=apikey";

        String response = apiHttpClient.get(url);
        VoucherApiResponse voucherApiResponse;
        try {
            voucherApiResponse = new ObjectMapper().readValue(response, VoucherApiResponse.class);
        } catch (IOException ex) {
            throw new CostDeliveryException(ex.getMessage(), ex);
        }

        if (voucherApiResponse.expiry.before(new Date())) {
            throw new CostDeliveryException("Voucher code = " + voucherCode + "already expired");
        }
        return BigDecimal.valueOf(voucherApiResponse.discount);
    }
}
