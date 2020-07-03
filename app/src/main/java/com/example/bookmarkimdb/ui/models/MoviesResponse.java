package com.example.bookmarkimdb.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class for the JSON structure returned from OMDB Api
 * Search :{
 *  <Movie list>
 * }
 */
public class MoviesResponse {
    @SerializedName("Search")
    private List<MovieSearch> search;

    public List<MovieSearch> getSearch() {
        return search;
    }

    public void setSearch(List<MovieSearch> search) {
        this.search = search;
    }

    public MoviesResponse(List<MovieSearch> search) {
        this.search = search;
    }
}
