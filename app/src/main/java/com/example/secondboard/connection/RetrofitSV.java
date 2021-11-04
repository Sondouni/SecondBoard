package com.example.secondboard.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSV {
    private static Retrofit rt;
    private static BoardService bs;
    public static BoardService getConnect(){
        if(rt==null){
            rt = new Retrofit.Builder()
                    .baseUrl("http://192.168.3.52:8090/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            bs = rt.create(BoardService.class);
        }
        return bs;
    }
}
