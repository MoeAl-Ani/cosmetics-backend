package com.infotamia.cosmetics.filters;

import com.infotamia.cosmetics.rest.user.login.FacebookCustomerLoginResource;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.collections4.list.UnmodifiableList;

import java.util.ArrayList;
import java.util.List;

/**
 * skip http requests from further processing. example request like {@link FacebookCustomerLoginResource}.
 *
 * @author Mohammed Al-Ani
 */
public class SkipFilterUtils {
    private static final UnmodifiableList<String> skippedUris;

    static {
        List<String> skip = new ArrayList<>();
        skip.add(UriBuilder.fromResource(FacebookCustomerLoginResource.class).build().toString().substring(1));
        skippedUris = new UnmodifiableList<>(skip);
    }

    private SkipFilterUtils() {
    }

    public static boolean shouldSkip(String path) {
        return skippedUris.stream().anyMatch(path::contains);
    }
}
