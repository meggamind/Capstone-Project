package com.example.aniket.capstone_project.ui.explore.thingstodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.Post;
import com.github.florent37.picassopalette.PicassoPalette;
import com.klinker.android.sliding.MultiShrinkScroller;
import com.klinker.android.sliding.SlidingActivity;
import com.squareup.picasso.Picasso;

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
                if (!v.isSelected()) {
                    v.setSelected(true);
                } else {
                    v.setSelected(false);
                }
            }
        });
        sampleLayout.setHoverView(hover);
        sampleLayout.setBlurDuration(250);
        sampleLayout.addChildAppearAnimator(hover, R.id.heart, Techniques.FlipInX, 550, 0);
        sampleLayout.addChildDisappearAnimator(hover, R.id.heart, Techniques.FlipOutX, 550, 500);

        Intent intent = getIntent();
        Post post = intent.getExtras().getParcelable("mDatas");
        setTitle(post.title);

        TextView info = findViewById(R.id.detail_post_body);
        info.setText(post.body);
        String imageName = post.photo;

        ImageView imageView = findViewById(R.id.to_do_header_image);
        Picasso.with(getApplicationContext()).load(imageName).into(imageView,
                PicassoPalette.with(post.photo, imageView)
                        .use(PicassoPalette.Profile.MUTED_DARK));
    }

    @Override
    protected void configureScroller(MultiShrinkScroller scroller) {
        super.configureScroller(scroller);
        scroller.setIntermediateHeaderHeightRatio(1);
    }
}
