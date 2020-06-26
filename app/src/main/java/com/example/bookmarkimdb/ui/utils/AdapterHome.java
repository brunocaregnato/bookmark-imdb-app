package com.example.bookmarkimdb.ui.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.models.Watched;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {

    private List<Watched> watchedList;

    public AdapterHome(List<Watched> watchedList) {
        this.watchedList = watchedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setData(watchedList.get(position));
    }

    @Override
    public int getItemCount() {
        return watchedList.size();
    }

    //popular as tags da fragment_home aqui
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView historyText;

        public ViewHolder(@NonNull View itemView) {
             super(itemView);
//            itemView.setOnClickListener(this);
//            historyText = itemView.findViewById(R.id.historyText);
        }

        private void setData(Watched historyMeal) {
         //   historyText.setText(historyMeal.getDate().concat(" - ").concat(historyMeal.getMealTitle()));
        }

        @Override
        public void onClick(View v) {
        }
    }
}
