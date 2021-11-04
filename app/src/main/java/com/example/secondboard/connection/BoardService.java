package com.example.secondboard.connection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BoardService {
    @GET("list")
    Call<List<BoardVO>> selectList();

}
