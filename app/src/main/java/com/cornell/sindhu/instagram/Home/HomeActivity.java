package com.cornell.sindhu.instagram.Home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cornell.sindhu.instagram.Account.AccountActivity;
import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.Models.User;
import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;
import com.cornell.sindhu.instagram.Utils.MainFeedListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUMBER = 0;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ListView mFeed;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        setContentView(R.layout.fragment_feed);
        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationBar();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        mFeed = (ListView) findViewById(R.id.myFeed);
        Log.d(TAG, mFeed.toString());
        downloadMyFeed();
    }

    private void downloadMyFeed() {

        final ArrayList<Post> posts = new ArrayList<>();
        DatabaseReference myRef = mDatabase.getReference("posts");
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.child("public").getChildren()) {
                    for(DataSnapshot snapshot1: snapshot.getChildren()) {
                        posts.add(snapshot1.getValue(Post.class));
                    }
                }

                if (currentUser != null) {
                    for(DataSnapshot snapshot: dataSnapshot.child("private").child(currentUser.getUid()).getChildren()) {
                        posts.add(snapshot.getValue(Post.class));
                    }
                }

                Log.d(TAG, posts.toString());

                listAdapter = new MainFeedListAdapter(HomeActivity.this, R.layout.layout_feed_list_item, posts);
                mFeed.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(HomeActivity.this, bottomNavigationView);
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
