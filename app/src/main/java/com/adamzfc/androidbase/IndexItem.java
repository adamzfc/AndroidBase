package com.adamzfc.androidbase;

/**
 * Created by adamzfc on 4/14/17.
 */
@SuppressWarnings("checkstyle:membername")
public class IndexItem {
    private String name;
    private Class clz;

    public IndexItem(String name, Class clz) {
        this.name = name;
        this.clz = clz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }
}
