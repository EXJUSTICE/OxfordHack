package com.xu.hookmeup.Service;

import com.xu.hookmeup.Model.FbPicture;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by marci on 04.08.2016.
 */
public interface FbService {
    String BASE_URL = "https://graph.facebook.com/v2.8/";

    @GET("/{id}/picture")
    Observable<Response> getPicture(@Path("id") String id, @QueryMap Map<String, String> params);
}
