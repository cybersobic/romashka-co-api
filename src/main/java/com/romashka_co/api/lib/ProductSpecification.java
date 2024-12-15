package com.romashka_co.api.lib;

import com.romashka_co.api.models.Product;
import org.springframework.data.jpa.domain.Specification;

// Класс с описанием фильтров в запросе
public class ProductSpecification {
    public static Specification<Product> nameFilter(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> priceSmallerThanFilter(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), price);
    }

    public static Specification<Product> priceGreaterThanFilter(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<Product> isAvailableFilter(Boolean available) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("availability"), available);
    }
}
