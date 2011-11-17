package com.morningwoodsoftware.android.spuge;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Application;
import android.util.Log;

import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.channel.ChannelListener;
import com.morningwoodsoftware.android.spuge.channel.impl.sms.ChannelSmsImpl;
import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;
import com.morningwoodsoftware.android.spuge.dto.Venue;
import com.morningwoodsoftware.android.spuge.exception.ApplicationException;
import com.morningwoodsoftware.android.spuge.exception.NotReadyException;
import com.morningwoodsoftware.android.spuge.util.VenueParser;

public class SpugeApplication extends Application implements OnSharedPreferenceChangeListener
{
    private static final String TAG = "SPUGE";
    private static final String KEY_NUMBER = "number";

	private Map<String, Venue> venues = new HashMap<String, Venue>();
	private Channel channel;
	private boolean sending = false;
    private String number;
	
	@Override
	public void onCreate()
	{
		this.channel = new ChannelSmsImpl()
		{
			@Override
			public void onReceive(Message message)
			{
				// TODO: What to do next?
				AlertDialog.Builder popup = new AlertDialog.Builder(SpugeApplication.this);
				popup.setTitle("New SMS");
				popup.setIcon(android.R.drawable.ic_dialog_alert);
				popup.setMessage( message.getBody() );
				popup.show();
			}
		};
		
		// TODO: Init venues
		// TESTS
        readVenues();
		
        // Get number from preferences and listen for changes to this number
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        this.number = prefs.getString(KEY_NUMBER, null);
        
		super.onCreate();
	}

	/**
	 * Returns number to send messages to
	 * 
	 * @return
	 */
    public String getNumber() {
        return number;
    }

	/**
	 * Returns list of venues
	 * 
	 * @return
	 */
	public Map<String, Venue> getVenues()
	{
		return venues;
	}
	
    public Venue getVenueByName(String name) {
        return venues.get(name);
    }

	/**
	 * Sends a venue using a Channel
	 * 
	 * @param venue
	 */
	public void sendMessage(Venue venue)
	throws NotReadyException, ApplicationException
	{
		if (sending) {
			throw new NotReadyException("Still sending previous message!");
        }
		
		// TODO: Get message from venue
		Message message = getMessage(venue);
		Contact receiver = new Contact(number);
		
		// TODO: Get receiver
		
		// TODO: Remove below when fixed
        /*
		Message message = new Message("Some message body");
		Contact receiver = new Contact("5556");
        */
		
		SpugeApplication.this.sending = true;
		
		// TODO: Implement
		channel.send(message, receiver, 
				new ChannelListener( this.getApplicationContext() ) 
		{
			@Override
			public void onMessageSent() {
				Log.d("sendMessage()", "Message sent successfully!");
				SpugeApplication.this.sending = false;
				// TODO Auto-generated method stub
			}

			@Override
			public void onMessageFailed() {
				Log.d("sendMessage()", "Message failed!");
				SpugeApplication.this.sending = false;
				// TODO Auto-generated method stub
			}

			@Override
			public void onSentMessageReceived() {
				Log.d("sendMessage()", "Sent Message received!");
				SpugeApplication.this.sending = false;
				// TODO Auto-generated method stub
			}
		});
	}
	
	/**
	 * Gets a message matching passed venue
	 * 
	 * @param venue
	 * @return
	 */
	private Message getMessage(Venue venue)
	{
		// TODO: id?
		return new Message("FIXME:ID", venue.getQuestion());
	}

	/**
	 * Reads and constructs a list of venues
	 * 
	 * @return
	 */
    private void readVenues() {
        VenueParser parser = new VenueParser(this);
        //FIXME: use setVenues(..) ?
        //FIXME: exceptions
        try {
            venues = parser.readVenues();
        }
        catch(Exception ex) {
            //FIXME: now what?
        }
    }

    /* method required by OnSharedPreferenceChangeListener */
    @Override
    public synchronized void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(KEY_NUMBER)) {
            number = prefs.getString(KEY_NUMBER, null);
        }
        return;
    }
}
