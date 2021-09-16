package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProgressHistoryAdapter extends RecyclerView.Adapter<ProgressHistoryAdapter.ProgressViewHolder>{
    Context context;
    ArrayList<Progress> list;

    public ProgressHistoryAdapter(Context context, ArrayList<Progress> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item,parent,false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProgressViewHolder holder, int position) {
        Progress progress = list.get(position);
        holder.category.setText(progress.getCategory());
        holder.questions.setText(progress.getQuestions());
        holder.answered.setText(progress.getAnswered());
        holder.unanswered.setText(progress.getUnanswered());
        holder.correct.setText(progress.getCorrect());
        holder.wrong.setText(progress.getWrong());
        holder.score.setText(progress.getScore());
        holder.DateTxt.setText(progress.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder{
        TextView category,questions,answered,unanswered,correct,wrong,score,DateTxt;

        public ProgressViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.quizCategory);
            questions = itemView.findViewById(R.id.questionCount);
            answered = itemView.findViewById(R.id.questionAttempt);
            unanswered = itemView.findViewById(R.id.questionUnAttempt);
            correct = itemView.findViewById(R.id.correct);
            wrong = itemView.findViewById(R.id.wrong);
            score = itemView.findViewById(R.id.scoretxt);
            DateTxt = itemView.findViewById(R.id.datetxt);
        }
    }
}
