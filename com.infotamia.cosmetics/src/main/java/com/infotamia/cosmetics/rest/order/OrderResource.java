package com.infotamia.cosmetics.rest.order;

import com.infotamia.base.rest.AbstractResourceHandler;
import com.infotamia.cosmetics.services.order.OrderService;
import com.infotamia.cosmetics.transaction.CosmeticsTransactional;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.pojos.entities.OrderDetailsEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Mohammed Al-Ani
 **/
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("orders")
@RequestScoped
public class OrderResource implements AbstractResourceHandler {
    @Inject
    private OrderService orderService;
    public OrderResource() {
        //
    }

    @GET
    @Path("{orderReference}")
    public OrderDetailsEntity getOrder(@PathParam("orderReference") String orderReference) throws ItemNotFoundException {
        return orderService.fetchOrder(orderReference);
    }

    @POST
    @CosmeticsTransactional
    public Response postOrder(OrderDetailsEntity orderPayload) throws IncorrectParameterException {
        // create order
        return Response.status(Response.Status.CREATED)
                .entity(orderService.createOrder(orderPayload))
                .build();
    }
}
