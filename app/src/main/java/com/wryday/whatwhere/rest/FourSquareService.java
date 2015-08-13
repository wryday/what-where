package com.wryday.whatwhere.rest;

import retrofit.http.GET;
import retrofit.http.Query;

public interface FourSquareService {

    @GET("venues/search")
    FourSquareResponse searchForVenues(@Query("near") String place,
                             @Query("oauth_token") String token,
                             @Query("v") long v);

}
