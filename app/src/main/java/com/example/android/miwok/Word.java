package com.example.android.miwok;

/**
 * {@param Word} is a class which contain default translation and miwok translation of the word
 */
public class Word {
    //Default translation for the word
    private String mDefaultTranslation;
    //Miwok translation of the word
    private String mMiwokTranslation;
    //Image Resource id
    private int mImageResourceId;
    private boolean mContainImage;
    //Audio Resource Id
    private int mAudioResourceId;


    //Creating Word Constructor without image
    public Word (String defaultTranslation, String miwokTranslation, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
        mContainImage = false;
    }
    //Creating Word Constructor with image reg id
    public Word (String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mContainImage = true;
        mAudioResourceId = audioResourceId;
    }
    // get the default translation of the word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }
    //get the miwok translation of the word
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    //getImage res ID
    public int getImageResourceId(){
        return mImageResourceId;
    }
    //give true or false according to whoch constructor is used (contain image or not)
    public boolean getContainImage(){
        return  mContainImage;
    }
    //get audio resource id
    public int getAudioResourceId(){
        return mAudioResourceId;
    }

    // overriding of toString method is used for debugging process
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mContainImage=" + mContainImage +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
