package com.example.android.musicplayers;

import android.view.View;
import android.widget.Button;

/**
 * Created by a0 on 5‏/8‏/2017.
 */

public class MusicItem {

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private String nameSong;
    private String artistsName;
    private String albumName;
    private int mediaPlayer;
    private String price;

    private int mButtonResourceId ;
    private Button ButtonResource;
    private View mButtonResource;

    private static final int NO_IMAGE_PROVIDED = -1;

    public MusicItem(int ImageResourceId, String nameSong, String artistsName, String albumName, int mediaPlayer, String price) {
        mImageResourceId = ImageResourceId;
        this.nameSong = nameSong;
        this.artistsName = artistsName;
        this.albumName = albumName;
        this.mediaPlayer = mediaPlayer;
        this.price = price;
    }

    public MusicItem(int ImageResourceId, String nameSong, String artistsName, int mediaPlayer, String price) {
        mImageResourceId = ImageResourceId;
        this.nameSong = nameSong;
        this.artistsName = artistsName;
        this.mediaPlayer = mediaPlayer;
        this.price = price;
    }

    public MusicItem(int ImageResourceId, String artistsName, String albumName, String price) {
        mImageResourceId = ImageResourceId;
        this.artistsName = artistsName;
        this.albumName = albumName;
        this.price = price;
    }

    public MusicItem(int ImageResourceId, String nameSong, String artistsName, int mediaPlayer) {
        mImageResourceId = ImageResourceId;
        this.nameSong = nameSong;
        this.artistsName = artistsName;
        this.mediaPlayer = mediaPlayer;
    }

    public MusicItem(String nameSong, String artistsName, int mediaPlayer) {
        this.nameSong = nameSong;
        this.artistsName = artistsName;
        this.mediaPlayer = mediaPlayer;
    }

    public MusicItem(int ImageResourceId, String nameSong, int mediaPlayer) {
        mImageResourceId = ImageResourceId;
        this.nameSong = nameSong;
        this.mediaPlayer = mediaPlayer;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getArtistsName() {
        return artistsName;
    }

    public void setArtistsName(String artistsName) {
        this.artistsName = artistsName;
    }

    public int getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(int mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Button getButtonResource() {
        return ButtonResource;
    }

    public void setButtonResource(Button buttonResource) {
        ButtonResource = buttonResource;
    }

    public View getmButtonResource() {
        return mButtonResource;
    }

    public void setmButtonResource(View mButtonResource) {
        this.mButtonResource = mButtonResource;
    }

    public int getmButtonResourceId() {
        return mButtonResourceId;
    }

    public void setmButtonResourceId(int mButtonResourceId) {
        this.mButtonResourceId = mButtonResourceId;
    }

    /**
     * Returns whether or not there is an image for this music.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
