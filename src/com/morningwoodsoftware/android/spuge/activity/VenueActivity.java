package com.morningwoodsoftware.android.spuge.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.morningwoodsoftware.android.R;
import com.morningwoodsoftware.android.spuge.SpugeApplication;
import com.morningwoodsoftware.android.spuge.dto.Venue;
import com.morningwoodsoftware.android.spuge.exception.ApplicationException;
import com.morningwoodsoftware.android.spuge.exception.NotReadyException;
import com.morningwoodsoftware.android.spuge.util.DelayedCancelProgressDialog;

public class VenueActivity extends Activity
{
    public static final String TAG = "SPUGE";

    private SpugeApplication app;
    private LinearLayout venueLayout;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venues);

        this.app = (SpugeApplication) getApplication();
        this.venueLayout = (LinearLayout) findViewById(R.id.venueLayout);

        // render the venues
        renderVenues();
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

    private void renderVenues()
    {
        if (venueLayout != null)
        {
            List<Venue> venues = app.getVenues();
            for (int i=0; i<venues.size(); i++)
            {
                Venue venue = venues.get(i);
                Button b = new Button(this);
                b.setText(venue.getName());
                final int idx = i;
                b.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        DelayedCancelProgressDialog.showDialog(VenueActivity.this, "Loading", "Please wait while activity is loading", "Cancel");        

                        Log.d(TAG, "VenuesActivity.onClick: " + idx);
                        
                        try
                        {
                            //app.sendMessage(app.getVenueByIndex(idx));
                        }
                        catch (NotReadyException e)
                        {
                            Log.e("onClick", "NotReadyException", e);
                            // TODO: Output previous message still being sent
                        }
                        catch (ApplicationException e)
                        {
                            Log.e("onClick", "ApplicationException", e);
                            // TODO: Output app error
                        }
                        
                    }
                    
                });
                venueLayout.addView(b);
            }
        }
    }
}
