package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;

    AudioManager mAudioManager;

    /**
     * instance of media.onCompletion class
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

        //Array of Numbers
        final ArrayList<Word> numberList = new ArrayList<Word>();

        //Creating an object of class Word
        // adding the values to the words ArrayList which datatype is Word
        numberList.add(new Word("one","lutti", R.drawable.number_one, R.raw.number_one));
        numberList.add(new Word("two","otiiko",R.drawable.number_two, R.raw.number_two));
        numberList.add(new Word("three","tolookosu",R.drawable.number_three, R.raw.number_three));
        numberList.add(new Word("four","oyyisa",R.drawable.number_four, R.raw.number_four));
        numberList.add(new Word("five","massokka",R.drawable.number_five, R.raw.number_five));
        numberList.add(new Word("six","temmokka",R.drawable.number_six, R.raw.number_six));
        numberList.add(new Word("seven","kenekaku",R.drawable.number_seven, R.raw.number_seven));
        numberList.add(new Word("eight","kawinta",R.drawable.number_eight, R.raw.number_eight));
        numberList.add(new Word("nine","wo’e",R.drawable.number_nine, R.raw.number_nine));
        numberList.add(new Word("ten","na’aacha",R.drawable.number_ten, R.raw.number_ten));

        //Creating ArrayAdapter Object itemAdapter
        WordAdapter itemAdapter = new WordAdapter(this,numberList,R.color.category_numbers);

        //Creating ListView Object listView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //getting the clickedWord on which the user clicked
                Word clickedWord = numberList.get(position);

                // current state print in logs, used for debugging
                Log.v("Numbers Activity", "Current word : "+ clickedWord);


                //Release the mediaPlayer every time before starting a new mediaPlayer
                /**
                 * when user clicks multiple views at same time,
                 * even if the first one's audio is not completely finished
                 * (CASE: resource release onCompletion),
                 * the resource released when new view clicked and new mediaPlayer is starting
                 */
                releaseMediaPlayer();

                //requesting audioFocus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    // we have audio focus now
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,
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