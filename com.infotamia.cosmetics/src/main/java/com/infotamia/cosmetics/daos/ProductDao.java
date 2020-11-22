package com.infotamia.cosmetics.daos;

import com.infotamia.pojos.entities.ProductEntity;
import com.infotamia.pojos.entities.ProductImageEntity;
import com.infotamia.pojos.entities.ProductTranslationEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class ProductDao {
    @Inject
    private Session session;

    public ProductDao() {
        //
    }

    public Optional<List<ProductEntity>> findProductsByIds(Collection<Integer> pIds) {
        List<Object[]> result = session.createSQLQuery("select {pr.*}, {pt.*}, {pi.*} from product pr " +
                "left join product_translation pt on pr.id = pt.product_id " +
                "left join product_image pi on pr.id = pi.product_id " +
                "where pr.id in (:pIds) and pr.available")
                .addEntity("pr", ProductEntity.class)
                .addEntity("pt", ProductTranslationEntity.class)
                .addEntity("pi", ProductImageEntity.class)
                .setParameter("pIds", pIds)
                .getResultList();
        return getProductEntities(result);
    }

    public Optional<List<ProductEntity>> findAll() {
        List<Object[]> result = session.createSQLQuery("select {pr.*}, {pt.*}, {pi.*} from product pr " +
                "left join product_translation pt on pr.id = pt.product_id " +
                "left join product_image pi on pi.product_id = pr.id")
                .addEntity("pr", ProductEntity.class)
                .addEntity("pt", ProductTranslationEntity.class)
                .addEntity("pi", ProductImageEntity.class)
                .getResultList();
        return getProductEntities(result);
    }

    public Optional<ProductEntity> findOneByProductId(Integer pId) {
        List<Object[]> result = session.createSQLQuery("select {pr.*}, {pt.*}, {pi.*} from product pr " +
                "left join product_translation pt on pr.id = pt.product_id " +
                "left join product_image pi on pi.product_id = pr.id " +
                "where pr.id = :pId ")
                .addEntity("pr", ProductEntity.class)
                .addEntity("pt", ProductTranslationEntity.class)
                .addEntity("pi", ProductImageEntity.class)
                .setParameter("pId", pId)
                .getResultList();
        return getProductEntities(result).orElse(new ArrayList<>()).stream().findFirst();
    }

    private Optional<List<ProductEntity>> getProductEntities(List<Object[]> result) {
        if (result.isEmpty()) return Optional.empty();

        Map<Integer, ProductEntity> resultMap = new HashMap<>();

        for (Object[] objects : result) {
            ProductEntity productEntity = null;
            ProductTranslationEntity productTranslationEntity = null;
            ProductImageEntity productImageEntity = null;

            for (Object o : objects) {
                if (o instanceof ProductEntity) productEntity = (ProductEntity) o;
                else if (o instanceof ProductTranslationEntity) productTranslationEntity = (ProductTranslationEntity) o;
                else productImageEntity = (ProductImageEntity) o;
            }

            if (productEntity != null) {
                resultMap.put(productEntity.getId(), productEntity);
                if (productTranslationEntity != null)
                    productEntity.getProductTranslations().add(productTranslationEntity);
                if (productImageEntity != null)
                    productEntity.getImages().add(productImageEntity.getImageUrl());
            }
        }

        return Optional.of(new ArrayList<>(resultMap.values()));
    }
}
