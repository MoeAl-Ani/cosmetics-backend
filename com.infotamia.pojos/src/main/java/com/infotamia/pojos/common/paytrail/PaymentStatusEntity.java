package com.infotamia.pojos.common.paytrail;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Mohammed Al-Ani
 **/
@Entity
@Table(name = "payment_status")
public class PaymentStatusEntity implements Comparable<PaymentStatusEntity> {
    private Long id;
    private Long paymentId;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "payment_id", nullable = false)
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public PaymentStatus getStatus() {
        return status;
    }

    @Enumerated(EnumType.STRING)
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentStatusEntity that = (PaymentStatusEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentId, status, createdAt);
    }

    @Override
    public int compareTo(PaymentStatusEntity paymentStatusEntity) {
        return Comparator.comparing(PaymentStatusEntity::getCreatedAt, Comparator.reverseOrder())
                .compare(this, paymentStatusEntity);
    }
}

