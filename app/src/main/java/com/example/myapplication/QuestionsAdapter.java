package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Questions question = list.get(position);
        holder.question.setText(question.getStatement());
        holder.a1.setText(question.getA1());
        holder.a2.setText(question.getA2());
        holder.a3.setText(question.getA3());
        holder.a4.setText(question.getA4());

        if( holder.a1.isChecked() ){
            holder.a2.setChecked(false);
            holder.a3.setChecked(false);
            holder.a4.setChecked(false);
        }
        else if(holder.a2.isChecked()){
            holder.a1.setChecked(false);
            holder.a3.setChecked(false);
            holder.a4.setChecked(false);
        }
        else if(holder.a3.isChecked()){
            holder.a1.setChecked(false);
            holder.a2.setChecked(false);
            holder.a4.setChecked(false);
        }
        else if(holder.a4.isChecked()){
            holder.a1.setChecked(false);
            holder.a2.setChecked(false);
            holder.a3.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int position);
        void onA1Click(int position);
        void onA2Click(int position);
        void onA3Click(int position);
        void onA4Click(int position);
    }

    public void setonItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        RadioButton a1,a2,a3,a4;
        RadioGroup optionRadioGroup;
        static ArrayList<String> answers;

        public static ArrayList<String> getAnswers() {
            return answers;
        }

        public RadioButton getA1() {
            return a1;
        }

        public RadioButton getA2() {
            return a2;
        }

        public RadioButton getA3() {
            return a3;
        }

        public RadioButton getA4() {
            return a4;
        }

        public MyViewHolder(@NonNull @NotNull View itemView,onItemClickListener listener) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            a1 = itemView.findViewById(R.id.option1);
            a2 = itemView.findViewById(R.id.option2);
            a3 = itemView.findViewById(R.id.option3);
            a4 = itemView.findViewById(R.id.option4);
            optionRadioGroup = itemView.findViewById(R.id.optionRadioGroup);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onA1Click(position);
                        }
                    }
                }
            });
            a2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onA2Click(position);
                        }
                    }
                }
            });
            a3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onA3Click(position);
                        }
                    }
                }
            });
            a4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( listener != null ){
                        int position = getAdapterPosition();
                        if( position != RecyclerView.NO_POSITION ){
                            listener.onA4Click(position);
                        }
                    }
                }
            });
        }
    }

}
