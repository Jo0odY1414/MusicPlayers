package com.example.android.musicplayers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_lists);

        final ArrayList<MusicItem> musicList = new ArrayList<>();

        MusicItem onecallaway = new MusicItem(R.drawable.onecallaway_album,"Charlie Puth","One Call Away","BUY $9.99");
        MusicItem attention = new MusicItem(R.drawable.attention,"Charlie Puth","Attention","BUY $8.99");
        MusicItem despacito = new MusicItem(R.drawable.despacito,"Luis Fonsi","Despacito","BUY $19.99");
        MusicItem shapeofyou = new MusicItem(R.drawable.divide_album_edsheeran,"Ed Sheeran","Divide","BUY $11.99");
        musicList.add(onecallaway);
        musicList.add(attention);
        musicList.add(despacito);
        musicList.add(shapeofyou);

        MusicItemAdapter adapter = new MusicItemAdapter(this, musicList, 3);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        TextView describeBuy = new TextView(getApplicationContext());
        describeBuy.setText(R.string.describe_buy);
        describeBuy.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        describeBuy.setTextSize(30);
        listView.addFooterView(describeBuy);
    }
}
