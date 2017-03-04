package com.adamzfc.base.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * activity scope
 * Created by adamzfc on 3/4/17.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {}
