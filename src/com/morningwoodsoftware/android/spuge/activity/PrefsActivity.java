package com.morningwoodsoftware.android.spuge.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.morningwoodsoftware.android.R;
import com.morningwoodsoftware.android.spuge.SpugeApplication;

public class PrefsActivity extends PreferenceActivity
{
    public static final String TAG = "SPUGE";

    SpugeApplication app;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        app = (SpugeApplication) getApplication();

        addPreferencesFromResource(R.xml.prefs);
        setContentView(R.layout.prefs);

        Log.d(TAG, "PrefsActivity: onCreate");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "PrefsActivity.onPause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "PrefsActivity.onResume");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "PrefsActivity.onStart");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "PrefsActivity.onRestart");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "PrefsActivity.onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "PrefsActivity.onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return ActivityUtil.onCreateOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return ActivityUtil.onOptionsItemSelected(this, item);
    }
}
