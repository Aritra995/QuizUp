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

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{
    Context context;
    ArrayList<String> list;

    public InstructionAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item,parent,false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InstructionViewHolder holder, int position) {
        String instruction = list.get(position);
        holder.instructionTxt.setText(instruction);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder{
        TextView instructionTxt;
        public InstructionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            instructionTxt = itemView.findViewById(R.id.instructionTxt);
        }
    }
}
