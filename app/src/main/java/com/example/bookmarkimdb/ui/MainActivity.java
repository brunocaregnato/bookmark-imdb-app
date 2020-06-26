package com.example.bookmarkimdb.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmarkimdb.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.common.api.GoogleApiClient.*;

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener  {

    private AppBarConfiguration mAppBarConfiguration;
    private GoogleApiClient googleApiClient;

    private final static String API_KEY = "e391ba67";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this) //Be aware of state of the connection
                .addOnConnectionFailedListener(this) //Be aware of failures
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        // @TODO NEED TO MERGE MOVIE API

//
//        if (API_KEY.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        final ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//
        // DEPOIS DO USUÁRIO PREENCHER A SEARCHBOX ++ CLICAR NO BOTÃO PESQUISAR,
        // VAI CHAMAR ESSA FERA AQUI EMBAIXO

//        Call<MoviesResponse> call = apiService.getMovies(API_KEY,<TEXTO DA SEARCH BOX>,3);
//        call.enqueue(new Callback<MoviesResponse>() {
//            @Override
//            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                int statusCode = response.code();
//                Log.println(1,"teste",Integer.toString(statusCode));
//                Log.println(1,"url",response.raw().request().url().toString());
//                List<MovieSearch> movies = response.body().getSearch();
//                for (MovieSearch movie : movies) {
//                    Call<Movie> callDetail = apiService.getMovieDetail(API_KEY,movie.getTitle());
//                    callDetail.enqueue(new Callback<Movie>() {
//                        @Override
//                        public void onResponse(Call<Movie> call, Response<Movie> response) {
//                            int statusCode = response.code();
//                            Log.i("teste",Integer.toString(statusCode));
//                            Log.i("url",response.raw().request().url().toString());
//                            Movie movie = response.body();
//                            Log.i("Objetao",movie.toString());
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<Movie> call, Throwable t) {
//                            mostraAlerta("Erro",t.toString());
//                            // Log error here since request failed
//                            Log.e(TAG, t.toString());
//                        }
//                    });
//                }
////               Log.println(1,"teste",Integer.toString(movies.size()));

//                  // PASSA A LISTA DE FILMES PRO RECYCLERVIEW ATRAVÉS DO ADAPTER
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
//            }
//
//            @Override
//            public void onFailure(Call<MoviesResponse> call, Throwable t) {
//                mostraAlerta("Erro",t.toString());
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
//
////        Call<Movie> callDetail = apiService.getMovieDetail(API_KEY,"Love & Pop");
////        callDetail.enqueue(new Callback<Movie>() {
////            @Override
////            public void onResponse(Call<Movie> call, Response<Movie> response) {
////                int statusCode = response.code();
////                Log.i("teste",Integer.toString(statusCode));
////                Log.i("url",response.raw().request().url().toString());
////                Movie movie = response.body();
////                Log.i("Objetao",movie.toString());
////
////
//////               Log.println(1,"teste",Integer.toString(movies.size()));
//////                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
////            }
////
////            @Override
////            public void onFailure(Call<Movie> call, Throwable t) {
////                mostraAlerta("Erro",t.toString());
////                // Log error here since request failed
////                Log.e(TAG, t.toString());
////            }
////        });
    }


    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d("LOCATION CHANGED", location.getLatitude() + "");
            Log.d("LOCATION CHANGED", location.getLongitude() + "");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopConnection();
    }

    public void stopConnection() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        try{
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
            if (addresses.isEmpty()) {
                onStop();
            }
            else {
                if (addresses.size() > 0) {
                    Log.i("bruno:" , addresses.get(0).getFeatureName() + " " +
                             addresses.get(0).getLocality() + " " +
                             addresses.get(0).getAdminArea() + " " +
                             addresses.get(0).getCountryName());
                    onStop();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        stopConnection();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void mostraAlerta(String titulo, String mensagem) {
        AlertDialog alertDialog = new
                AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog. BUTTON_NEUTRAL ,
                getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
