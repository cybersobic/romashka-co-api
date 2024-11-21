package com.romashka_co.api.services;

import com.romashka_co.api.exceptions.ProductCreationException;
import com.romashka_co.api.exceptions.ProductNotFoundException;
import com.romashka_co.api.models.Product;
import com.romashka_co.api.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Класс-сервис с бизнес-логикой для товаров
@Service
public class ProductService {
    // Внедрение зависимости от репозитория
    @Autowired
    private ProductRepository productRepository;

    // Получение списка всех товаров
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Получение товара по id
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Товар не найден"));
    }

    // Добавление нового товара
    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        }
        catch (Exception ex) {
            throw new ProductCreationException(ex.getMessage());
        }
    }

    // Обновление данных о товаре
    public Product updateProduct(Long id, Product productInfo) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Товар не найден"));

            product.setName(productInfo.getName());
            product.setDescription(productInfo.getDescription());
            product.setPrice(productInfo.getPrice());
            product.setAvailability(productInfo.getAvailability());

            return productRepository.save(product);
        }
        catch (ProductNotFoundException ex) {
            throw new ProductNotFoundException("Товар не найден");
        }
        catch (Exception ex) {
            throw new ProductCreationException(ex.getMessage());
        }
    }

    // Удаление товара
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Товар не найден"));
        productRepository.delete(product);
    }
}
