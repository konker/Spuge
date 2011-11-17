package com.morningwoodsoftware.android.spuge;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.channel.ChannelListener;
import com.morningwoodsoftware.android.spuge.channel.impl.sms.ChannelSmsImpl;
import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;
import com.morningwoodsoftware.android.spuge.dto.Venue;

public class SpugeApplication extends Application  
{
	private Map<String, Venue> venues = new HashMap<String, Venue>();
	private Channel channel;
	
	@Override
	public void onCreate()
	{
		this.channel = new ChannelSmsImpl();
		
		// TODO: Init venues
		// TESTS
        readVenues();
		
		super.onCreate();
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
	
	/**
	 * Sends a venue using a Channel
	 * 
	 * @param venue
	 */
	public void sendMessage(Venue venue, final Intent intent)
	{
		// TODO: Get message from venue
		// Message message = getMessage(venue);
		
		// TODO: Get receiver
		
		// TESTING: Foo message & receiver
		Message message = new Message("1", "Some message body");
		Contact receiver = new Contact("5554");
		
		// TODO: Make this properly
		channel.send(message, receiver, 
				new ChannelListener( this.getApplicationContext() ) 
		{
			@Override
			public void onMessageSent() {
				Log.d("sendMessage()", "Message sent successfully!");
				// TODO Auto-generated method stub
			}

			@Override
			public void onMessageFailed() {
				Log.d("sendMessage()", "Message failed!");
				// TODO Auto-generated method stub
			}

			@Override
			public void onSentMessageReceived() {
				Log.d("sendMessage()", "Sent Message received!");
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
		// TODO: Return matching message
		return null;
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
}
