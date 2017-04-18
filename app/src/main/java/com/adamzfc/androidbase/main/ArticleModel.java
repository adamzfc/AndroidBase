package com.adamzfc.androidbase.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 3/17/17.
 */
@SuppressWarnings({"checkstyle:javadocmethod", "javadoctype", "javadocvariable"})
public interface ArticleModel {
    String TABLE_NAME = "Articles";
    String CREATE_TABLE = ""
            + "CREATE TABLE Articles (\n"
            + "objectId VARCHAR(20) PRIMARY KEY,\n"
            + "title TEXT NOT NULL, \n"
            + "content TEXT NOT NULL, \n"
            + "image TEXT\n"
            + "createdAt TEXT\n"
            + ")";

    @Nullable
    String objectId();

    @NonNull
    String title();

    @NonNull
    String content();

    @Nullable
    String image();

}
