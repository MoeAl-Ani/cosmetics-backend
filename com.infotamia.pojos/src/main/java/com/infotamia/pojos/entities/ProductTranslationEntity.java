package com.infotamia.pojos.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "product_translation")
public class ProductTranslationEntity {
    public ProductTranslationEntity() {
        //
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "language_id")
    private Integer languageId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTranslationEntity that = (ProductTranslationEntity) o;
        return getProductId().equals(that.getProductId()) &&
                getLanguageId().equals(that.getLanguageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getLanguageId());
    }

    @Override
    public String toString() {
        return "ProductTranslation{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", languageId=" + languageId +
                '}';
    }
}
