package com.example.bookmarkimdb.ui.models;

public class MovieDTO {

    String id;
    String photoPath;
    String addressLon;
    String addressLat;
    String addressName;

    public MovieDTO(String id, String photoPath, String addressLon, String addressLat, String addressName) {
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

    public String getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(String addressLon) {
        this.addressLon = addressLon;
    }

    public String getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(String addressLat) {
        this.addressLat = addressLat;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

}
