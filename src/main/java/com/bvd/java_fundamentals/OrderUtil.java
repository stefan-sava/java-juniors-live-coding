package com.bvd.java_fundamentals;

import com.bvd.java_fundamentals.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/*
 * Implement the methods below so that the requirements are met.
 */
public class OrderUtil {

    private OrderUtil() {
    }

    // retrieve orders from csv lines
    public static List<Order> parseCsvLines(final List<String> lines) {
        if(lines == null || lines.isEmpty()) {
            return Collections.emptyList();
        }
        List<Order> orders = new ArrayList<>();
        for(String line : lines) {
            String[] fields = line.split(",", -1);
            try{
                orders.add(new Order(fields[0],
                        fields[1],
                        LocalDateTime.parse(fields[2]),
                        fields[3],
                        fields[4],
                        BigDecimal.valueOf(Long.parseLong(fields[5])),
                        Integer.parseInt(fields[6])));
            }catch(Exception ignored){}
        }
        return orders;
    }

    // calculate revenue by day
    // revenue = unitPrice * quantity
    public static Map<LocalDate, BigDecimal> revenueByDay(final List<Order> orders) {
        // Write your code here and replace the return statement
        return Collections.emptyMap();
    }

    // get top "n" products by revenue
    public static List<Map.Entry<String, BigDecimal>> topProductsByRevenue(final List<Order> orders, final int n) {
        // Write your code here and replace the return statement
        return Collections.emptyList();
    }

    // get customers who ordered products from at least "minCategories" different categories
    public static List<String> customersWithCategoryDiversity(final List<Order> orders, final int minCategories) {
        // Write your code here and replace the return statement
        return Collections.emptyList();
    }

    // find the first product containing a given substring (case-insensitive)
    public static Optional<Order> findFirstProductContaining(final List<Order> orders, final String product) {
        // Write your code here and replace the return statement
        return Optional.empty();
    }
}
