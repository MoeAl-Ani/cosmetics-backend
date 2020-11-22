package com.infotamia.cosmetics.utils;

import com.infotamia.cosmetics.daos.ProductDao;
import com.infotamia.cosmetics.factory.AbstractCDIFactory;
import com.infotamia.cosmetics.provider.UriInfoProvider;
import com.infotamia.cosmetics.rest.order.OrderResource;
import com.infotamia.cosmetics.services.order.OrderService;
import com.infotamia.pojos.entities.OrderDetailsEntity;
import com.infotamia.pojos.entities.OrderProductEntity;
import com.infotamia.pojos.entities.ProductEntity;
import com.infotamia.pojos.entities.ProductTranslationEntity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Mohammed Al-Ani
 **/
public class OrderHtmlUtils {
    public static final String TEMPLATE_FILE = "templates/customer_order_confirmation_message";
    public static final String PATTERN = "(\\[\\[(.+)\\]\\])";

    private OrderHtmlUtils() {
        //
    }


    public static String convertOrderToCustomerConfirmationMessage(OrderDetailsEntity order) {

        UriInfo uriInfo = AbstractCDIFactory.select(UriInfoProvider.class).provide();
        String orderUri = UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderResource.class).path(order.getOrderReference()).scheme("https").build().toString();
        ProductDao productDao = AbstractCDIFactory.select(ProductDao.class);
        Set<Integer> pIds = order.getSelectedProducts().stream()
                .map(OrderProductEntity::getProductId)
                .collect(Collectors.toSet());
        Map<Integer, ProductEntity> pMap = productDao.findProductsByIds(pIds).stream().flatMap(Collection::stream)
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));
        StringBuilder sb = new StringBuilder();
        sb.append("<tbody>\n");
        for (OrderProductEntity selectedProduct : order.getSelectedProducts()) {
            ProductEntity productEntity = pMap.get(selectedProduct.getProductId());
            String productImageUrl = productEntity.getImages().stream().findFirst().orElse("");
            String productName = productEntity.getProductTranslations()
                    .stream()
                    .filter(p -> p.getLanguageId() == 1)
                    .map(ProductTranslationEntity::getName)
                    .findFirst()
                    .orElse("");
            String productPrice = String.valueOf(pMap.get(selectedProduct.getProductId()).getPrice() * selectedProduct.getQuantity());
            String productQuantity = String.valueOf(selectedProduct.getQuantity());
            sb.append("<tr style=\"border-top:1px solid #dfe6e9;font-family:'proxima-soft',sans-serif\">\n");
            sb.append("<td style=\"width:1%;padding-left:16px;font-family:'proxima-soft',sans-serif\">\n");
            sb.append("<img src=\"").append(productImageUrl).append("\"style=\"max-height:80px;max-width:64px;font-family:'proxima-soft',sans-serif\" alt=\"Product image\" class=\"CToWUd\">\n");
            sb.append("</td>");
            sb.append("<td style=\"padding-left:12px;padding-right:16px;font-family:'proxima-soft',sans-serif\">\n");
            sb.append("<p style=\"margin:12px 0 0 0;line-height:16px;font-family:'proxima-soft',sans-serif\">").append(productName).append("</p>\n");
            sb.append("<p style=\"float:left;margin-top:12px;font-size:12px;line-height:16px;font-family:'proxima-soft',sans-serif\">Qty: ").append(productQuantity).append("</p>\n");
            sb.append("<p style=\"float:right;margin-top:8px;font-family:'proxima-soft',sans-serif\">€").append(productPrice).append("</p>\n");
            sb.append("</td>\n");
            sb.append("</tr>\n");
        }
        sb.append("</tbody>\n");

        Map<String, String> map = new HashMap<>();
        map.put("ORDER_NUMBER", order.getOrderReference());
        map.put("ORDER_LINK", orderUri);
        map.put("TABLE_BODY", sb.toString());
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                OrderHtmlUtils.class.getClassLoader()
                                        .getResourceAsStream(TEMPLATE_FILE))))) {
            String template = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                String groupToken = matcher.group();
                String groupKey = matcher.group(2);
                if (map.get(groupKey) == null) {
                    continue;
                }
                template = template.replace(groupToken, map.get(groupKey));
            }
            return template;

        } catch (IOException e) {
            return null;
        }
    }
}
