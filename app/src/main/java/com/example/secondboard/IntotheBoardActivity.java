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
    TextView tvTitle,tvCtnt,tvWriter,tvRDT;
    EditText edTitle,edCtnt,edWriter,edRDT;
    Button btnChange,btnDel,btnBack,btnOkay;
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
        ly1 = findViewById(R.id.ly1);
        ly2 = findViewById(R.id.ly2);

        Intent intent = getIntent();
        BoardVO vo = (BoardVO)intent.getSerializableExtra("BoardVO");
        iboard = vo.getIboard();
        tvTitle.setText(vo.getTitle());
        tvCtnt.setText(vo.getCtnt());
        tvWriter.setText(vo.getWriter());
        tvRDT.setText(vo.getRdt());

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
                Log.i("myLog",getTime());
                btnOkay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoardVO vo = new BoardVO();
                        vo.setTitle(edTitle.getText().toString());
                        vo.setWriter(edWriter.getText().toString());
                        vo.setCtnt(edCtnt.getText().toString());
                        vo.setRdt(edRDT.getText().toString());
                        vo.setIboard(iboard);
                        RetrofitSV.getConnect().updBoard(vo).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        ly2.setVisibility(View.GONE);
                        ly1.setVisibility(View.VISIBLE);
                        oneShow(iboard);
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
}