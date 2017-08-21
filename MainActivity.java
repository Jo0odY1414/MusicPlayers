package com.example.android.musicplayers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView artists = (ImageView) findViewById(R.id.button_artists);
        artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(MainActivity.this, ArtistsActivity.class);
                startActivity(artistsIntent);
            }
        });

        ImageView album = (ImageView) findViewById(R.id.button_album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(MainActivity.this, AlbumActivity.class);
                startActivity(albumIntent);
            }
        });

        ImageView allSongs = (ImageView) findViewById(R.id.button_all_songs);
        allSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allSongsIntent = new Intent(MainActivity.this, AllSongsActivity.class);
                startActivity(allSongsIntent);
            }
        });

        ImageView playStore = (ImageView) findViewById(R.id.button_play_store);
        playStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playStoreIntent = new Intent(MainActivity.this, PlayStoreActivity.class);
                startActivity(playStoreIntent);
            }
        });

        ImageView nowPlaying = (ImageView) findViewById(R.id.button_now_playing);
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(MainActivity.this, NowPlayingActivity.class);
                startActivity(nowPlayingIntent);
            }
        });


    }
}
