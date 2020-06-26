package com.example.bookmarkimdb.ui.rest;

import com.example.bookmarkimdb.ui.models.Movie;
import com.example.bookmarkimdb.ui.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    // curl -X GET -i 'http://api.themoviedb.org/3/movie/top_rated?api_key=7e8f60e325cd06e164799af1e317d7a7'

    @GET("/")
    Call<MoviesResponse> getMovies(@Query("apikey") String apiKey, @Query("s") String movieName, @Query("page") int page);

    // curl -X GET -i 'http://api.themoviedb.org/3/movie/238?api_key=7e8f60e325cd06e164799af1e317d7a7'

    @GET("/")
    Call<Movie> getMovieDetail(@Query("apikey") String apiKey, @Query("t") String movieName);


    // @GET("movie/{id}")
    // Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
