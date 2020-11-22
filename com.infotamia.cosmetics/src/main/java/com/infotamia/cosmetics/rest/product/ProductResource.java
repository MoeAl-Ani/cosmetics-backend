package com.infotamia.cosmetics.rest.product;

import com.infotamia.base.rest.AbstractResourceHandler;
import com.infotamia.cosmetics.daos.CategoryDao;
import com.infotamia.cosmetics.daos.ProductDao;
import com.infotamia.pojos.entities.CategoryEntity;
import com.infotamia.pojos.entities.ProductEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mohammed Al-Ani
 **/
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProductResource implements AbstractResourceHandler {

    public ProductResource() {
        //
    }

    @Inject
    private ProductDao productDao;
    @Inject
    private CategoryDao categoryDao;

    @GET
    public Collection<ProductEntity> getProducts() {
        List<ProductEntity> products = productDao.findAll().orElse(new ArrayList<>());
        Map<Integer, CategoryEntity> catMap = categoryDao.fetchCategoriesByIds(
                products.stream().map(ProductEntity::getId).collect(Collectors.toSet()))
                .stream().flatMap(Collection::stream)
                .collect(Collectors.toMap(CategoryEntity::getId, Function.identity()));
        products.forEach(p -> {
            if (catMap.containsKey(p.getCategoryId()))
                p.setCategory(catMap.get(p.getCategoryId()));
        });
        return products;
    }

    @GET
    @Path("{id}")
    public ProductEntity getProduct(@PathParam("id") Integer id) {
        return productDao.findOneByProductId(id).orElse(null);
    }

}
