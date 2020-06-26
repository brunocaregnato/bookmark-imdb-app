package com.example.bookmarkimdb.ui.rest;

import com.example.bookmarkimdb.ui.models.Movie;
import com.example.bookmarkimdb.ui.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    // curl -X GET -i 'http://www.omdbapi.com/&apikey=e391ba67?s='

    @GET("/")
    Call<MoviesResponse> getMovies(@Query("apikey") String apiKey, @Query("s") String movieName, @Query("page") int page);

    // curl -X GET -i 'http://www.omdbapi.com/&apikey=e391ba67?t='

    @GET("/")
    Call<Movie> getMovieDetail(@Query("apikey") String apiKey, @Query("t") String movieName);

    // curl -X GET -i 'http://www.omdbapi.com/&apikey=e391ba67?i='
    @GET("/")
    Call<Movie> getMovieDetailById(@Query("apikey") String apiKey, @Query("i") String movieId);

    // @GET("movie/{id}")
    // Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
