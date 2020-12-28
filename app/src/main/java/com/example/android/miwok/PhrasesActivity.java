package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
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

        //Creating ArrayList of Word Type to store phrases
        final ArrayList<Word> phrasesList = new ArrayList<>();
        phrasesList.add( new Word("Where are you going?","minto wuksus", R.raw.phrase_where_are_you_going));
        phrasesList.add( new Word("What is your name?","tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrasesList.add( new Word("My name is...","oyaaset...", R.raw.phrase_my_name_is));
        phrasesList.add( new Word("How are you feeling?","michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrasesList.add( new Word("I’m feeling good.","kuchi achit", R.raw.phrase_im_feeling_good));
        phrasesList.add( new Word("Are you coming?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrasesList.add( new Word("Yes, I’m coming.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrasesList.add( new Word("I’m coming.","әәnәm", R.raw.phrase_im_coming));
        phrasesList.add( new Word("Let’s go.","yoowutis", R.raw.phrase_lets_go));
        phrasesList.add( new Word("Come here.","әnni'nem", R.raw.phrase_come_here));

        //Creating WordAdapter Object
        WordAdapter itemAdapter = new WordAdapter(this,phrasesList,R.color.category_phrases);
        //Creating ListView reference
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word clickedWord = phrasesList.get(position);   //getting the word on which the user clicked
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
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,
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