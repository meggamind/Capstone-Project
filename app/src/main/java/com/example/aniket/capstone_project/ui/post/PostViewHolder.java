package com.example.aniket.capstone_project.ui.post;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.Post;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.florent37.picassopalette.PicassoPalette;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * Created by aniket on 1/7/18.
 */

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

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);

        numStarsView.setText(String.valueOf(post.starCount));

//        bodyView.setText(post.body);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        if (post.photo != null) {
            Timber.d("Aniket4, : " + post.photo);
            StorageReference storageReference = storage.getReferenceFromUrl(post.photo);

//            Glide.with(itemView.getContext())
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .placeholder(R.drawable.user_placeholder)
//                    .error(R.drawable.user_placeholder_error)
//                    .into(mThingToDoImage);

//            String url =

            Picasso.with(itemView.getContext()).load(post.photo).into(mThingToDoImage,
                    PicassoPalette.with(post.photo, mThingToDoImage)
                            .use(PicassoPalette.Profile.MUTED_DARK)
                            .intoBackground(mMetaBar)
                            .intoTextColor(titleView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                            .intoTextColor(mLocationView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(authorView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(numStarsView, PicassoPalette.Swatch.BODY_TEXT_COLOR)

            );


        }

        if (post.location != null){
            mLocationView.setText(post.location);
        }

            starView.setOnClickListener(starClickListener);
    }
}

