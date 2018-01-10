package com.example.aniket.capstone_project.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aniket on 1/7/18.
 */

@IgnoreExtraProperties
public class Post implements Parcelable{

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();
    public String photo;
    public String location;

    public Post() {
    }

    public Post(String uid, String author, String title, String body, String photo, String location) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.photo = photo;
        this.location = location;
    }

    protected Post(Parcel in) {
        uid = in.readString();
        author = in.readString();
        title = in.readString();
        body = in.readString();
        starCount = in.readInt();
        photo = in.readString();
        location = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("photo", photo);
        result.put("location", location);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeInt(starCount);
        dest.writeString(photo);
        dest.writeString(location);
    }
}
