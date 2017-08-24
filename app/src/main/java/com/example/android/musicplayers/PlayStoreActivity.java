package com.example.android.musicplayers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayStoreActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.example.android.musicplayers.NAME";
    public static final String EXTRA_ARTIST = "com.example.android.musicplayers.ARTIST";
    public static final String EXTRA_IMAGE = "-1";
    public static final String EXTRA_SONG = "-1";
    ImageView imageViewSong;
    TextView textViewNameSong;
    TextView textViewArtistName;
    private String nameOfSong;
    private String artistName;
    private int image;
    private int songResourseId;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private TextView describePlay;
    private TextView describeBuy;
    private TextView describeBuyFunction;
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
        setContentView(R.layout.activity_play_store);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        describePlay = (TextView) findViewById(R.id.describe_play);

        describeBuy = (TextView) findViewById(R.id.describe_buy);

        describeBuyFunction = (TextView) findViewById(R.id.describe_buy_function);

        imageViewSong = (ImageView) findViewById(R.id.Image_music);
        textViewNameSong = (TextView) findViewById(R.id.textView_AudioName);
        textViewArtistName = (TextView) findViewById(R.id.textView_AudioArtist);

        // Get the Intent that started this activity and extract the nameOfSong & artistName & image & song
        Intent nowPlayingIntent = getIntent();
        nameOfSong = nowPlayingIntent.getStringExtra(NowPlayingActivity.EXTRA_NAME);
        artistName = nowPlayingIntent.getStringExtra(NowPlayingActivity.EXTRA_ARTIST);
        image = nowPlayingIntent.getIntExtra(NowPlayingActivity.EXTRA_IMAGE, Integer.parseInt(NowPlayingActivity.EXTRA_IMAGE));
        songResourseId = nowPlayingIntent.getIntExtra(NowPlayingActivity.EXTRA_SONG, Integer.parseInt(NowPlayingActivity.EXTRA_SONG));

        if (nameOfSong != null) {
            textViewNameSong.setText(nameOfSong);
            textViewArtistName.setText(artistName);
            imageViewSong.setImageResource(image);
            Log.v("nameOfSong", String.valueOf(textViewNameSong.getText()));
            Log.v("artistName", String.valueOf(textViewArtistName.getText()));
            Log.v("image", String.valueOf(image));
            Log.v("songResourseId", String.valueOf(songResourseId));
        } else {
            nameOfSong = String.valueOf(textViewNameSong.getText());
            artistName = String.valueOf(textViewArtistName.getText());
            image = R.drawable.despacito;
            songResourseId = R.raw.despacito;
        }

        ImageView play = (ImageView) findViewById(R.id.ic_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                releaseMediaPlayer();

                describePlay.setVisibility(View.VISIBLE);
                describeBuy.setVisibility(View.GONE);
                describeBuyFunction.setVisibility(View.GONE);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(PlayStoreActivity.this, songResourseId);

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });

        Button buyFromStore = (Button) findViewById(R.id.button_buy_from_store);
        buyFromStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describeBuy.setVisibility(View.VISIBLE);
                describePlay.setVisibility(View.GONE);
            }
        });

        Button buttonDescribeBuyFunction = (Button) findViewById(R.id.button_details);
        buttonDescribeBuyFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describeBuyFunction.setVisibility(View.VISIBLE);
                describePlay.setVisibility(View.GONE);
            }
        });

        Button buttonNowPlay = (Button) findViewById(R.id.button_now_play);
        buttonNowPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(PlayStoreActivity.this, NowPlayingActivity.class);
                nowPlayingIntent.putExtra(EXTRA_NAME, nameOfSong);
                nowPlayingIntent.putExtra(EXTRA_ARTIST, artistName);
                nowPlayingIntent.putExtra(EXTRA_IMAGE, image);
                nowPlayingIntent.putExtra(EXTRA_SONG, songResourseId);
                startActivity(nowPlayingIntent);

            }
        });

        Button mainActivity = (Button) findViewById(R.id.button_home);

        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(PlayStoreActivity.this, MainActivity.class);
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