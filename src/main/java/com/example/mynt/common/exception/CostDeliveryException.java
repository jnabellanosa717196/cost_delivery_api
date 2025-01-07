package com.example.mynt.common.exception;

public class CostDeliveryException extends Exception{
    public CostDeliveryException(String msg) {
        super(msg);
    }

    public CostDeliveryException(String msg, Throwable t) {
        super(msg, t);
    }
}
