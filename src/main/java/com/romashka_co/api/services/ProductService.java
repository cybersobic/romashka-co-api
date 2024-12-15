package com.romashka_co.api.services;

import com.romashka_co.api.exceptions.ProductCreationException;
import com.romashka_co.api.exceptions.ProductNotFoundException;
import com.romashka_co.api.lib.ProductSpecification;
import com.romashka_co.api.models.Product;
import com.romashka_co.api.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

// Класс-сервис с бизнес-логикой для товаров
@Service
public class ProductService {
    // Внедрение зависимости от репозитория
    @Autowired
    private ProductRepository productRepository;

    // Получение списка всех товаров
    public List<Product> getAllProducts(Integer page, Integer size, String name, Double smallerThanPrice, Double greaterThanPrice, Boolean availability, String sortBy) {
        // Создание пустой спецификации
        Specification<Product> specification = Specification.where(null);

        // Проверка фильтра имени
        if(name != null && !name.isEmpty()) {
            specification = specification.and(ProductSpecification.nameFilter(name));
        }

        // Проверка фильтра меньше чем цена
        if(smallerThanPrice != null && smallerThanPrice > 0) {
            specification = specification.and(ProductSpecification.priceSmallerThanFilter(smallerThanPrice));
        }

        // Проверка фильтра больше чем цена
        if(greaterThanPrice != null && greaterThanPrice > 0) {
            specification = specification.and(ProductSpecification.priceGreaterThanFilter(greaterThanPrice));
        }

        // Проверка фильтра доступности товара
        if(availability != null) {
            specification = specification.and(ProductSpecification.isAvailableFilter(availability));
        }

        // Сортировка товаров
        Sort sort = Sort.unsorted();
        if(sortBy != null && !sortBy.isEmpty()) {
            sort = switch (sortBy) {
                case "name" -> Sort.by(Sort.Direction.ASC, "name");
                case "-name" -> Sort.by(Sort.Direction.DESC, "name");
                case "price" -> Sort.by(Sort.Direction.ASC, "price");
                case "-price" -> Sort.by(Sort.Direction.DESC, "price");
                default -> throw new IllegalArgumentException("Некорректное значение sortBy:" + sortBy);
            };
        }

        // Значение номера страницы по умолчанию
        int defaultPage = 0;

        // Значение количества товаров на странице по умолчанию
        int defaultSize = getAllProductsCount();

        // Проверка значений page и size
        int pageIndex = (page != null && page >= 0) ? page : defaultPage;
        int pageSize = (size != null && size > 0) ? size : defaultSize;

        // Создание пагинации товаров
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        return productRepository.findAll(specification, pageable).getContent();
    }

    // Получение количества всех товаров
    public int getAllProductsCount() {
        return (int) productRepository.count();
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
