package com.infotamia.pojos.entities;

import javax.persistence.*;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "order_product")
public class OrderProductEntity {
    public OrderProductEntity() {
        //
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "comment")
    private String comment;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "OrderProductEntity{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", count=" + quantity +
                ", comment='" + comment + '\'' +
                '}';
    }
}
