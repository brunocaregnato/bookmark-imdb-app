package com.example.bookmarkimdb.ui.models;

/**
 * Class responsible for the DataTransfer of
 * Movie data from/to the SQLite DB
 */
public class MovieDTO {

    String id;
    String photoPath;
    double addressLon;
    double addressLat;
    String addressName;

    public MovieDTO(String id, String photoPath, double addressLon, double addressLat, String addressName) {
        this.id = id;
        this.photoPath = photoPath;
        this.addressLon = addressLon;
        this.addressLat = addressLat;
        this.addressName = addressName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public double getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(double addressLon) {
        this.addressLon = addressLon;
    }

    public double getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(double addressLat) {
        this.addressLat = addressLat;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

}
