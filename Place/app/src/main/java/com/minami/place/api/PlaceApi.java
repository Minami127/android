package com.minami.place.api;

import com.minami.place.model.PlaceList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceApi {

    // 구글 nearbysearch Api 호출

    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceList> getPlaceList(@Query("location") String location,
                                 @Query("radius") int radius,
                                 @Query("language") String language,
                                 @Query("keyword") String keyword,
                                 @Query("key") String apikey);

    @GET("/maps/api/place/nearbysearch/json")
    Call<PlaceList> getPlaceList(@Query("location") String location,
                                 @Query("radius") int radius,
                                 @Query("language") String language,
                                 @Query("keyword") String keyword,
                                 @Query("pagetoken") String pageToken,
                                 @Query("key") String apikey);


}
