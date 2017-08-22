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

public class NowPlayingActivity extends AppCompatActivity {

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
    private TextView describe;
    private TextView describeDetails;
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
        setContentView(R.layout.activity_now_playing);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        describe = (TextView) findViewById(R.id.describe);
        describeDetails = (TextView) findViewById(R.id.describe_details);

        imageViewSong = (ImageView) findViewById(R.id.trakImage);
        textViewNameSong = (TextView) findViewById(R.id.trakNameSong);
        textViewArtistName = (TextView) findViewById(R.id.trakNameArtist);

        // Get the Intent that started this activity and extract the nameOfSong & artistName & image & song
        Intent playStoreIntent = getIntent();
        nameOfSong = playStoreIntent.getStringExtra(PlayStoreActivity.EXTRA_NAME);
        artistName = playStoreIntent.getStringExtra(PlayStoreActivity.EXTRA_ARTIST);
        image = playStoreIntent.getIntExtra(PlayStoreActivity.EXTRA_IMAGE, Integer.parseInt(PlayStoreActivity.EXTRA_IMAGE));
        songResourseId = playStoreIntent.getIntExtra(PlayStoreActivity.EXTRA_SONG, Integer.parseInt(PlayStoreActivity.EXTRA_SONG));

        if (nameOfSong != null) {
            textViewNameSong.setText(nameOfSong);
            textViewArtistName.setText(artistName);
            imageViewSong.setImageAlpha(image);
            Log.i("nameOfSong", String.valueOf(textViewNameSong.getText()));
            Log.i("artistName", String.valueOf(textViewArtistName.getText()));
            Log.i("image", String.valueOf(image));
            Log.i("songResourseId", String.valueOf(songResourseId));
        } else {
            nameOfSong = String.valueOf(textViewNameSong.getText());
            artistName = String.valueOf(textViewArtistName.getText());
            image = R.drawable.divide_album_edsheeran;
            songResourseId = R.raw.shapeofyou;
            Log.i("ShapeOfYou", String.valueOf(songResourseId));
        }
        ImageView stop = (ImageView) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describe.setText("Stop playing the song NOW");
                describe.setVisibility(View.VISIBLE);

                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.prepareAsync();
                }
            }
        });

        ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describe.setText("Start playing the song NOW");
                describe.setVisibility(View.VISIBLE);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(NowPlayingActivity.this, songResourseId);

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });

        ImageView pause = (ImageView) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describe.setText("Pause playing the song NOW");
                describe.setVisibility(View.VISIBLE);

                if (mMediaPlayer != null)
                    mMediaPlayer.pause();
            }
        });

        Button buttonDescribeNowPlaying = (Button) findViewById(R.id.button_details);
        buttonDescribeNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                describeDetails.setText(R.string.describe_now_playing);
                describeDetails.setVisibility(View.VISIBLE);
            }
        });

        Button buttonBuyFromStore = (Button) findViewById(R.id.button_buy_from_store);
        buttonBuyFromStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playStoreIntent = new Intent(NowPlayingActivity.this, PlayStoreActivity.class);
                playStoreIntent.putExtra(EXTRA_NAME, nameOfSong);
                playStoreIntent.putExtra(EXTRA_ARTIST, artistName);
                playStoreIntent.putExtra(EXTRA_IMAGE, image);
                playStoreIntent.putExtra(EXTRA_SONG, songResourseId);
                startActivity(playStoreIntent);

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
