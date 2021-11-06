package com.example.secondboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText edSearch;
    private Spinner spinS;
    private RecyclerView rvList;
    private Button btnBack,btnWrite,btnSearch;
    private List<String> list;
    private TextView tvspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edSearch = findViewById(R.id.edSearch);
        spinS = findViewById(R.id.spinS);
        rvList = findViewById(R.id.rvList);
        btnBack = findViewById(R.id.btnBack);
        btnWrite = findViewById(R.id.btnWrite);

//        list.add(getResources().getString(R.string.title));
//        list.add(getResources().getString(R.string.write));
//        list.add(getResources().getString(R.string.num));
//        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.list, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinS.setAdapter(adapter);
        spinS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //선택된다면,textview에 보여질거를 작성
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            //선택안된다면, 디폴트값?
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}