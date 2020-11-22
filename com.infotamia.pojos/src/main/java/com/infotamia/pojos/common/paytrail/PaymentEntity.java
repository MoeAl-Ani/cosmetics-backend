package com.infotamia.pojos.common.paytrail;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "payment")
public class PaymentEntity {
    private Long id;
    @NotNull(message = "transactionId was null")
    private String transactionId;
    @NotNull(message = "orderId was null")
    private Long orderId;
    @NotNull(message = "payment type was null")
    private PaymentType type;
    private Integer selectedMethod;
    private LocalDateTime createdAt;
    private List<PaymentStatusEntity> paymentStatusEntity;

    public PaymentEntity() {
        paymentStatusEntity = new ArrayList<>();
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "transaction_id", nullable = false)
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String token) {
        this.transactionId = token;
    }

    @Basic
    @Column(name = "order_id", nullable = false)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public PaymentType getType() {
        return type;
    }

    @Enumerated(EnumType.STRING)
    public void setType(PaymentType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
    }

    @Basic
    @Column(name = "selected_method")
    public Integer getSelectedMethod() {
        return selectedMethod;
    }

    public void setSelectedMethod(Integer selectedMethod) {
        this.selectedMethod = selectedMethod;
    }

    @Transient
    public List<PaymentStatusEntity> getPaymentStatusEntity() {
        return paymentStatusEntity;
    }

    public void setPaymentStatusEntity(List<PaymentStatusEntity> paymentStatusEntity) {
        this.paymentStatusEntity = paymentStatusEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionId, orderId, type, createdAt);
    }
}

