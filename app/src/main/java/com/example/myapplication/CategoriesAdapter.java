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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<String> categoryList;

    public CategoriesAdapter(Context context, ArrayList<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {
        String category = categoryList.get(position);
        holder.categoryText.setText(category);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView categoryText;
        public CategoryViewHolder(@NonNull @NotNull View itemView,onItemClickListener listener) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryTxt);
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
        }
    }

}
