package com.minami.memoapp.api;

import com.minami.memoapp.model.Res;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST

public interface MemoApi {

    @POST("/memo")
    Call<Res> addMemo(@Header("Authorization")) String token,@Body ;

}
