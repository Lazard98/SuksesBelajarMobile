package com.example.irfan.suksesbelajar.mMySQL;

import com.example.irfan.suksesbelajar.mDataObject.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL ="http://suksesbelajar.id/android/";

    @GET("tampilOwner.php")
    Call<List<Owner>> listOwner();


}
