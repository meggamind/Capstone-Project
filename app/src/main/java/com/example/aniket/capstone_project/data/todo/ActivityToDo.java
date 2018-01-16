package com.example.aniket.capstone_project.data.todo;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by aniket on 1/3/18.
 */

public class ActivityToDo implements Parcelable {
    private String mName;
    private ArrayList<String> mAcitivityImages;
    private String mLocation;
    private String mInfo;

    public ActivityToDo() {
        mAcitivityImages = new ArrayList<>();
    }

    public ActivityToDo(String name,
                        ArrayList<String> activityImages,
                        String location,
                        String info) {
        mName = name;
        mAcitivityImages = activityImages;
        mLocation = location;
        mInfo = info;
    }


    public ActivityToDo(Parcel in) {
        readFromParcel(in);
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<String> getallActivityImages() {
        return mAcitivityImages;
    }

    public void setAcitivityImages(ArrayList<String> images) {
        mAcitivityImages = images;
    }

    public void addActivityImages(String image) {
        mAcitivityImages.add(image);
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeList(mAcitivityImages);
        dest.writeString(mLocation);
        dest.writeString(mInfo);
    }

    public static final Parcelable.Creator<ActivityToDo> CREATOR
            = new Parcelable.Creator<ActivityToDo>() {

        @Override
        public ActivityToDo createFromParcel(Parcel in) {
            return new ActivityToDo(in);
        }

        @Override
        public ActivityToDo[] newArray(int size) {
            return new ActivityToDo[size];
        }
    };


    private void readFromParcel(Parcel in) {
        mName = in.readString();
        mAcitivityImages = new ArrayList<>();
        in.readList(mAcitivityImages, Drawable.class.getClassLoader());
        mLocation = in.readString();
        mInfo = in.readString();
    }
}
