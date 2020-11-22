package com.infotamia.cosmetics.daos;

import com.infotamia.pojos.entities.OrderDetailsEntity;
import com.infotamia.pojos.entities.ProductEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class OrderDao {

    @Inject
    private Session session;
    public OrderDao() {
        //
    }

    public Optional<OrderDetailsEntity> fetchOneByOrderReference(String orderReference) {
        if (StringUtils.isBlank(orderReference)) return Optional.empty();
        List<Object[]> result = session.createSQLQuery("select {od.*}, {p.*} from order_details as od " +
                "join order_product op on op.order_id = od.id " +
                "join product p on p.id = op.product_id " +
                "where od.order_reference = :orderReference")
                .addEntity("od", OrderDetailsEntity.class)
                .addEntity("p", ProductEntity.class)
                .setParameter("orderReference", orderReference)
                .getResultList();
        Set<OrderDetailsEntity> oResult = new HashSet<>();
        for (Object[] objects : result) {
            OrderDetailsEntity orderDetailsEntity = null;
            ProductEntity productEntity = null;

            for (Object o : objects) {
                if (o instanceof OrderDetailsEntity) {
                    orderDetailsEntity = (OrderDetailsEntity) o;
                } else if (o instanceof ProductEntity) {
                    productEntity = (ProductEntity) o;
                }
            }

            if (orderDetailsEntity == null) return Optional.empty();
            else oResult.add(orderDetailsEntity);
            if (productEntity != null) orderDetailsEntity.getProducts().add(productEntity);
        }
        return oResult.stream().findFirst();
    }

    public Optional<OrderDetailsEntity> fetchOneByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) return Optional.empty();
        List<Object[]> result = session.createSQLQuery("select {od.*}, {p.*} from order_details as od " +
                "join order_product op on op.order_id = od.id " +
                "join product p on p.id = op.product_id " +
                "where od.id = :orderId")
                .addEntity("od", OrderDetailsEntity.class)
                .addEntity("p", ProductEntity.class)
                .setParameter("orderId", orderId)
                .getResultList();
        Set<OrderDetailsEntity> oResult = new HashSet<>();
        for (Object[] objects : result) {
            OrderDetailsEntity orderDetailsEntity = null;
            ProductEntity productEntity = null;

            for (Object o : objects) {
                if (o instanceof OrderDetailsEntity) {
                    orderDetailsEntity = (OrderDetailsEntity) o;
                } else if (o instanceof ProductEntity) {
                    productEntity = (ProductEntity) o;
                }
            }

            if (orderDetailsEntity == null) return Optional.empty();
            else oResult.add(orderDetailsEntity);
            if (productEntity != null) orderDetailsEntity.getProducts().add(productEntity);
        }
        return oResult.stream().findFirst();
    }

    public OrderDetailsEntity insert(OrderDetailsEntity orderDetailsEntity) {
        session.persist(orderDetailsEntity);
        return orderDetailsEntity;
    }




    public String toString() {
        return super.toString();
    }
}
