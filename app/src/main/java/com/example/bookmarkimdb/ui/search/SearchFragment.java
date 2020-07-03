package com.example.bookmarkimdb.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.database.BDSQLite;
import com.example.bookmarkimdb.ui.models.Movie;
import com.example.bookmarkimdb.ui.models.MovieDTO;
import com.example.bookmarkimdb.ui.rest.ApiClient;
import com.example.bookmarkimdb.ui.rest.ApiInterface;
import com.example.bookmarkimdb.ui.search.details.SearchDetail;
import com.example.bookmarkimdb.ui.utils.AdapterSearch;
import com.example.bookmarkimdb.ui.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private List<MovieDTO> searchList;
    private List<MovieDTO> searchListDB;

    private List<Movie> searchListAPIResponse;
    private final static String API_KEY = "e391ba67";

    private BDSQLite bd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchList = new ArrayList<>();
        searchListAPIResponse = new ArrayList<>();
        searchListDB = new ArrayList<>();
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

//        final TextView textView = root.findViewById(R.id.text_gallery);
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        bd = new BDSQLite(getContext());
//        MovieDTO filme1 = new MovieDTO("tt0168972", "89123798123", "12309","12309","casa");
//        MovieDTO filme2 = new MovieDTO("tt1528785", "66666666", "66666666","66666666","casa");
//        bd.addMovies(filme1);
//        bd.addMovies(filme2);
        searchListDB.addAll(bd.getAllMovies());

        for(Integer i=0; i<searchListDB.size();i++){
            final MovieDTO movieSaved = searchListDB.get(i);
            Log.i("t1",movieSaved.getId());
            Log.i("t1",movieSaved.getPhotoPath());
            //Log.i("t1",movieSaved.getAddressLat());
            Log.i("t1",movieSaved.getAddressName());
            Call<Movie> callDetail = apiService.getMovieDetailById(API_KEY,movieSaved.getId());
            callDetail.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    int statusCode = response.code();
                    Log.i("teste",Integer.toString(statusCode));
                    Log.i("url",response.raw().request().url().toString());

                    Movie movie = response.body();
                    Log.i("bode",movie.getDirector());
                    searchListAPIResponse.add(movie);
                    searchList.add(movieSaved);
                    Log.i("Objetao1",searchListAPIResponse.get(searchListAPIResponse.size()-1).toString());
                    Log.i("Objetao2",searchList.get(searchList.size()-1).toString());
                    resetAdapterState();

//               Log.println(1,"teste",Integer.toString(movies.size()));
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
//                    mostraAlerta("Erro",t.toString());
                    // Log error here since request failed
                    Log.e("HELLO DARLING", t.toString());
                }
            });
        }
//        for(Movie movieSaved : searchListAPIResponse) {
//            Log.i("Films", movieSaved.toString());
//        }
        this.setRecyclerView(root);

        return root;
    }

    private void resetAdapterState(){
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setRecyclerView(View view){
        recyclerView = view.findViewById(R.id.searchFragment);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
//        for(Integer i=0; i<searchListAPIResponse.size();i++) {
//            Movie movieSaved = searchListAPIResponse.get(i);
//            Log.i("testa lista", movieSaved.toString());
//        }
        final AdapterSearch adapter = new AdapterSearch(searchList, searchListAPIResponse);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(getContext().getApplicationContext(), recyclerView ,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view1, int position) {
                            SearchFragment.this.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                    new SearchDetail(searchListDB.get(position).getId())).commit();
                        }
                    })
        );
    }
}
