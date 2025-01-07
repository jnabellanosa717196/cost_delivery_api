package com.example.mynt.costdelivery;

import com.example.mynt.common.exception.CostDeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.mynt.costdelivery.CostDeliveryConstants.HEAVY_KG;
import static com.example.mynt.costdelivery.CostDeliveryConstants.HEAVY_PARCEL_COST;
import static com.example.mynt.costdelivery.CostDeliveryConstants.LARGE_PARCEL_COST;
import static com.example.mynt.costdelivery.CostDeliveryConstants.LARGE_VOL;
import static com.example.mynt.costdelivery.CostDeliveryConstants.MAX_KG;
import static com.example.mynt.costdelivery.CostDeliveryConstants.MEDIUM_PARCEL_COST;
import static com.example.mynt.costdelivery.CostDeliveryConstants.MEDIUM_VOL;
import static com.example.mynt.costdelivery.CostDeliveryConstants.SMALL_PARCEL_COST;
import static com.example.mynt.costdelivery.CostRule.HEAVY_PARCEL;
import static com.example.mynt.costdelivery.CostRule.LARGE_PARCEL;
import static com.example.mynt.costdelivery.CostRule.MEDIUM_PARCEL;
import static com.example.mynt.costdelivery.CostRule.SMALL_PARCEL;

@Service
public class CostDeliveryService {

    private final CostDeliveryApi apiClient;

    @Autowired
    public CostDeliveryService(CostDeliveryApi costDeliveryApi) {
        this.apiClient = costDeliveryApi;
    }

    public BigDecimal getCostDelivery(CostDeliveryParams params) throws CostDeliveryException {
        if (Validator.areParamsInvalid(params)) {
            throw new CostDeliveryException("Parameters are invalid");
        }

        CostRule costRule = identifyParcelCostRule(params);
        BigDecimal costDeliveryWOVoucher = computeCostDelivery(params, costRule);

        if (params.voucherCode().isPresent()) {
            BigDecimal discount = apiClient.getVoucherDiscount(params.voucherCode().get());
            return costDeliveryWOVoucher.subtract(discount);
        }
        return costDeliveryWOVoucher.setScale(2);
    }

    private CostRule identifyParcelCostRule(CostDeliveryParams params) throws CostDeliveryException {
        if (params.weight().doubleValue() > MAX_KG) {
            throw new CostDeliveryException("Parcel weight exceeds 50kg");
        } else if (params.weight().doubleValue() > HEAVY_KG && params.weight().doubleValue() < MAX_KG) {
            return HEAVY_PARCEL;
        }

        BigDecimal volume = params.height().multiply(params.width().multiply(params.length()));
        if (volume.doubleValue() < MEDIUM_VOL) {
            return SMALL_PARCEL;
        } else if (volume.doubleValue() > MEDIUM_VOL && volume.doubleValue() < LARGE_VOL) {
            return MEDIUM_PARCEL;
        } else {
            return LARGE_PARCEL;
        }
    }

    private BigDecimal computeCostDelivery(CostDeliveryParams params, CostRule costRule) {
        BigDecimal volume = params.height().multiply(params.width().multiply(params.length()));

        return switch (costRule) {
            case HEAVY_PARCEL -> HEAVY_PARCEL_COST.multiply(params.weight());
            case SMALL_PARCEL -> SMALL_PARCEL_COST.multiply(volume);
            case MEDIUM_PARCEL -> MEDIUM_PARCEL_COST.multiply(volume);
            case LARGE_PARCEL -> LARGE_PARCEL_COST.multiply(volume);
        };
    }

}
