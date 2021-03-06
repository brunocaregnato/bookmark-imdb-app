package com.example.bookmarkimdb.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.bookmarkimdb.ui.MainActivity;
import com.example.bookmarkimdb.ui.home.details.Details;
import com.example.bookmarkimdb.ui.models.Movie;
import com.example.bookmarkimdb.ui.models.MovieSearch;
import com.example.bookmarkimdb.ui.models.MoviesResponse;
import com.example.bookmarkimdb.ui.rest.ApiClient;
import com.example.bookmarkimdb.ui.rest.ApiInterface;
import com.example.bookmarkimdb.ui.utils.AdapterHome;
import com.example.bookmarkimdb.ui.utils.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Movie> moviesList;
    private String TAG = "DEBUG: ";
    private ProgressBar progressBar;
    private FloatingActionButton searchMoreButton;
    private final static String API_KEY = "e391ba67";
    private Integer pageNumber = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        moviesList = new ArrayList<>();
        searchMoreButton = view.findViewById(R.id.searchMore);
        searchMoreButton.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progress_spinner);
        setRecyclerView(view);

        final EditText searchMovie = view.findViewById(R.id.searchMovie);
        FloatingActionButton searchMoviesButton = view.findViewById(R.id.searchMoviesButton);
        if (searchMovie.getText().toString().trim().length() > 0) {
            pageNumber = 1;
            searchMovies(view, apiService, searchMovie, pageNumber);
        }

        searchMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {

                if (searchMovie.getText().toString().trim().length() > 0) {
                    pageNumber = 1;
                    moviesList.clear();
                    searchMoreButton.setVisibility(View.GONE);
                    searchMovies(view, apiService, searchMovie, pageNumber);
                } else {
                    searchMoreButton.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Please input a movie title for the search.", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        searchMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {

                if (searchMovie.getText().toString().trim().length() > 0) {
                    pageNumber += 1;
                    searchMovies(view, apiService, searchMovie, pageNumber);
                    if((moviesList.size()%10)>pageNumber)
                        searchMoreButton.setVisibility(view.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Please input a movie title for the search.", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        return view;
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.homeFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));

        final AdapterHome adapterHome = new AdapterHome(moviesList);
        recyclerView.setAdapter(adapterHome);
        adapterHome.notifyDataSetChanged();

        //searched movies details
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext().getApplicationContext(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view1, int position) {
                                FragmentTransaction transaction = HomeFragment.this.getFragmentManager().beginTransaction();
                                transaction.replace(R.id.nav_host_fragment,
                                        new Details(moviesList.get(position).getImdbID(), API_KEY));
                                transaction.addToBackStack(null);
                                transaction.commitAllowingStateLoss();
                            }
                        })
        );

    }

    private void resetAdapterState() {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void displayAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void searchMovies(final View view, final ApiInterface apiService, EditText searchMovie, final Integer pageNumber) {

        progressBar.setVisibility(view.VISIBLE);

        Call<MoviesResponse> call = apiService.getMovies(API_KEY, searchMovie.getText().toString().trim(), pageNumber);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieSearch> movies = response.body().getSearch();
                if (movies == null) {
                    Toast.makeText(getContext(), "No movies where found with this title. Please try another one.", Toast.LENGTH_SHORT).show();
                    resetAdapterState();
                    progressBar.setVisibility(view.GONE);
                } else {
                    for (MovieSearch movie : movies) {
                        Call<Movie> callDetail = apiService.getMovieDetail(API_KEY, movie.getTitle());
                        callDetail.enqueue(new Callback<Movie>() {
                            @Override
                            public void onResponse(Call<Movie> call, Response<Movie> response) {
                                Movie movie = response.body();
                                moviesList.add(movie);
                                resetAdapterState();
                                if((moviesList.size()%10)>pageNumber)
                                    searchMoreButton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(Call<Movie> call, Throwable t) {
                                displayAlert("Error", t.toString());
                                Log.e(TAG, t.toString());
                            }
                        });
                    }

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    progressBar.setVisibility(view.GONE);
                }
            }


            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                displayAlert("Error", t.toString());
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}