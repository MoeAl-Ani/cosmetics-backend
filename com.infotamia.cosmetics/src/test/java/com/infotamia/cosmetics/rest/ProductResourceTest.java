package com.infotamia.cosmetics.rest;

import com.infotamia.cosmetics.base.JerseyBase;
import com.infotamia.pojos.entities.ProductEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * @author Mohammed Al-Ani
 */
public class ProductResourceTest extends JerseyBase {


    @Test
    public void getProducts() {
        Collection<ProductEntity> products = get().target("products/")
                .jwt(null)
                .expectingStatusCode(200)
                .execute(ProductEntity.class, List.class);
        Assert.assertNotNull(products);
        Assert.assertFalse(products.isEmpty());
        Assert.assertEquals(5, products.size());
    }

    @Test
    public void getProduct() {
        ProductEntity product = get().target("products/1")
                .jwt(null)
                .expectingStatusCode(200)
                .execute(ProductEntity.class);
        Assert.assertNotNull(product);
        Assert.assertEquals(1, product.getId().intValue());
    }
}
