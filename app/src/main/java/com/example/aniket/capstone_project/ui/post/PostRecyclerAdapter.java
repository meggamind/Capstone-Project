package com.example.aniket.capstone_project.ui.post;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.Post;
import com.example.aniket.capstone_project.ui.explore.thingstodo.ThingsToDoActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.florent37.picassopalette.PicassoPalette;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * Created by aniket on 1/19/18.
 */

public class PostRecyclerAdapter extends FirebaseRecyclerAdapter<Post, PostRecyclerAdapter.PostViewHolder> {
    private Context mContext;
    private Post[] mPostItems;

    public PostRecyclerAdapter(FirebaseRecyclerOptions<Post> options, Context context) {
        super(options);
        mContext = context;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView authorView;
        public ImageView starView;
        public TextView numStarsView;
        public TextView bodyView;
        public ImageView postImage;
        private ImageView mThingToDoImage;
        private TextView mLocationView;
        public View mMetaBar;

        public PostViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.post_title);
            authorView = itemView.findViewById(R.id.post_author);
            starView = itemView.findViewById(R.id.star);
            numStarsView = itemView.findViewById(R.id.post_num_stars);
            bodyView = itemView.findViewById(R.id.post_body);
            mThingToDoImage = itemView.findViewById(R.id.thing_to_do_image);
            mLocationView = itemView.findViewById(R.id.thing_to_do_location);
            mMetaBar = itemView.findViewById(R.id.meta_bar);
        }
    }


    @Override
    protected void onBindViewHolder(PostViewHolder viewHolder, int position, final Post model) {
        final DatabaseReference postRef = getRef(position);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Timber.d("Aniket5.5, onBindViewHolder");
        setPostData(model, viewHolder);

        final String postKey = postRef.getKey();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ThingsToDoActivity.class);
                intent.putExtra("mDatas", model);
                mContext.startActivity(intent);
            }
        });

        // Determine if the current user has liked this post and set UI accordingly
        if (model.stars.containsKey(getUid())) {
            viewHolder.starView.setImageResource(R.drawable.ic_toggle_star);
        } else {
            viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
        }

        viewHolder.starView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());
                DatabaseReference locationPostRef = mDatabase.child("location-posts").child(model.location).child(postRef.getKey());
                onStarClicked(locationPostRef);
                onStarClicked(globalPostRef);
                onStarClicked(userPostRef);
            }
        });
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        Timber.d("Aniket5.3, onCreateViewHolder");

        return new PostViewHolder(inflater.inflate(R.layout.things_to_do_city2, viewGroup, false));
    }

    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Timber.d("postTransaction:onComplete:" + databaseError);
            }
        });
    }

    public void setPostData(Post post, PostViewHolder viewHolder) {
        Timber.i("Aniket5.3: Setting Post data! ");
//        mPostItems = new Post[posts.size()];
//        for (int i = 0; i < posts.size(); i++) {
//            mPostItems[i] = new Post();
        viewHolder.titleView.setText(post.title);
        viewHolder.authorView.setText(post.author);
        viewHolder.numStarsView.setText(String.valueOf(post.starCount));
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            if (posts.get(i).photo != null) {
        Timber.d("Aniket5.4, : " + post.photo);
//                mPostItems[i].photo = posts.get(i).photo;
//            StorageReference storageReference = storage.getReferenceFromUrl(posts.get(i).photo);


        Picasso.with(mContext).load(post.photo).into(viewHolder.mThingToDoImage,
                PicassoPalette.with(post.photo, viewHolder.mThingToDoImage)
                        .use(PicassoPalette.Profile.MUTED_DARK)
                        .intoBackground(viewHolder.mMetaBar)
                        .intoTextColor(viewHolder.titleView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                        .intoTextColor(viewHolder.mLocationView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                        .intoTextColor(viewHolder.authorView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                        .intoTextColor(viewHolder.numStarsView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
        );
        if (post.location != null) {
            viewHolder.mLocationView.setText(post.location);
        }
//        viewHolder.starView.setOnClickListener(starClickListener);
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
