package com.cornell.sindhu.instagram.Upload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cornell.sindhu.instagram.Account.AccountActivity;
import com.cornell.sindhu.instagram.Manifest;
import com.cornell.sindhu.instagram.Models.Post;
import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;
import com.cornell.sindhu.instagram.Utils.Permissions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by sindhu on 12/3/17.
 */

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";
    private static final int ACTIVITY_NUMBER = 2;
    private static final int GALLERY_IMAGE_SELECTED = 6;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef;

    private Button galleryButton, postButton;
    private ImageView uploadedImage;
    private EditText mDescription;
    private RadioButton mPrivateState;

    String imageDownloadUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationBar();

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mDescription = findViewById(R.id.description);
        mPrivateState = findViewById(R.id.makePrivateButton);

        galleryButton = findViewById(R.id.galleryButton);
        postButton = findViewById(R.id.postButton);

        imageDownloadUrl = "";
        uploadedImage = findViewById(R.id.uploadedImage);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Launching gallery");
                imageDownloadUrl = ""; // Reset

                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_IMAGE_SELECTED);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mDescription.getText().toString();
                boolean privateState = mPrivateState.isChecked();
                String uid = currentUser.getUid();
                Post newPost = new Post(uid, currentUser.getDisplayName(), currentUser.getEmail(), privateState, imageDownloadUrl, description);
                myRef.child("posts").child(uid).push().setValue(newPost);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_IMAGE_SELECTED && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uploadedImage.setImageURI(selectedImage);
            uploadImageToFirebase(selectedImage);
        }
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

    private void uploadImageToFirebase(Uri file) {
        String filename = "ImageUniqueName.jpg";
        currentUser = mAuth.getCurrentUser();
        StorageReference postedImagesRef = mStorageRef.child(currentUser.getUid()).child(filename);

        postedImagesRef.putFile(file)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    imageDownloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Log.d(TAG, "success: Image upload");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, "failed: Image upload");
                }
            });
    }


}
