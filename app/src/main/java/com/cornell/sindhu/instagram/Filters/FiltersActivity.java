package com.cornell.sindhu.instagram.Filters;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;
import com.cornell.sindhu.instagram.Utils.MainFeedListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by sindhu on 12/3/17.
 */

public class FiltersActivity extends AppCompatActivity {
    private static final String TAG = "ProcessedFiltersActivity";
    private static final int ACTIVITY_NUMBER = 4;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ListView mFeed;
    private MainFeedListAdapter listAdapter;
    private ArrayList<Post> processedImages = new ArrayList<>();
    private StorageReference mStorageRef;
    private String processedImageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feed);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationBar();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mFeed = findViewById(R.id.myFeed);
        Log.d(TAG, mFeed.toString());
        listAdapter = new MainFeedListAdapter(FiltersActivity.this, R.layout.layout_feed_list_item, processedImages);
        mFeed.setAdapter(listAdapter);
        downloadAsciiFeed();
    }

    private void downloadAsciiFeed() {

        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null){

            DatabaseReference privateRef = mDatabase.getReference("posts/private");

            //DatabaseReference privateRef = mDatabase.getReference("processed/private");
            privateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        for(DataSnapshot snapshot1: snapshot.getChildren()) {
                            final Post post = snapshot1.getValue(Post.class);
                            String imageName = post.getImageName();
                            Log.d(TAG, imageName);
                            mStorageRef.child("ascii-" + currentUser.getUid() + "/"+ imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    processedImageUrl = uri.toString();
                                    Log.d(TAG, processedImageUrl);
                                    post.setImageUrl(processedImageUrl);
                                    Log.d(TAG, post.getImageUrl());
                                    processedImages.add(post);

                                    Log.d(TAG, processedImages.toString());

                                    listAdapter.setPosts(processedImages);
                                    listAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(FiltersActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "User signed in already");
        } else {
            Log.d(TAG, "User not logged in");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}