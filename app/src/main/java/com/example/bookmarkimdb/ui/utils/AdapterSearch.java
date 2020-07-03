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
//        viewHolder.setData(watchedList.get(position));
    }

    @Override
    public int getItemCount() {
        return watchedList.size();
    }

    //popular as tags da fragment_search aqui
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, plot, addressName;
        private ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            poster = itemView.findViewById(R.id.poster);
            plot = itemView.findViewById(R.id.plot);
            addressName = itemView.findViewById(R.id.addressName);
        }

        private void setData(MovieDTO movieDTO, Movie movieDetail) {
//private void setData(MovieDTO movieDTO) {
            title.setText(movieDetail.getTitle());
    addressName.setText(movieDTO.getAddressName());
            plot.setText(movieDetail.getPlot());
            Glide.with(itemView.getContext()).load(movieDetail.getPoster()).into(poster);
        }

//        poster
//        title
//        plot
//        addressName

        @Override
        public void onClick(View v) {
        }
    }
}
