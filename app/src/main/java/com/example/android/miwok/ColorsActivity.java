package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;

    AudioManager mAudioManager;

    /**
     * instance of media.oncompletion class
     */
    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };



    /**
     * Instance of onAudioFocusChangeListener
     */
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback because your Audio Focus was temporarily stolen, but will be back soon. i.e. for a phone call
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback, because you lost the Audio Focus.
                releaseMediaPlayer();

            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback, because you hold the Audio Focus
                mMediaPlayer.start();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //creating AudioManger object instance
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //creating ArrayList of Word Type for colors
       final ArrayList<Word> colorsList = new ArrayList<>();
        colorsList.add( new Word("red","weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        colorsList.add( new Word("green","chokokki", R.drawable.color_green, R.raw.color_green));
        colorsList.add( new Word("brown","ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        colorsList.add( new Word("gray","ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        colorsList.add( new Word("black","kululli", R.drawable.color_black, R.raw.color_black));
        colorsList.add( new Word("white","kelelli", R.drawable.color_white, R.raw.color_white));
        colorsList.add( new Word("dusty yellow","ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorsList.add( new Word("mustard yellow","chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //Creating WordAdapter object
        WordAdapter itemAdapter = new WordAdapter(this,colorsList,R.color.category_colors);
        //Creating ListView reference
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word clickedWord = colorsList.get(position);   //getting the word on which the user clicked

                //Release the mediaPlayer every time before starting a new mediaPlayer
                /**
                 * when user clicks multiple views at same time , even if the first one's audio is not completely finished ( CASE: resource release
                 * onCompletion ) , the resource released when new view clicked and new mediaPlayer is starting
                 */
                releaseMediaPlayer();

                //requesting audioFocus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    // we have audio focus now
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this,
                            clickedWord.getAudioResourceId());
                    //start the media player to play
                    mMediaPlayer.start();
                    //set an listener to release media player after completion
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // releasing mediaPlayer when the user change the app or gone to HOME Screen (onStop)
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