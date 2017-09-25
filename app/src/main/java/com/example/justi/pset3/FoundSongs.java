package com.example.justi.pset3;

import java.io.Serializable;

/**
 * Created by justi on 20-9-2017.
 */

public class FoundSongs implements Serializable {
    private String name, artist, imageURL;


    public FoundSongs(String TheName, String TheArtist, String TheImageURL) {
        this.name = TheName;
        this.artist = TheArtist;
        this.imageURL = TheImageURL;
    }

    // Returns the Track name
    public String getName() {
        return name;
    }

    // Returns the Artist
    public String getArtist() {
        return artist;
    }

    // Returns the image URL
    public String getImageURL() {
        return imageURL;
    }

}
