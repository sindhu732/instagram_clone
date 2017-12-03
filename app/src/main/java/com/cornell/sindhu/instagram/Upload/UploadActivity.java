package com.cornell.sindhu.instagram.Upload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cornell.sindhu.instagram.Utils.BottomNavigationViewHelper;
import com.cornell.sindhu.instagram.R;

/**
 * Created by sindhu on 12/3/17.
 */

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";
    private static final int ACTIVITY_NUMBER = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Log.d(TAG, "onCreate: started");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.enableNavigation(UploadActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUMBER);
        menuItem.setChecked(true);
    }

}
