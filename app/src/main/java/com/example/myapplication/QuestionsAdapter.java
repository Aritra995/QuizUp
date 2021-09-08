package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Questions> list;

    public QuestionsAdapter(Context context, ArrayList<Questions> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Questions question = list.get(position);
        holder.question.setText(question.getStatement());
        holder.a1.setText(question.getA1());
        holder.a2.setText(question.getA2());
        holder.a3.setText(question.getA3());
        holder.a4.setText(question.getA4());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        RadioButton a1,a2,a3,a4;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            a1 = itemView.findViewById(R.id.option1);
            a2 = itemView.findViewById(R.id.option2);
            a3 = itemView.findViewById(R.id.option3);
            a4 = itemView.findViewById(R.id.option4);
        }
    }
}
