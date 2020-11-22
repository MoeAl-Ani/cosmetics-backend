package com.infotamia.pojos.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "order_details")
public class OrderDetailsEntity {

    public OrderDetailsEntity() {
        //
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "order_reference")
    private String orderReference;
    @Column(name = "net_amount")
    private Double netAmount;
    @Column(name = "address_id")
    private Long addressId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Transient
    private String email;
    @Transient
    private String phoneNumber;
    @Transient
    private final List<ProductEntity> products = new ArrayList<>();
    @Transient
    private Set<OrderProductEntity> selectedProducts = new HashSet<>();
    @Transient
    private AddressEntity address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public Set<OrderProductEntity> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(Set<OrderProductEntity> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntity that = (OrderDetailsEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String toString() {
        return super.toString();
    }
}
