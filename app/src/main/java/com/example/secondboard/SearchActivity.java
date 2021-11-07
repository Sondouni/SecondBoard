package com.example.secondboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.secondboard.connection.BoardVO;
import com.example.secondboard.connection.RetrofitSV;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText edSearch;
    private Spinner spinS;
    private RecyclerView rvList;
    private Button btnBack,btnWrite,btnSearch;
    private List<String> list;
    private TextView tvspinner;
    private String search,value;
    private BoardAdapter Bdadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edSearch = findViewById(R.id.edSearch);
        spinS = findViewById(R.id.spinS);
        rvList = findViewById(R.id.rvList);
        btnBack = findViewById(R.id.btnBack);
        btnWrite = findViewById(R.id.btnWrite);
        btnSearch = findViewById(R.id.btnSearch);
        Bdadapter = new BoardAdapter();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),BoardWriteActivity.class);
                startActivity(intent);
            }
        });
        rvList.setAdapter(Bdadapter);
//        list.add(getResources().getString(R.string.title));
//        list.add(getResources().getString(R.string.write));
//        list.add(getResources().getString(R.string.num));
//        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.list, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinS.setAdapter(adapter);
        spinS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            //선택안된다면, 디폴트값?
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchConnect();
            }
        });
    }
    public void onStart(){
        super.onStart();
        searchConnect();
    }
    public void searchConnect(){
        search = spinS.getSelectedItem().toString();
        value = edSearch.getText().toString();
        RetrofitSV.getConnect().selectList(search,value).enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if(response.isSuccessful()) {
                    List<BoardVO> list = response.body();
                    for (BoardVO vo : list) {
                        Log.i("myLog", vo.getTitle());
                    }
                    Bdadapter.setList(list);
                    Bdadapter.notifyDataSetChanged();
                }
                Log.i("myLog","Datafail");
            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                Log.i("myLog","fail");
            }
        });
    }
}