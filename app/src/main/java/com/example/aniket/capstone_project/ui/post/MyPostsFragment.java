package com.example.aniket.capstone_project.ui.post;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by aniket on 1/7/18.
 */

public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts

        return databaseReference.child("user-posts")
                .child(getUid());
    }
}