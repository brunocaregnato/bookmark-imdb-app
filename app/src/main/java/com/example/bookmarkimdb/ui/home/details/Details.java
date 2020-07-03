package com.example.bookmarkimdb.ui.home.details;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.models.Movie;
import com.example.bookmarkimdb.ui.rest.ApiClient;
import com.example.bookmarkimdb.ui.rest.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends Fragment {

    private String IMDB_ID;
    private String API_KEY;

    private ImageView posterDetail;
    private TextView titleDetail, plotDetail, metascore, votes, actors, director, year;

    public Details(String imdbId, String apiKey){
        this.IMDB_ID = imdbId;
        this.API_KEY = apiKey;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail, container, false);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        titleDetail = view.findViewById(R.id.titleDetail);
        posterDetail = view.findViewById(R.id.posterDetail);
        plotDetail = view.findViewById(R.id.plotDetail);
        metascore =  view.findViewById(R.id.metascore);
        votes =  view.findViewById(R.id.votes);
        actors = view.findViewById(R.id.actors);
        director = view.findViewById(R.id.director);
        year = view.findViewById(R.id.year);

        FloatingActionButton addMovieButton = view.findViewById(R.id.addMovieButton);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {
                Details.this.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new AddMovie(IMDB_ID, String.valueOf(titleDetail.getText()))).commitAllowingStateLoss();
            }
        });


        Call<Movie> callDetail = apiService.getMovieDetailById(API_KEY, IMDB_ID);
        callDetail.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                titleDetail.setText(movie.getTitle());
                plotDetail.setText(movie.getPlot());
                metascore.setText("Metascore: " + movie.getMetascore() + "/100");
                votes.setText("Votes: " + movie.getImdbVotes());
                actors.setText("Actors: " + movie.getActors());
                director.setText("Director(s): " + movie.getDirector());
                year.setText("Year: " + movie.getYear());
                Glide.with(view.getContext()).load(movie.getPoster()).into(posterDetail);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
//                mostraAlerta("Erro",t.toString());
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
            }
        });

        return view;
    }



}
