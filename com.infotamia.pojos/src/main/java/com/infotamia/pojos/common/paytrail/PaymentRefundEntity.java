package com.infotamia.pojos.common.paytrail;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "payment_refund")
public class PaymentRefundEntity {
    private Integer id;
    private Long paymentId;
    private String refundToken;
    private RefundStatus refundStatus;
    private LocalDateTime createdAt;
    private PaymentEntity paymentEntity;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "payment_id")
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Basic
    @Column(name = "refund_token")
    public String getRefundToken() {
        return refundToken;
    }

    public void setRefundToken(String refundToken) {
        this.refundToken = refundToken;
    }

    @Basic
    @Column(name = "refund_status")
    @Enumerated(EnumType.STRING)
    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    @Enumerated(EnumType.STRING)
    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Basic
    @Column(name = "created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Transient
    public PaymentEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(PaymentEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRefundEntity that = (PaymentRefundEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(refundToken, that.refundToken) &&
                Objects.equals(refundStatus, that.refundStatus) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentId, refundToken, refundStatus, createdAt);
    }
}

