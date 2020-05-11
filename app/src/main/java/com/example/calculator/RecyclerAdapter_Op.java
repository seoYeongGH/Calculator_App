package com.example.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter_Op extends RecyclerView.Adapter<RecyclerAdapter_Op.ViewHolder> {
    ArrayList<String> btnTexts;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btnOp;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            btnOp = itemView.findViewById(R.id.btnOp);
        }

        void setItem(String text)
        {
            btnOp.setText(text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View btnView = inflater.inflate(R.layout.operation_view, parent, false);

        return new ViewHolder(btnView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Op.ViewHolder holder, int position)
    {
        String text = btnTexts.get(position);
        holder.setItem(text);
    }

    @Override
    public int getItemCount() {
        if(btnTexts == null)
            btnTexts = new ArrayList<String>();

        return btnTexts.size();
    }

    public void setItems(ArrayList<String> items)
    {
        btnTexts = items;
    }
}
