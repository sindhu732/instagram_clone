package com.cornell.sindhu.instagram.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cornell.sindhu.instagram.Home.HomeActivity;
import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;
import com.cornell.sindhu.instagram.Utils.MainFeedListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by sindhu on 12/3/17.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUMBER = 1;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = mDatabase.getReference();

    private EditText mSearchTerm;
    private ImageButton mSearchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationBar();
        mAuth = FirebaseAuth.getInstance();
        mSearchTerm = findViewById(R.id.searchTerm);
        mSearchButton = findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = mSearchTerm.getText().toString();
                Log.d(TAG, "Search: " + searchTerm);

                String pathPosts = "posts";
                if(searchTerm.length() > 0) {
                    if(currentUser == null) {
                        pathPosts += "/public";
                    } else {
                        pathPosts += "/private";
                    }

                    downloadMyFeed();
                }
            }
        });
        
    }

    private void downloadMyFeed() {
        final ArrayList<Post> posts = new ArrayList<>();
        DatabaseReference myRef = mDatabase.getReference("posts");
        final FirebaseUser currentUser = mAuth.getCurrentUser();

            myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d(TAG, "got Data " + dataSnapshot.getChildren().toString());

                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    for(DataSnapshot snapshot1: snapshot.getChildren()) {
                        posts.add(snapshot1.getValue(Post.class));
                        //Log.d(TAG, "got Post: " + snapshot1.getValue().toString());
                    }
                }

                Log.d(TAG, posts.toString());

//                listAdapter = new MainFeedListAdapter(SearchActivity.this, R.layout.layout_feed_list_item, posts);
//                mFeed.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "User signed in already");
        } else {
            Log.d(TAG, "User not logged in");
            Toast.makeText(SearchActivity.this, "Sign In to search your private uploads", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}
