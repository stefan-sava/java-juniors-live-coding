package com.bvd.java_fundamentals.model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class Order {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private String productName;
    private String category;
    private BigDecimal unitPrice;
    private int quantity;

    public  Order() {}

    public Order(String orderId, String customerId, LocalDate orderDate, String productName, String category, BigDecimal unitPrice, int quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

}
