package com.example.android.musicplayers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class AllSongsActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.example.android.musicplayers.NAME";
    public static final String EXTRA_ARTIST = "com.example.android.musicplayers.ARTIST";
    public static final String EXTRA_IMAGE = "-1";
    public static final String EXTRA_SONG = "-1";

    private String nameOfSong;
    private String artistName;
    private int image;
    private int songResourseId;

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private TextView describePlay;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_lists);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<MusicItem> musicList = new ArrayList<>();

        MusicItem shapeofyou = new MusicItem(R.drawable.divide_album_edsheeran, "Shape of you", "Ed Sheeran", R.raw.shapeofyou);
        MusicItem onecallaway = new MusicItem(R.drawable.onecallaway_album, "One Call Away", "Charlie Puth", R.raw.onecallaway);
        MusicItem attention = new MusicItem(R.drawable.attention, "Attention", "Charlie Puth", R.raw.attention);
        MusicItem despacito = new MusicItem(R.drawable.despacito, "Despacito", "Luis Fonsi", R.raw.despacito);
        musicList.add(shapeofyou);
        musicList.add(onecallaway);
        musicList.add(attention);
        musicList.add(despacito);

        MusicItemAdapter adapter = new MusicItemAdapter(this, musicList, 1);

        final TextView nameOfScreen = (TextView) findViewById(R.id.screen_name);
        nameOfScreen.setText("ALL SONG");

        final ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        describePlay = new TextView(getApplicationContext());

        nameOfSong = shapeofyou.getNameSong();
        artistName = shapeofyou.getArtistsName();
        image = shapeofyou.getmImageResourceId();
        songResourseId = shapeofyou.getMediaPlayer();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                listView.removeFooterView(describePlay);

                releaseMediaPlayer();

                MusicItem musicItem = musicList.get(position);

                describePlay.setText(R.string.describe_play_song);
                describePlay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                describePlay.setTextSize(30);
                listView.addFooterView(describePlay);

                nameOfSong = musicItem.getNameSong();
                artistName = musicItem.getArtistsName();
                image = musicItem.getmImageResourceId();
                songResourseId = musicItem.getMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(AllSongsActivity.this, musicItem.getMediaPlayer());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        Button playStore = new Button(getApplicationContext());
        playStore.setGravity(CENTER_HORIZONTAL);
        playStore.setText("GO TO NOW PLAYING");
        listView.addFooterView(playStore);

        playStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(AllSongsActivity.this, NowPlayingActivity.class);
                nowPlayingIntent.putExtra(EXTRA_NAME, nameOfSong);
                nowPlayingIntent.putExtra(EXTRA_ARTIST, artistName);
                nowPlayingIntent.putExtra(EXTRA_IMAGE, image);
                nowPlayingIntent.putExtra(EXTRA_SONG, songResourseId);
                startActivity(nowPlayingIntent);
            }
        });

        Button mainActivity = new Button(getApplicationContext());
        mainActivity.setGravity(CENTER_HORIZONTAL);
        mainActivity.setText("GO TO HOME");
        listView.addFooterView(mainActivity);

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(AllSongsActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
