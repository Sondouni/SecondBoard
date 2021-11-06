package com.example.secondboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.secondboard.connection.BoardVO;
import com.example.secondboard.connection.RetrofitSV;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardWriteActivity extends AppCompatActivity {
    private EditText etTitle,etCtnt,etWrite;
    private Button btnWrite,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        etTitle = findViewById(R.id.etTitle);
        etCtnt = findViewById(R.id.etCtnt);
        etWrite = findViewById(R.id.etWrite);
        btnWrite = findViewById(R.id.btnWrite);
        btnCancel = findViewById(R.id.btnCancel);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWrite();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendWrite(){
        BoardVO vo = new BoardVO();
        vo.setTitle(etTitle.getText().toString());
        vo.setCtnt(etCtnt.getText().toString());
        vo.setWriter(etWrite.getText().toString());
        RetrofitSV.getConnect().insBoard(vo).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    private void mapWrite(View v){
        BoardVO vo = new BoardVO();
        vo.setTitle(etTitle.getText().toString());
        vo.setCtnt(etCtnt.getText().toString());
        vo.setWriter(etWrite.getText().toString());
        RetrofitSV.getConnect().insBoard1(vo).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String,String> map = response.body();
                String result = map.get("result");
                switch (result){
                    case "1":
                        finish();
                        break;
                    default:
                        //todo 키보드 내리기
                        Snackbar.make(v,R.string.msg_fial,Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }
}