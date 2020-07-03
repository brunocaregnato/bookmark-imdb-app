package com.example.bookmarkimdb.ui.home.details;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bookmarkimdb.R;
import com.example.bookmarkimdb.ui.database.BDSQLite;
import com.example.bookmarkimdb.ui.home.HomeFragment;
import com.example.bookmarkimdb.ui.models.MovieDTO;
import com.example.bookmarkimdb.ui.search.SearchFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.File;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class AddMovie extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String imdbId, title, picturePath;
    private TextView titleAdd, actualLocation;
    private EditText address;
    private ImageView image;
    private GoogleApiClient googleApiClient;

    private double latitude, longitude;

    private final int GALLERY = 1;
    private final int PERMISSION_REQUEST = 2;
    private final int RESULT_OK = -1;

    private BDSQLite bd;

    public AddMovie(String imdbId, String title){
        this.imdbId = imdbId;
        this.title = title;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_movie, container, false);

        bd = new BDSQLite(getContext());

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }

        actualLocation = view.findViewById(R.id.actualLocation);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

        image = view.findViewById(R.id.image);
        address = view.findViewById(R.id.address);

        titleAdd = view.findViewById(R.id.titleAdd);
        titleAdd.setText("Add " + title);

        Button loadImageButton = view.findViewById(R.id.loadImageButton);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY);
            }
        });

        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewButton) {
                MovieDTO movie =
                        new MovieDTO(imdbId, new File(picturePath).getAbsolutePath(),
                        latitude, longitude, String.valueOf(address.getText()));
                bd.addMovies(movie);

                Toast.makeText(getActivity().getApplicationContext(), "Title registered successfully!", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SearchFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null,
                    null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            picturePath = c.getString(columnIndex);
            c.close();
            showPicture(new File(picturePath).getAbsolutePath());
        }
    }

    private void showPicture(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onConnected(Bundle bundle) {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                try {
                    Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
                    if (addresses.isEmpty()) {
                        onStop();
                    } else {
                        if (addresses.size() > 0) {
                            Address local = addresses.get(0);
                            actualLocation.setText("Actual Location: "
                                    + local.getCountryName()
                                    + ", " + local.getAdminArea()
                                    + ", " + local.getSubAdminArea()
                                    + ", " + local.getSubLocality()
                                    + ", " + local.getSubThoroughfare());
                            onStop();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        });

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        stopConnection();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopConnection();
    }

    public void stopConnection() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


}
