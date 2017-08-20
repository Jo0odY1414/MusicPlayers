package com.example.android.musicplayers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ArtistsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_lists);

        final ArrayList<MusicItem> musicList = new ArrayList<>();

        MusicItem oneCallAway = new MusicItem(R.drawable.onecallaway_album, "Charlie Puth", "One Call Away", "BUY $9.99");
        MusicItem attention = new MusicItem(R.drawable.attention, "Charlie Puth", "Attention", "BUY $8.99");
        MusicItem despacito = new MusicItem(R.drawable.despacito, "Luis Fonsi", "Despacito", "BUY $19.99");
        MusicItem shapeOfYou = new MusicItem(R.drawable.divide_album_edsheeran, "Ed Sheeran", "Divide", "BUY $11.99");
        musicList.add(oneCallAway);
        musicList.add(attention);
        musicList.add(despacito);
        musicList.add(shapeOfYou);

        final MusicItemAdapter adapter = new MusicItemAdapter(this, musicList, 2);

        final ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.v("onItemClick","onItemClick");

            }
        });
    }
}
