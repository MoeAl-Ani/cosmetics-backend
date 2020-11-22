package com.infotamia.pojos.entities;

import javax.persistence.*;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "product_image")
public class ProductImageEntity {
    public ProductImageEntity() {
        //
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "image_url")
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
