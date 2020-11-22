package com.infotamia.pojos.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "category")
public class CategoryEntity {
    public CategoryEntity() {
        //
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private final Set<CategoryTranslationEntity> categoryTranslations = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<CategoryTranslationEntity> getCategoryTranslations() {
        return categoryTranslations;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                '}';
    }
}
