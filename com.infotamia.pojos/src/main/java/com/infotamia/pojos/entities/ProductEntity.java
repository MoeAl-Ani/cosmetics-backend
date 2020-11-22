package com.infotamia.pojos.entities;

import javax.persistence.*;
import java.util.*;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "product")
public class ProductEntity {
    public ProductEntity() {
        //
    }
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "price")
    private double price;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "category_id")
    private Integer categoryId;

    @Transient
    private final List<ProductTranslationEntity> productTranslations = new ArrayList<>();
    @Transient
    private final Set<String> images = new HashSet<>();
    @Transient
    private CategoryEntity category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<ProductTranslationEntity> getProductTranslations() {
        return productTranslations;
    }

    public Set<String> getImages() {
        return images;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return getId().equals(that.getId()) &&
                getProductTranslations().equals(that.getProductTranslations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductTranslations());
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", price=" + price +
                ", productTranslations=" + productTranslations +
                '}';
    }
}
