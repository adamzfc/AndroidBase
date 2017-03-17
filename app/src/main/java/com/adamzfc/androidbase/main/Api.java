package com.adamzfc.androidbase.main;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by adamzfc on 3/17/17.
 */

public interface Api {
    @Headers({
            "X-Bmob-REST-API-Key:e34dffa7bdc1caa95e31cc1c4ed6a7e0",
            "X-Bmob-Application-Id:126d4d9ca2400971db8b0e3126581167",
            "Content-Type:application/json"
            })
    @GET("ListTable")
    Observable<BaseBean<List<ListTable>>> getList();
}

