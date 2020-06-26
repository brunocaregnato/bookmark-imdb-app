package com.example.bookmarkimdb.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


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
//    @SerializedName("results")
//    private List<Movie> results;
//    @SerializedName("total_results")
//    private int totalResults;
//    @SerializedName("total_pages")
//    private int totalPages;


//    public MoviesResponse(List<Movie> results){
//        this.results = results;
//    }

//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }
//
//    public List<Movie> getResults() {
//        return this.results;
//    }
//
//    public void setResults(List<Movie> results) {
//        this.results = results;
//    }
//
//    public int getTotalResults() {
//        return totalResults;
//    }
//
//    public void setTotalResults(int totalResults) {
//        this.totalResults = totalResults;
//    }
//
//    public int getTotalPages() {
//        return totalPages;
//    }
//
//    public void setTotalPages(int totalPages) {
//        this.totalPages = totalPages;
//    }
}
