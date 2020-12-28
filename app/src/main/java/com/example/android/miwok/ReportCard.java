package com.example.android.miwok;

import java.util.ArrayList;

/**
 * this class store name of student and subject grades
 */
public class ReportCard {
    private ArrayList<String> mStudentName = new ArrayList<>();
    private ArrayList<String> mEnglishGrade = new ArrayList<>();
    private ArrayList<String> mMathsGrade = new ArrayList<>();
    private ArrayList<String> mHistoryGrade = new ArrayList<>();

//constructor
    public ReportCard(String name, String englishGrade, String mathsGrade, String historyGrade){
        mStudentName.add(name);
        mEnglishGrade.add(englishGrade);
        mMathsGrade.add(mathsGrade);
        mHistoryGrade.add(historyGrade);
    }

    public String getEnglishGrade(String name){
        for (int i=0; i< mStudentName.size(); i++){
            if (mStudentName.get(i) == name){
                return mEnglishGrade.get(i);
            }
        }
        return null;
    }
    public String setEnglishGrade(String name, String englishGrade){
        for (int i=0; i< mStudentName.size(); i++){
            if (mStudentName.get(i) == name){
                mEnglishGrade.add(i,englishGrade);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ReportCard\n" +
                "StudentName = " + mStudentName +
                "\nEnglishGrade = " + mEnglishGrade +
                "\nMathsGrade = " + mMathsGrade +
                "\nHistoryGrade = " + mHistoryGrade ;
    }
}
