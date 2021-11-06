package com.example.secondboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.secondboard.connection.BoardVO;
import com.example.secondboard.connection.RetrofitSV;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardMainActivity extends AppCompatActivity {
    private BoardAdapter adp;
    private RecyclerView rvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_main);
        rvList = findViewById(R.id.rvList);
        adp = new BoardAdapter();
        rvList.setAdapter(adp);
        network();
    }
    public void onStart(){
        super.onStart();
        network();
    }
    public void clkWrite(View v){
        Intent intent = new Intent(this,BoardWriteActivity.class);
        startActivity(intent);
    }
    private void network(){
        RetrofitSV.getConnect().selectList().enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if(response.isSuccessful()){
                    List<BoardVO> list = response.body();
                    Collections.reverse(list);
                    adp.setList(list);
                    adp.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                Log.i("myLog","실패" + t.getMessage());
            }
        });
    }
    public void clkSearch(View v){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }
}
class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.MyViewHolder>{
    private List<BoardVO> list;
    public void setList(List<BoardVO> list){this.list = list;}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_main,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(list.get(position),list.size()-position);

        BoardVO vo = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),IntotheBoardActivity.class);
                intent.putExtra("BoardVO",vo);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null? 0:list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIboard,tvTitle,tvWriter,tvRDT;
        public MyViewHolder(View v){
            super(v);
            tvIboard = v.findViewById(R.id.tvIboard);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvWriter = v.findViewById(R.id.tvWriter);
            tvRDT = v.findViewById(R.id.tvRDT);
        }
        public void setItem(BoardVO param,int position){
            tvIboard.setText(String.valueOf(position));
            tvTitle.setText(param.getTitle());
            tvWriter.setText(param.getWriter());
            tvRDT.setText(param.getRdt());
        }
    }
}