package com.morningwoodsoftware.android.spuge.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.morningwoodsoftware.android.R;

public class ActivityUtil
{
    public static boolean onCreateOptionsMenu(Activity activity, Menu menu)
    {
        activity.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public static boolean onOptionsItemSelected(Activity activity, MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.itemPrefs:
                activity.startActivity(new Intent(activity, PrefsActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.itemVenue:
                activity.startActivity(new Intent(activity, VenueActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
        return true;
    }
}
