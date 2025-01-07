package com.example.mynt.costdelivery;

public class Validator {

    public static boolean areParamsInvalid(CostDeliveryParams params) {
        return params.weight().doubleValue() == 0
                || params.height().doubleValue() == 0
                || params.width().doubleValue() == 0
                || params.length().doubleValue() == 0;
    }
}
