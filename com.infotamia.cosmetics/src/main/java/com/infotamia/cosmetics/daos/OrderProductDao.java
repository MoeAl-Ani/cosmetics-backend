package com.infotamia.cosmetics.daos;

import com.infotamia.pojos.entities.OrderProductEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class OrderProductDao {

    @Inject
    private Session session;
    public OrderProductDao() {
        //
    }

    public List<OrderProductEntity> insertAll(List<OrderProductEntity> products) {
        for (OrderProductEntity product : products) {
            session.persist(product);
        }
        return products;
    }

    public Optional<Set<OrderProductEntity>> findSelectedProducts(Long orderId) {
        List<OrderProductEntity> resultList = session.createSQLQuery("select {p.*} from order_product p " +
                "where p.order_id = :orderId")
                .addEntity("p", OrderProductEntity.class)
                .setParameter("orderId", orderId)
                .getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(new HashSet<>(resultList));
    }

    public String toString() {
        return super.toString();
    }
}
