package com.example.aniket.capstone_project.ui.post;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.aniket.capstone_project.data.todo.ActivityToDo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by aniket on 1/7/18.
 */

public class RecentPostsFromAllUsersFragment extends PostListFragment {


    private static final String ARG_TITLE = "title";
    private String mTitle;
    private RecyclerView mRecyclerView;
    private List<ActivityToDo> mDatas = new ArrayList<>();

    public static RecentPostsFromAllUsersFragment getInstance(String title) {
        RecentPostsFromAllUsersFragment fra = new RecentPostsFromAllUsersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        Timber.d("Aniket5, add titles");
        fra.setArguments(bundle);
        return fra;
    }

    public RecentPostsFromAllUsersFragment(){}

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Timber.d("Aniket5, in query");
        Query recentPostsQuery = databaseReference.child("location-posts")
                .child(mTitle);

        return recentPostsQuery;
    }
}
