package com.example.secondboard.connection;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardService {
    @GET("list")
    Call<List<BoardVO>> selectList();

    @GET("list")
    Call<List<BoardVO>> selectList(@Query("search") String search,@Query("value") String value);

    @POST("ins")
    Call<Void> insBoard(@Body BoardVO vo);
    @POST("ins")
    Call<Map<String,String>> insBoard1(@Body BoardVO vo);

    @GET("selone")
    Call<BoardVO> selOneBoard(@Query("iboard") int iboard);

    @GET("del")
    Call<Void> delBoard(@Query("iboard") int iboard);

    @POST("upd")//todo 확인
    Call<Void> updBoard(@Body BoardVO vo);
}
