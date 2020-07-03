package com.example.bookmarkimdb.ui.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.models.Movie;
import com.example.bookmarkimdb.ui.models.MovieDTO;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {

    private List<MovieDTO> watchedList;
    private List<Movie> watchedListResponse;

    public AdapterSearch(List<MovieDTO> watchedList, List<Movie> watchedListResponse) {
        this.watchedList = watchedList;
        this.watchedListResponse = watchedListResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_search_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setData(watchedList.get(position), watchedListResponse.get(position));
    }

    @Override
    public int getItemCount() {
        return watchedList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, plot, metascore;
        private ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.titleSearch);
            poster = itemView.findViewById(R.id.posterSearch);
            plot = itemView.findViewById(R.id.plotSearch);
            metascore = itemView.findViewById(R.id.metascoreSearch);
        }

        private void setData(MovieDTO movieDTO, Movie movieDetail) {
            title.setText(movieDetail.getTitle());
            plot.setText(movieDetail.getPlot());
            metascore.setText("Metascore: " + movieDetail.getMetascore() + "/100");
            Glide.with(itemView.getContext()).load(movieDetail.getPoster()).into(poster);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
