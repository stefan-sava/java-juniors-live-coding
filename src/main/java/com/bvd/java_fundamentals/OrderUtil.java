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
        // Write your code here and replace the return statement
        if (lines == null || lines.isEmpty()) {
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

        if(orders == null || orders.isEmpty()) return Map.of();

        return orders.stream().
                collect(
                        Collectors.groupingBy(
                                Order::getOrderDate,
                                Collectors.mapping(o -> o.getUnitPrice().multiply(BigDecimal.valueOf(o.getQuantity())), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    // get top "n" products by revenue
    public static List<Map.Entry<String, BigDecimal>> topProductsByRevenue(final List<Order> orders, final int n) {
        // Write your code here and replace the return statement
        Map<String, BigDecimal> revenueByKey = orders.stream()
                .collect(Collectors.toMap(o-> o.getProductName().toLowerCase().trim(), o -> o.getUnitPrice().multiply(BigDecimal.valueOf(o.getQuantity())), BigDecimal::add));

        Map<String, String> names = orders.
                stream().
                collect(Collectors.toMap(o -> o.getProductName().trim().toLowerCase(), o->o.getProductName().trim(), (a,b) -> b));

        return revenueByKey.
                entrySet().
                stream().
                sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                limit(n).
                map(e -> Map.entry(names.get(e.getKey()), e.getValue())).
                collect(Collectors.toList());

    }

    // get customers who ordered products from at least "minCategories" different categories
    public static List<String> customersWithCategoryDiversity(final List<Order> orders, final int minCategories) {

        Map<String, Set<String>> customerCategory = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomerId, Collectors.mapping(Order::getCategory, Collectors.toSet())));


        return customerCategory.entrySet().stream().
                filter(e -> e.getValue().size() >= minCategories).
                map(Map.Entry::getKey).
                collect(Collectors.toList());
    }

    // find the first product containing a given substring (case-insensitive)
    public static Optional<Order> findFirstProductContaining(final List<Order> orders, final String product) {
        for (Order order : orders) {
            if (order.getProductName().toLowerCase().contains(product.toLowerCase()) ||
                    order.getCategory().toLowerCase().contains(product.toLowerCase())) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }
}
