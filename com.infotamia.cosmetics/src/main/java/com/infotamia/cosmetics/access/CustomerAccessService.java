package com.infotamia.cosmetics.access;

import com.infotamia.access.AccessService;
import com.infotamia.base.rest.AbstractResourceHandler;
import com.infotamia.cosmetics.rest.order.OrderResource;
import com.infotamia.cosmetics.rest.payment.PayTrailPaymentResource;
import com.infotamia.cosmetics.rest.product.ProductResource;
import com.infotamia.cosmetics.rest.user.login.FacebookCustomerLoginResource;
import com.infotamia.cosmetics.rest.user.UserResource;

import java.util.Collection;
import java.util.HashSet;

/**
 * Access service for the cosmetic user principal.
 *
 * @author Mohammed Al-Ani
 */
public class CustomerAccessService extends AccessService {

    private final Collection<Class<? extends AbstractResourceHandler>> visibleResources;

    public CustomerAccessService() {
        visibleResources = new HashSet<>();
        visibleResources.add(FacebookCustomerLoginResource.class);
        visibleResources.add(UserResource.class);
        visibleResources.add(ProductResource.class);
        visibleResources.add(OrderResource.class);
        visibleResources.add(PayTrailPaymentResource.class);
    }

    /**
     * test whether the current user principal has access to the resource.
     *
     * @param resourceClass
     * @return
     */
    @Override
    public Boolean hasAccessToResource(Class<? extends AbstractResourceHandler> resourceClass) {
        if (resourceClass.isSynthetic()) {
            Class<?> superclass = resourceClass.getSuperclass();
            return visibleResources.contains(superclass);
        }
        return visibleResources.contains(resourceClass);
    }
}
