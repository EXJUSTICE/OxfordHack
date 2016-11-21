package com.xu.hookmeup.Service;

import com.xu.hookmeup.Model.Event;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by marci on 04.08.2016.
 */
public interface MahService {
    String BASE_URL = "http://da8a072c.ngrok.io/";

    @GET("/request")
    Observable<List<Event>> getEvents(@QueryMap Map<String, String> params);
}
