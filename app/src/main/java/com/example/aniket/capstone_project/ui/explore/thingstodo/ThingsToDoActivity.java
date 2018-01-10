package com.example.aniket.capstone_project.ui.explore.thingstodo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.Post;
import com.example.aniket.capstone_project.data.explore.ThingsToDoConstants;
import com.example.aniket.capstone_project.data.todo.ActivityToDo;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.florent37.picassopalette.PicassoPalette;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.klinker.android.sliding.MultiShrinkScroller;
import com.klinker.android.sliding.SlidingActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ThingsToDoActivity extends SlidingActivity {

    @Override
    public void init(Bundle savedInstanceState) {
        setTitle("Activity Title");

        setPrimaryColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        setContent(R.layout.activity_things_to_do);
        setHeaderContent(R.layout.activity_things_to_do_header);
        BlurLayout sampleLayout = findViewById(R.id.things_to_do_header);


        View hover = LayoutInflater.from(getApplicationContext()).inflate(R.layout.acitivity_things_to_do_header_hover, null);
        ImageView heart = hover.findViewById(R.id.heart);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada)
                        .duration(350)
                        .playOn(v);
                if(!v.isSelected()){
                    v.setSelected(true);
                }else{
                    v.setSelected(false);
                }
            }
        });


        hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Swing)
                        .duration(350)
                        .playOn(v);
            }
        });
        sampleLayout.setHoverView(hover);
        sampleLayout.setBlurDuration(250);
        sampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
        sampleLayout.addChildAppearAnimator(hover, R.id.share, Techniques.FlipInX, 550, 250);
        sampleLayout.addChildAppearAnimator(hover, R.id.more, Techniques.FlipInX, 550, 500);

        sampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);
        sampleLayout.addChildDisappearAnimator(hover, R.id.share, Techniques.FlipOutX, 550, 250);
        sampleLayout.addChildDisappearAnimator(hover, R.id.more, Techniques.FlipOutX, 550, 0);

//        sampleLayout.addChildAppearAnimator(hover, R.id.description, Techniques.FadeInUp);
//        sampleLayout.addChildDisappearAnimator(hover, R.id.description, Techniques.FadeOutDown);


        Intent intent = getIntent();
        Post post = intent.getExtras().getParcelable("mDatas");
        setTitle(post.title);


        TextView info = findViewById(R.id.detail_post_body);

        info.setText(post.body);

        String imageName = post.photo;


        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(
                ThingsToDoConstants.FIREBASE_STORAGE_LINK +
                        imageName);


        ImageView imageView = findViewById(R.id.to_do_header_image);



        Picasso.with(getApplicationContext()).load(imageName).into(imageView,
                PicassoPalette.with(post.photo, imageView)
                        .use(PicassoPalette.Profile.MUTED_DARK));
//                        .intoBackground(mMetaBar)
//                        .intoTextColor(titleView, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
//                        .intoTextColor(mLocationView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
//                        .intoTextColor(authorView, PicassoPalette.Swatch.BODY_TEXT_COLOR)
//                        .intoTextColor(numStarsView, PicassoPalette.Swatch.BODY_TEXT_COLOR)

    }

    @Override
    protected void configureScroller(MultiShrinkScroller scroller) {
        super.configureScroller(scroller);
        scroller.setIntermediateHeaderHeightRatio(1);
    }
}
