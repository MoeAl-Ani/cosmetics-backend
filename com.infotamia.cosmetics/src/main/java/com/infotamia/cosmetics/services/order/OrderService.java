package com.infotamia.cosmetics.services.order;

import com.google.common.util.concurrent.AtomicDouble;
import com.infotamia.cosmetics.daos.*;
import com.infotamia.cosmetics.utils.EmailValidatorUtils;
import com.infotamia.cosmetics.utils.OrderHtmlUtils;
import com.infotamia.cosmetics.utils.PhoneNumberUtils;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.pojos.entities.OrderDetailsEntity;
import com.infotamia.pojos.entities.OrderProductEntity;
import com.infotamia.pojos.entities.ProductEntity;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ProductDao productDao;
    @Inject
    private OrderProductDao orderProductDao;
    @Inject
    private UserDao userDao;
    @Inject
    private AddressDao addressDao;

    public OrderService() {
        //
    }

    public OrderDetailsEntity fetchOrder(String orderReference) throws ItemNotFoundException {
        OrderDetailsEntity orderDetail = orderDao.fetchOneByOrderReference(orderReference).orElseThrow(() -> new ItemNotFoundException("order not found", BaseErrorCode.ORDER_NOT_FOUND));
        orderProductDao.findSelectedProducts(orderDetail.getId()).ifPresent(orderDetail::setSelectedProducts);
        return orderDetail;
    }
    public OrderDetailsEntity fetchOrder(Long orderId) throws ItemNotFoundException {
        OrderDetailsEntity orderDetail = orderDao.fetchOneByOrderId(orderId).orElseThrow(() -> new ItemNotFoundException("order not found", BaseErrorCode.ORDER_NOT_FOUND));
        orderProductDao.findSelectedProducts(orderDetail.getId()).ifPresent(orderDetail::setSelectedProducts);
        return orderDetail;
    }

    public OrderDetailsEntity createOrder(OrderDetailsEntity payload) throws IncorrectParameterException {
        if (payload == null) throw new IncorrectParameterException("order was null", BaseErrorCode.ORDER_PAYLOAD_IS_NULL);
        if (CollectionUtils.isEmpty(payload.getSelectedProducts()))
            throw new IncorrectParameterException("order items were empty", BaseErrorCode.ORDER_ITEMS_WERE_EMPTY);
        if (payload.getSelectedProducts().stream().anyMatch(e -> e.getQuantity() == null || e.getQuantity() <= 0))
            throw new IncorrectParameterException("product count was wrong", BaseErrorCode.ORDER_ITEMS_COUNT_WERE_WRONG);
        if (!EmailValidatorUtils.isValid(payload.getEmail()))
            throw new IncorrectParameterException("email was wrong", BaseErrorCode.USER_EMAIL_INCORRECT);
        if (!PhoneNumberUtils.isValid(payload.getPhoneNumber(), "IQ"))
            throw new IncorrectParameterException("phone number was wrong", BaseErrorCode.USER_PHONE_INCORRECT);

        Set<Integer> pIds = payload.getSelectedProducts()
                .stream()
                .filter(e -> e.getProductId() != null && e.getProductId() > 0)
                .map(OrderProductEntity::getProductId)
                .collect(Collectors.toSet());
        if (payload.getAddress() == null)
            throw new IncorrectParameterException("address entity was null", BaseErrorCode.ORDER_ADDRESS_MISSING);
        if (StringUtils.isBlank(payload.getAddress().getCity()))
            throw new IncorrectParameterException("address city was null", BaseErrorCode.ORDER_ADDRESS_CITY_MISSING);
        if (StringUtils.isBlank(payload.getAddress().getCountry()))
            throw new IncorrectParameterException("address country was null", BaseErrorCode.ORDER_ADDRESS_COUNTRY_MISSING);
        if (StringUtils.isBlank(payload.getAddress().getStreet()))
            throw new IncorrectParameterException("address street was null", BaseErrorCode.ORDER_ADDRESS_STREET_MISSING);
        if (pIds.isEmpty() || pIds.size() != payload.getSelectedProducts().size())
            throw new IncorrectParameterException("given products ids were wrong", BaseErrorCode.ORDER_ITEMS_PRODUCTS_IDS_WERE_WRONG);
        productDao.findProductsByIds(pIds).ifPresent(p -> {
            // create order, products
            payload.setId(null);
            userDao.findUserByEmail(payload.getEmail().toLowerCase().trim())
                    .ifPresentOrElse(u -> payload.setUserId(u.getId()), () -> {
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(payload.getEmail().toLowerCase().trim());
                userEntity.setPhoneNumber(PhoneNumberUtils.format(payload.getPhoneNumber(), "IQ"));
                userEntity.setLanguageId(1);
                userEntity.setCreatedAt(LocalDateTime.now());
                userDao.insertUser(userEntity);
                payload.setUserId(userEntity.getId());
            });
            payload.setOrderReference(UUID.randomUUID().toString());
            payload.setNetAmount(calculateOrderNetAmount(p, payload.getSelectedProducts()));
            payload.setAddressId(addressDao.insert(payload.getAddress()).getId());
            payload.setCreatedAt(LocalDateTime.now());
            OrderDetailsEntity createdOrder = orderDao.insert(payload);
            payload.setId(createdOrder.getId());
            for (OrderProductEntity selectedProduct : payload.getSelectedProducts()) {
                selectedProduct.setOrderId(createdOrder.getId());
            }
            orderProductDao.insertAll(new ArrayList<>(payload.getSelectedProducts()));
        });
        return payload;
    }

    private Double calculateOrderNetAmount(List<ProductEntity> products, Set<OrderProductEntity> selectedProducts) {
        Map<Integer, Double> pMap = products.stream().collect(Collectors.toMap(ProductEntity::getId, ProductEntity::getPrice));
        AtomicDouble sum = new AtomicDouble(0.0);
        selectedProducts.forEach(sP -> {
            sum.addAndGet(sP.getQuantity() * pMap.get(sP.getProductId()));
        });
        return sum.get();
    }
}
