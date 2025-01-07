package com.example.mynt.costdelivery;

import java.math.BigDecimal;

public class CostDeliveryConstants {
    public static final int MAX_KG = 50;
    public static final int HEAVY_KG = 10;
    public static final int MEDIUM_VOL = 1500;
    public static final int LARGE_VOL = 2500;

    public static final BigDecimal HEAVY_PARCEL_COST = BigDecimal.valueOf(20);
    public static final BigDecimal SMALL_PARCEL_COST = BigDecimal.valueOf(0.03);
    public static final BigDecimal MEDIUM_PARCEL_COST = BigDecimal.valueOf(0.04);
    public static final BigDecimal LARGE_PARCEL_COST = BigDecimal.valueOf(0.05);
}
