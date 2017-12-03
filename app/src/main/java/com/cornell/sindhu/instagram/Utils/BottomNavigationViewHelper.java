package com.cornell.sindhu.instagram.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.cornell.sindhu.instagram.Account.AccountActivity;
import com.cornell.sindhu.instagram.Filters.FiltersActivity;
import com.cornell.sindhu.instagram.Home.HomeActivity;
import com.cornell.sindhu.instagram.R;
import com.cornell.sindhu.instagram.Search.SearchActivity;
import com.cornell.sindhu.instagram.Upload.UploadActivity;

/**
 * Created by constantin on 12/3/17.
 */

public class BottomNavigationViewHelper {

    public static void enableNavigation(final Context context, BottomNavigationView view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home: // ACTIVITY_NUMBER = 0
                        Intent homeIntent = new Intent(context, HomeActivity.class);
                        context.startActivity(homeIntent);
                        break;

                    case R.id.navigation_upload: // ACTIVITY_NUMBER = 1
                        Intent uploadIntent = new Intent(context, UploadActivity.class);
                        context.startActivity(uploadIntent);
                        break;

                    case R.id.navigation_search: // ACTIVITY_NUMBER = 2
                        Intent searchIntent = new Intent(context, SearchActivity.class);
                        context.startActivity(searchIntent);
                        break;

                    case R.id.navigation_account: // ACTIVITY_NUMBER = 3
                        Intent accountIntent = new Intent(context, AccountActivity.class);
                        context.startActivity(accountIntent);
                        break;

                    case R.id.navigation_filters: // ACTIVITY_NUMBER = 4
                        Intent filtersIntent = new Intent(context, FiltersActivity.class);
                        context.startActivity(filtersIntent);
                        break;
                }
                return false;
            }
        });
    }
}
