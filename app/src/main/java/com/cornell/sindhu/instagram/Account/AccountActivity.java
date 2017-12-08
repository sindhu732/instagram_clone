package com.cornell.sindhu.instagram.Account;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.cornell.sindhu.instagram.Home.HomeActivity;
import com.cornell.sindhu.instagram.Models.User;
import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sindhu on 12/3/17.
 */

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";
    private static final int ACTIVITY_NUMBER = 3;

    private FirebaseAuth mAuth;
    private EditText mName, mEmail, mPassword, mEmailNew, mPasswordNew;
    private Button loginButton, signUpButton, googleButton, logoutButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationBar();
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mName = findViewById(R.id.input_name);
        mEmailNew = findViewById(R.id.input_email_new);
        mPasswordNew = findViewById(R.id.input_password_new);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to Log in");

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (email != null && password != null) {
                    signInWithEmail(email, password);
                } else {
                    Toast.makeText(AccountActivity.this, "Email and password fields missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to Log in");

                String name = mName.getText().toString();
                String email = mEmailNew.getText().toString();
                String password = mPasswordNew.getText().toString();

                if (email != null && password != null) {
                    signUpWithEmail(name, email, password);
                } else {
                    Toast.makeText(AccountActivity.this, "Input fields missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Log.d(TAG, "onClick: attempting to Log out");
                Toast.makeText(AccountActivity.this, "Signed Out. Sign In again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(AccountActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

    private void signInWithEmail(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AccountActivity.this, "Authentication successful",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure. Signing Up for new account instead.", task.getException());
                            Toast.makeText(AccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUpWithEmail(final String name, final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AccountActivity.this, "Authentication successful",
                                    Toast.LENGTH_SHORT).show();

                            // add name to Account
                            createUserProfile(name, user.getUid(), email);

                            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserProfile(String name, String uid, String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        User user = new User(uid, name, email);
        myRef.child("users").child(uid).setValue(user);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()) {
                               Log.d(TAG, "User display_name added");
                           }
                       }
                   }
                );
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}