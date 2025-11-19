package com.bvd.java_fundamentals;

import com.bvd.java_fundamentals.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        return lines.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(line -> {
                    try {
                        String[] split = line.split(",", -1);
                        return new Order(split[0].trim(),
                                split[1].trim(),
                                LocalDate.parse(split[2].trim()),
                                split[3].trim(),
                                split[4].trim(),
                                new BigDecimal(split[5].trim()),
                                Integer.parseInt(split[6].trim()));
                    } catch (Exception ignored) { }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // calculate revenue by day
    // revenue = unitPrice * quantity
    public static Map<LocalDate, BigDecimal> revenueByDay(final List<Order> orders) {
        // Write your code here and replace the return statement
        if(orders == null || orders.isEmpty()) {
            return Collections.emptyMap();
        }

        return orders.stream().
                collect(Collectors.groupingBy(Order::getOrderDate, Collectors.mapping(order -> order.getUnitPrice().multiply(BigDecimal.valueOf(order.getQuantity())), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    // get top "n" products by revenue
    public static List<Map.Entry<String, BigDecimal>> topProductsByRevenue(final List<Order> orders, final int n) {
        if(orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, BigDecimal> map = orders.
                stream().
                collect(
                        Collectors.toMap(
                                Order::getProductName,
                                order -> order.getUnitPrice().
                                        multiply(BigDecimal.valueOf(
                                                order.getQuantity())), BigDecimal::add));

        return map.entrySet().
                stream().
                sorted(Map.Entry.<String, BigDecimal>comparingByValue(BigDecimal::compareTo).reversed().thenComparing(Map.Entry.comparingByKey())).
                limit(n).
                toList();
    }

    // get customers who ordered products from at least "minCategories" different categories
    public static List<String> customersWithCategoryDiversity(final List<Order> orders, final int minCategories) {
        // Write your code here and replace the return statement
        return orders.stream()
                .collect(
                        Collectors.groupingBy(
                                Order::getCustomerId,
                                Collectors.mapping(
                                        Order::getCategory,
                                        Collectors.toSet()))).
                entrySet().
                stream().
                filter(customer -> customer.getValue().size() >= minCategories).
                map(Map.Entry::getKey).
                collect(Collectors.toList());
    }

    // find the first product containing a given substring (case-insensitive)
    public static Optional<Order> findFirstProductContaining(final List<Order> orders, final String product) {
        // Write your code here and replace the return statement
        return orders.stream().
                filter(order -> order.getProductName().toLowerCase().contains(product.toLowerCase())).
                findFirst();
    }
}
