package com.cornell.sindhu.instagram.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by sindhu on 12/6/17.
 */

public class MainFeedListAdapter extends ArrayAdapter<Post>{

    private LayoutInflater mInflater;
    private int mLayoutReference;
    private Context mContext;
    private DatabaseReference mReference;
    private String currentUsername = "";
    private List<Post> posts;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }



    public MainFeedListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutReference = resource;
        this.mContext = context;
        this.posts = objects;
    }


    static class PostView{
        TextView displayName, description, privateStatus;
        ImageView imageView;
        URL image;
        Post post;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostView holder;
        if(convertView == null) {
            convertView = mInflater.inflate(mLayoutReference, parent, false);
            holder = new PostView();

            holder.displayName = convertView.findViewById(R.id.displayName);
            holder.description = convertView.findViewById(R.id.description);
            holder.imageView = convertView.findViewById(R.id.image);
            holder.privateStatus = convertView.findViewById(R.id.privateStatus);
            holder.post = getItem(position);
            convertView.setTag(holder);
        }
        else{
            holder = (PostView) convertView.getTag();
        }
        Picasso.with(mContext).load(posts.get(position).getImageUrl()).into(holder.imageView);
        return convertView;
    }

}









