package com.morningwoodsoftware.android.spuge.activity;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.morningwoodsoftware.android.R;
import com.morningwoodsoftware.android.spuge.SpugeApplication;
import com.morningwoodsoftware.android.spuge.dto.Venue;
import com.morningwoodsoftware.android.spuge.exception.ApplicationException;
import com.morningwoodsoftware.android.spuge.exception.NotReadyException;

public class VenueActivity extends Activity implements OnClickListener
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

        this.app = (SpugeApplication)getApplication();
        this.venueLayout = (LinearLayout)findViewById(R.id.venueLayout);

        // render the venues
        renderVenues();
    }

    private void renderVenues() {
        if (venueLayout != null) {
            Map<String, Venue> venues = app.getVenues();
            for (Venue venue : venues.values()) {
                Button b = new Button(this);
                b.setText(venue.getName());
                b.setOnClickListener(this);
                venueLayout.addView(b);
            }
        }
    }

    public void onClick(View view) 
    {
        Log.d(TAG, "VenuesActivity.onClick: " + ((Button)view).getText());
        
        try
        {
        	// TODO: Get venue
        	app.sendMessage( null );
        }
        catch(NotReadyException e)
        {
        	// TODO: Output previous message still being sent
        }
        catch(ApplicationException e)
        {
        	// TODO: Output app error
        }
    }
}
