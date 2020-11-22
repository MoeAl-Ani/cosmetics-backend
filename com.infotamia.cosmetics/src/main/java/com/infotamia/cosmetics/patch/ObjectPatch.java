package com.infotamia.cosmetics.patch;

/**
 * @author Mohammed Al-Ani
 */
public interface ObjectPatch {
    <T> T apply(T t);
}
