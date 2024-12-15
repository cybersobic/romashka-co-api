package com.romashka_co.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Класс-модель с описанием структуры товара
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название товара не может быть пустым")
    @Size(max = 255, message = "Название товара не может содержать более 255 символов")
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 4096, message = "Описание товара не может содержать более 4096 символов")
    @Column(name = "description", length = 4096)
    private String description;

    @Min(value = 0, message = "Цена товара не может быть отрицательной")
    @Column(name = "price", nullable = false, columnDefinition = "DOUBLE PRECISION DEFAULT 0")
    private double price = 0;

    @Column(name = "availability", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean availability = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
