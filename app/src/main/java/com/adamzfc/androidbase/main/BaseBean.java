package com.adamzfc.androidbase.main;

/**
 * Created by adamzfc on 3/17/17.
 */
@SuppressWarnings({"checkstyle:javadocmethod", "javadoctype", "javadocvariable", "membername"})
public class BaseBean<T> {
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
