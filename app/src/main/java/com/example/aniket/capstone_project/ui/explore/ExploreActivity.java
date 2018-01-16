package com.example.aniket.capstone_project.ui.explore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.explore.ThingsToDoConstants;
import com.example.aniket.capstone_project.ui.post.NewPostActivity;
import com.example.aniket.capstone_project.ui.post.RecentPostsFromAllUsersFragment;
import com.example.aniket.capstone_project.ui.userprofile.UserProfileActivity;
import com.example.aniket.capstone_project.utils.view.ScreenUI;
import com.example.aniket.capstone_project.widgets.UpdatePostService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;
import cn.hugeterry.coordinatortablayout.listener.LoadHeaderImagesListener;

public class ExploreActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private List<String> mImageArray;
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private List<String> mTitles;
    private static final String SELECTED_TABS = "selected_tab";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        mTitles = new LinkedList<>();
        mImageArray = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userPostRef = mDatabase.child("posts");

        userPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp.child("location").getValue() != null) {
                        String location = dsp.child("location").getValue().toString();
                        if (!mTitles.contains(location)) {
                            mTitles.add(location);
                        }
                        mImageArray.add(dsp.child("photo").getValue().toString());
                    }
                }
                UpdatePostService.startPostWidgetService(getApplicationContext(), mImageArray);
                if (savedInstanceState != null) {
                    init(savedInstanceState.getInt(SELECTED_TABS, 0));
                } else {
                    init(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        super.onCreate(savedInstanceState);
    }


    private void init(int position) {
        setContentView(R.layout.activity_explore);

        initFragments();
        initViewPager();
        mCoordinatorTabLayout = findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTitle("")
                .setTranslucentStatusBar(this)
                .setLoadHeaderImagesListener(new LoadHeaderImagesListener() {
                    @Override
                    public void loadHeaderImages(ImageView imageView, TabLayout.Tab tab) {
                        loadImages(imageView, mImageArray.get(tab.getPosition()));
                    }
                })
                .setupWithViewPager(mViewPager);

        mCoordinatorTabLayout.getTabLayout().setTabMode(TabLayout.MODE_SCROLLABLE);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_explore:
                                Intent exploreIntent = new Intent(ExploreActivity.this, ExploreActivity.class);
                                startActivity(exploreIntent);
                                break;
                            case R.id.action_upload:
                                Intent newPostIntent = new Intent(ExploreActivity.this, NewPostActivity.class);
                                startActivity(newPostIntent);
                                break;
                            case R.id.action_profile:
                                Intent profileIntent = new Intent(ExploreActivity.this, UserProfileActivity.class);
                                int[] startingLocation = new int[2];
                                startingLocation[0] += ScreenUI.getScreenWidth(getApplicationContext()) / 2;
                                profileIntent.putExtra(ThingsToDoConstants.ARG_REVEAL_START_LOCATION, startingLocation);
                                startActivity(profileIntent);
                                break;
                        }
                        return true;
                    }
                });
        mCoordinatorTabLayout.getTabLayout()
                .getTabAt(position)
                .select();
    }


    private void loadImages(ImageView imageView, String url) {
        Picasso.with(getApplicationContext()).load(url).into(imageView);
    }


    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(RecentPostsFromAllUsersFragment.getInstance(title));
        }
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.vp);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(new ExploreAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    @Override
    protected void onSaveInstanceState(Bundle saved) {
        super.onSaveInstanceState(saved);
        saved.putInt(SELECTED_TABS,
                mCoordinatorTabLayout.getTabLayout().getSelectedTabPosition());
    }

}
