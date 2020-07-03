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
import androidx.fragment.app.FragmentTransaction;
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


    private RecyclerView recyclerView;
    private List<MovieDTO> searchList;
    private List<MovieDTO> searchListDB;

    private List<Movie> searchListAPIResponse;
    private final static String API_KEY = "e391ba67";

    private BDSQLite bd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.addToBackStack(null).commit();
        searchList = new ArrayList<>();
        searchListAPIResponse = new ArrayList<>();
        searchListDB = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        bd = new BDSQLite(getContext());

        searchListDB.addAll(bd.getAllMovies()); // grab movies from sqlite db to add on recyclerview AFTER checking them with an API call for each, to grab + info

        for(Integer i=0; i<searchListDB.size();i++){
            final MovieDTO movieSaved = searchListDB.get(i);

            Call<Movie> callDetail = apiService.getMovieDetailById(API_KEY,movieSaved.getId());
            callDetail.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    int statusCode = response.code();

                    Movie movie = response.body();

                    searchListAPIResponse.add(movie);
                    searchList.add(movieSaved);

                    resetAdapterState();

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                    Log.e("HELLO DARLING I'M AN ERROR BLEEP BLOOP", t.toString());
                }
            });
        }

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

        final AdapterSearch adapter = new AdapterSearch(searchList, searchListAPIResponse);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(getContext().getApplicationContext(), recyclerView ,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view1, int position) {
                            FragmentTransaction transaction = SearchFragment.this.getFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment,
                                    new SearchDetail(searchListDB.get(position).getId()));
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    })
        );
    }
}
