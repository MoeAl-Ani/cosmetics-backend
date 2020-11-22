package com.infotamia.pojos.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "category_translation")
public class CategoryTranslationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "name")
    private String name;
    @Column(name = "language_id")
    private Integer languageId;
    public CategoryTranslationEntity() {
        //
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        CategoryTranslationEntity that = (CategoryTranslationEntity) o;
        return getCategoryId().equals(that.getCategoryId()) &&
                getLanguageId().equals(that.getLanguageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryId(), getLanguageId());
    }

    @Override
    public String toString() {
        return "CategoryTranslationEntity{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", languageId=" + languageId +
                '}';
    }
}
