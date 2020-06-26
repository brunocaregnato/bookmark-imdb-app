//package com.example.bookmarkimdb.ui.utils;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bookmarkimdb.ui.models.MovieSearch;
//
//import java.util.List;
//
//public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
//
//    private List<MovieSearch> movieSearches;
//    private int rowLayout;
//    private Context context;
//
//
//    public static class MovieViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout moviesLayout;
//        TextView title;
//        TextView year;
//        TextView imdbID;
//        TextView type;
//
//
//        public MovieViewHolder(View v) {
//            super(v);
//            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
//            title = (TextView) v.findViewById(R.id.title);
//            year = (TextView) v.findViewById(R.id.year);
//            imdbID = (TextView) v.findViewById(R.id.imdbID);
//            type = (TextView) v.findViewById(R.id.type);
//        }
//    }
//
//    public MoviesAdapter(List<MovieSearch> movieSearches, int rowLayout, Context context) {
//        this.movieSearches = movieSearches;
//        this.rowLayout = rowLayout;
//        this.context = context;
//    }
//
//    @Override
//    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
//                                              int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
//        return new MovieViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(MovieViewHolder holder, final int position) {
//        holder.title.setText(movieSearches.get(position).getTitle());
//        holder.year.setText(movieSearches.get(position).getYear());
//        holder.imdbID.setText(movieSearches.get(position).getImdbID());
//        holder.type.setText(movieSearches.get(position).getType());
////        holder.title.setText("");
////        holder.year.setText("");
////        holder.imdbID.setText("");
////        holder.type.setText("");
//
//        // holder.rating.setText(movies.get(position).getVoteAverage().toString());
//    }
//
//    @Override
//    public int getItemCount() {
//        return movieSearches.size();
////        return 10;
//    }
//}