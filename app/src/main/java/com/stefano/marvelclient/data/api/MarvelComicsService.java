package com.stefano.marvelclient.data.api;

import com.stefano.marvelclient.data.model.ComicsResponse;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface MarvelComicsService {
    @GET("/v1/public/comics")
    Observable<ComicsResponse> getComicsFromNetwork(@Query("offset") int offset, @Query("limit") int limit);

}
