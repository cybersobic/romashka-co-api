package com.romashka_co.api.controllers;

import com.romashka_co.api.models.Product;
import com.romashka_co.api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("Все товары успешно получены :)\n");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // Возвращение статуса 200 Ok
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        System.out.printf("Товар №%d был успешно получен :)\n", id);
        return ResponseEntity.ok(product);  // Возвращение статуса 200 Ok
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        System.out.println("Товар был успешно добавлен :)\n");
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);  // Возвращение статуса 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productInfo) {
        Product updatedProduct = productService.updateProduct(id, productInfo);
        System.out.printf("Информация о товаре №%d была успешно обновлена :)\n", id);
        return ResponseEntity.ok(updatedProduct);   // Возвращение статуса 200 Ok
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        System.out.printf("Товар №%d был успешно удалён :)\n", id);
        return ResponseEntity.noContent().build();  // Возвращение статуса 204 No Content
    }
}
