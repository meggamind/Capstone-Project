package com.example.aniket.capstone_project.ui.explore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aniket.capstone_project.R;
import com.example.aniket.capstone_project.data.explore.ThingsToDoConstants;
import com.example.aniket.capstone_project.data.todo.ActivityToDo;
import com.example.aniket.capstone_project.ui.explore.thingstodo.ThingsToDoActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniket on 1/3/18.
 */

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ExploreCityViewHolder> {
    private Context mContext;
    private List<ActivityToDo> mDatas;
    List<ActivityToDo> mActivitites = new ArrayList<>();

    public MenuRecyclerAdapter(Context context, List<ActivityToDo> activitites) {
        mContext = context;
        mDatas = activitites;

    }

    @Override
    public MenuRecyclerAdapter.ExploreCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExploreCityViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.things_to_do_city, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuRecyclerAdapter.ExploreCityViewHolder holder, final int position) {


        String imageName = mDatas.get(position).getallActivityImages().get(0);


        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(
                ThingsToDoConstants.FIREBASE_STORAGE_LINK +
                        imageName);

        Glide.with(mContext)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(holder.image);


        System.out.println("Aniket1, name: " + imageName);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(addArgs(new Intent(mContext, ThingsToDoActivity.class), position));
            }
        });
        holder.location.setText(mDatas.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ExploreCityViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView location;
        ImageView image;

        public ExploreCityViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.post_title);
            location = itemView.findViewById(R.id.thing_to_do_location);
            image = itemView.findViewById(R.id.thing_to_do_image);
        }
    }

    private Intent addArgs(Intent intent, int position) {
        intent.putExtra("mDatas", mDatas.get(position));
        return intent;
    }

}
