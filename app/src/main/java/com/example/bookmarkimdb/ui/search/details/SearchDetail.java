package com.example.bookmarkimdb.ui.search.details;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.database.BDSQLite;

import com.example.bookmarkimdb.ui.models.MovieDTO;
import com.example.bookmarkimdb.ui.search.SearchFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;


public class SearchDetail extends Fragment {

    private String id;
    private BDSQLite bd;
    private ImageView photo;
    private TextView locationNickname, location;
    private double latitude, longitude;

    public SearchDetail(String id){
        this.id = id;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_detail, container, false);
        bd = new BDSQLite(getContext());
        photo = view.findViewById(R.id.photo);
        locationNickname = view.findViewById(R.id.locationNickname);
        location = view.findViewById(R.id.location);

        bd = new BDSQLite(getContext());
        MovieDTO movie = bd.getMovie(id);

        locationNickname.setText(movie.getAddressName());
        photo.setImageBitmap(BitmapFactory.decodeFile(movie.getPhotoPath()));

        latitude = movie.getAddressLat();
        longitude = movie.getAddressLon();

        try{
            Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                onStop();
            }
            else {
                if (addresses.size() > 0) {
                    Address local = addresses.get(0);
                    location.setText("Location: "
                            + local.getCountryName()
                            + ", " + local.getAdminArea()
                            + ", " + local.getSubAdminArea()
                            + ", " + local.getSubLocality()
                            + ", " + local.getSubThoroughfare());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }




        FloatingActionButton addMovieButton = view.findViewById(R.id.removeMovieButton);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {
                deleteConfirmationAlert();
            }
        });





        return view;
    }

    private void deleteConfirmationAlert() {
        AlertDialog.Builder deleteConfirmationDialog = new AlertDialog.Builder(getContext());
        deleteConfirmationDialog.setTitle("Remove movie entry from list");
        deleteConfirmationDialog.setMessage("Do you want to delete this movie entry from your list ?");
        deleteConfirmationDialog.setCancelable(false);
        deleteConfirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Movie successfully removed", Toast.LENGTH_SHORT).show();
                bd.deleteMovieById(id);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SearchFragment()).commit();
            }
        });

        deleteConfirmationDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "The movie wasn't removed", Toast.LENGTH_SHORT).show();
            }
        });
        deleteConfirmationDialog.show();
    }
}
