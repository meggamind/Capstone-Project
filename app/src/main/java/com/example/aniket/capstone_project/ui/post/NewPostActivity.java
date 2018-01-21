package com.example.aniket.capstone_project.ui.post;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.Post;
import com.example.aniket.capstone_project.data.User;
import com.example.aniket.capstone_project.ui.drawer.BaseActivity;
import com.example.aniket.capstone_project.ui.explore.ExploreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class NewPostActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final int RC_TAKE_PICTURE = 101;


    private EditText mBodyField;
    private FirebaseAuth mAuth;
    private Uri mFileUri = null;
    private EditText mTitleField;
    private Uri mDownloadUrl = null;
    private EditText mLocationField;
    private Button mUploadPhotoButton;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;
    private FloatingActionButton mSubmitButton;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mBodyField = findViewById(R.id.field_body);
        mTitleField = findViewById(R.id.field_title);
        mLocationField = findViewById(R.id.field_location);
        mSubmitButton = findViewById(R.id.fab_submit_post);
        mUploadPhotoButton = findViewById(R.id.button_camera);

        mSubmitButton.setOnClickListener(this);
        mUploadPhotoButton.setOnClickListener(this);
        // Restore instance state
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
        }
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                hideProgressDialog();
                switch (intent.getAction()) {
                    case ImageUploadService.UPLOAD_COMPLETED:
                    case ImageUploadService.UPLOAD_ERROR:
                        onUploadResultIntent(intent);
                        break;
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(mBroadcastReceiver, ImageUploadService.getIntentFilter());
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }


    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
    }


    private void onUploadResultIntent(Intent intent) {
        mDownloadUrl = intent.getParcelableExtra(ImageUploadService.EXTRA_DOWNLOAD_URL);
        mFileUri = intent.getParcelableExtra(ImageUploadService.EXTRA_FILE_URI);
        updateUI(mAuth.getCurrentUser());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.d("onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        if (requestCode == RC_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                mFileUri = data.getData();

                if (mFileUri != null) {
                    uploadFromUri(mFileUri);
                } else {
                    Timber.w("File URI is null");
                }
            } else {
                Toast.makeText(this, getString(R.string.picture_failure), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void uploadFromUri(Uri fileUri) {
        mFileUri = fileUri;
        updateUI(mAuth.getCurrentUser());
        startService(new Intent(this, ImageUploadService.class)
                .putExtra(ImageUploadService.EXTRA_FILE_URI, fileUri)
                .setAction(ImageUploadService.ACTION_UPLOAD));
        showProgressDialog(getString(R.string.progress_uploading));
    }


    private void updateUI(FirebaseUser user) {
    }


    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();
        final String location = mLocationField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(location)) {
            mLocationField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        if (mDownloadUrl == null) {
            return;
        }
        final String photo = mDownloadUrl.toString();

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, getString(R.string.post_in_progress), Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        if (user == null) {
                            // User is null, error out
                            Timber.e("User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    getString(R.string.user_fetch_error),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            writeNewPost(userId, user.username, title, body, photo, location);
                        }
                        setEditingEnabled(true);
                        finish();
                        Intent intent = new Intent(NewPostActivity.this, ExploreActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.w("getUser:onCancelled" + databaseError.toException());
                        setEditingEnabled(true);
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    private void writeNewPost(String userId, String username, String title, String body, String photo, String location) {
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body, photo, location);
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
        childUpdates.put("/location-posts/" + location + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_camera) {
            launchCamera();
        } else if (i == R.id.fab_submit_post) {
            submitPost();
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_TAKE_PICTURE);
    }
}
