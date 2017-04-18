package com.adamzfc.androidbase.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by adamzfc on 3/17/17.
 */
@SuppressWarnings({"checkstyle:javadocmethod", "javadoctype"})
@AutoValue
public abstract class Article implements ArticleModel {

    public static Builder builder() {
        return new AutoValue_Article.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder objectId(@Nullable String objectId);

        public abstract Builder title(@NonNull String title);

        public abstract Builder content(@NonNull String content);

        public abstract Builder image(@NonNull String image);

        public abstract Article build();
    }
}
