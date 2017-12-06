package com.cornell.sindhu.instagram.Utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.R;
import com.google.firebase.database.DatabaseReference;

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

    public MainFeedListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutReference = resource;
        this.mContext = context;
    }

    static class PostView{
        TextView displayName, description, privateStatus;
        ImageView image;
        Post post;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final PostView holder;

        if(convertView == null) {
            convertView = mInflater.inflate(mLayoutReference, parent, false);
            holder = new PostView();

            holder.displayName = (TextView) convertView.findViewById(R.id.displayName);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.privateStatus = (TextView) convertView.findViewById(R.id.privateStatus);

            holder.post = getItem(position);
        }

        return convertView;
    }

}









