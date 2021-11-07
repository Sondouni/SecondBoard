package com.example.secondboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.secondboard.connection.BoardVO;
import com.example.secondboard.connection.RetrofitSV;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntotheBoardActivity extends AppCompatActivity {
    TextView tvTitle,tvCtnt,tvWriter,tvRDT,txlikeNum;
    EditText edTitle,edCtnt,edWriter,edRDT;
    Button btnChange,btnDel,btnBack,btnOkay,btnLike,BtnDlike;
    ConstraintLayout ly1,ly2;
    private int iboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intothe_board);

        tvTitle = findViewById(R.id.tvTitle);
        tvCtnt = findViewById(R.id.tvCtnt);
        tvWriter = findViewById(R.id.tvWriter);
        tvRDT = findViewById(R.id.tvRDT);
        btnBack = findViewById(R.id.btnBack);
        btnDel = findViewById(R.id.btnDel);
        btnChange = findViewById(R.id.btnChange);
        edTitle = findViewById(R.id.edTitle);
        edCtnt = findViewById(R.id.edCtnt);
        edWriter = findViewById(R.id.edWriter);
        edRDT = findViewById(R.id.edRDT);
        btnOkay = findViewById(R.id.btnOkay);
        btnLike = findViewById(R.id.btnLike);
        BtnDlike = findViewById(R.id.BtnDlike);
        txlikeNum = findViewById(R.id.txlikeNum);

        ly1 = findViewById(R.id.ly1);
        ly2 = findViewById(R.id.ly2);
        /*
        Intent intent = getIntent();
        BoardVO vo = (BoardVO)intent.getSerializableExtra("BoardVO");
        iboard = vo.getIboard();
        tvTitle.setText(vo.getTitle());
        tvCtnt.setText(vo.getCtnt());
        tvWriter.setText(vo.getWriter());
        tvRDT.setText(vo.getRdt());
        txlikeNum.setText(vo.getHeart()+"");


         */

        Intent intent = getIntent();
        BoardVO vo = (BoardVO)intent.getSerializableExtra("BoardVO");
        iboard = vo.getIboard();
        oneShow(iboard);



        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clkHeart(iboard,v);
            }
        });
        BtnDlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clkHeart(iboard,v);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure to Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                RetrofitSV.getConnect().delBoard(iboard).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null);
                ad.create().show();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly1.setVisibility(View.GONE);
                ly2.setVisibility(View.VISIBLE);
                edTitle.setText(tvTitle.getText().toString());
                edCtnt.setText(tvCtnt.getText().toString());
                edWriter.setText(tvWriter.getText().toString());
                edRDT.setText(getTime());
                btnOkay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoardVO vo = new BoardVO();
                        vo.setTitle(edTitle.getText().toString());
                        vo.setWriter(edWriter.getText().toString());
                        vo.setCtnt(edCtnt.getText().toString());
                        vo.setRdt(edRDT.getText().toString());
                        vo.setHeart(Integer.parseInt(txlikeNum.getText().toString()));
                        vo.setIboard(iboard);
                        RetrofitSV.getConnect().updBoard(vo).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                //질문 눌럿을때 바로 새로통신을 하는 oneShow를 했는데, 첫번째에 안나오고 두번째에 나왓다.
                                //이건 비동기 상황에서 레이아웃이 전환되는것보다 늦어서 생기는 일인가??
                                if(response.isSuccessful()){
                                    tvTitle.setText(vo.getTitle());
                                    tvWriter.setText(vo.getWriter());
                                    tvCtnt.setText(vo.getCtnt());
                                    tvTitle.setText(vo.getTitle());
                                    txlikeNum.setText(vo.getHeart()+"");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        ly2.setVisibility(View.GONE);
                        ly1.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }
    public void onStart(){
        super.onStart();
        oneShow(iboard);
    }
    private void oneShow(int iboard){
        RetrofitSV.getConnect().selOneBoard(iboard).enqueue(new Callback<BoardVO>() {
            @Override
            public void onResponse(Call<BoardVO> call, Response<BoardVO> response) {
                BoardVO vo = response.body();
                tvTitle.setText(vo.getTitle());
                tvCtnt.setText(vo.getCtnt());
                tvWriter.setText(vo.getWriter());
                tvRDT.setText(vo.getRdt());
                txlikeNum.setText(vo.getHeart()+"");
            }

            @Override
            public void onFailure(Call<BoardVO> call, Throwable t) {

            }
        });
    }
    private String getTime(){
        long mNow = System.currentTimeMillis();
        Date mDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return mFormat.format(mDate);
    }
    private void clkHeart(int iboard,View v){
        BoardVO vo = new BoardVO();
        vo.setIboard(iboard);
        int i = Integer.parseInt(txlikeNum.getText().toString());
        switch (v.getId()){
            case R.id.btnLike:
                vo.setHeart(++i);
                break;
            case R.id.BtnDlike:
                vo.setHeart(--i);
                break;
        }
        txlikeNum.setText(vo.getHeart()+"");

        RetrofitSV.getConnect().updBoard(vo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }
}