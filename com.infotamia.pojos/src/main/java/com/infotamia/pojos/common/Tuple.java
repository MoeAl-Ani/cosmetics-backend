package com.infotamia.pojos.common;

/**
 * @author Mohammed Al-Ani
 */
public class Tuple<T, O> {
    private T obj1;
    private O obj2;

    public Tuple(T obj1, O obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public T getObj1() {
        return obj1;
    }

    public void setObj1(T obj1) {
        this.obj1 = obj1;
    }

    public O getObj2() {
        return obj2;
    }

    public void setObj2(O obj2) {
        this.obj2 = obj2;
    }
}