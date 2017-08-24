package com.example.android.musicplayers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class ArtistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_lists);

        final ArrayList<MusicItem> musicList = new ArrayList<>();

        MusicItem shapeOfYou = new MusicItem(R.drawable.divide_album_edsheeran, "Ed Sheeran", "Divide", "BUY $11.99");
        MusicItem oneCallAway = new MusicItem(R.drawable.onecallaway_album, "Charlie Puth", "One Call Away", "BUY $9.99");
        MusicItem attention = new MusicItem(R.drawable.attention, "Charlie Puth", "Attention", "BUY $8.99");
        MusicItem despacito = new MusicItem(R.drawable.despacito, "Luis Fonsi", "Despacito", "BUY $19.99");
        musicList.add(shapeOfYou);
        musicList.add(oneCallAway);
        musicList.add(attention);
        musicList.add(despacito);

        final MusicItemAdapter adapter = new MusicItemAdapter(this, musicList, 2);

        final TextView nameOfScreen = (TextView) findViewById(R.id.screen_name);
        nameOfScreen.setText("ARTIST");

        final ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        TextView describeBuy = new TextView(getApplicationContext());
        describeBuy.setText(R.string.describe_buy);
        describeBuy.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        describeBuy.setTextSize(25);
        listView.addFooterView(describeBuy);

        Button playStore = new Button(getApplicationContext());
        playStore.setGravity(CENTER_HORIZONTAL);
        playStore.setText("GO TO PLAY STORE");
        listView.addFooterView(playStore);


        playStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playStoreIntent = new Intent(ArtistsActivity.this, PlayStoreActivity.class);
                startActivity(playStoreIntent);
            }
        });

        Button mainActivity = new Button(getApplicationContext());
        mainActivity.setGravity(CENTER_HORIZONTAL);
        mainActivity.setText("GO TO HOME");
        listView.addFooterView(mainActivity);

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(ArtistsActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });

    }
}
