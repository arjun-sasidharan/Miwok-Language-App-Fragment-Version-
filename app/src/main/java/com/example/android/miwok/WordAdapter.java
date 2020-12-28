package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.ArrayLinkedVariables;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * this class modify the ArrayAdapter , so it can handle two textview at the same time
 */

public class WordAdapter extends ArrayAdapter< Word >{
    //store color resource
    private int mColorResourceId;

    //Creating Constructor
    public WordAdapter (Activity context, ArrayList<Word> wordsList, int colorResourceId){
        super(context, 0 , wordsList);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //check if existing view is being used otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView miwokText = (TextView) listItemView.findViewById(R.id.miwok_text);
        miwokText.setText(currentWord.getMiwokTranslation());

        TextView defaultText = (TextView) listItemView.findViewById(R.id.english_text);
        defaultText.setText(currentWord.getDefaultTranslation());

        ImageView imageIcon = (ImageView) listItemView.findViewById(R.id.icon);
        if (currentWord.getContainImage() == true ){
            imageIcon.setImageResource(currentWord.getImageResourceId());
            imageIcon.setVisibility(View.VISIBLE);
        }
        else
        {
            //Hiding the View because the activity doesn't have image
            imageIcon.setVisibility(View.GONE);
        }
        //set background color of texts in the lists
        View textContainer = (View) listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }



}
