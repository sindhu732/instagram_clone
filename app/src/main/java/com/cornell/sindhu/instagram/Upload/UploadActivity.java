package com.cornell.sindhu.instagram.Upload;

import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.cornell.sindhu.instagram.Account.AccountActivity;
import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sindhu on 12/3/17.
 */

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";
    private static final int ACTIVITY_NUMBER = 2;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button postButton;
    private EditText mDescription;
    private RadioButton mPrivateState;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationBar();

        mAuth = FirebaseAuth.getInstance();

        mDescription = findViewById(R.id.description);
        mPrivateState = findViewById(R.id.makePrivateButton);
        postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mDescription.getText().toString();
                boolean privateState = mPrivateState.isChecked();
                String uid = currentUser.getUid();
                Post newPost = new Post(uid, currentUser.getDisplayName(), currentUser.getEmail(), privateState, "blah.png", description);
                myRef.child("posts").child(uid).push().setValue(newPost);
            }
        });
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(UploadActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "User signed in already");
        } else {
            Log.d(TAG, "User not logged in");
//            Intent intent = new Intent(UploadActivity.this, AccountActivity.class);
//            startActivity(intent);
            Toast.makeText(UploadActivity.this, "Can't upload without Logging in", Toast.LENGTH_SHORT).show();
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
