package com.morningwoodsoftware.android.spuge;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.channel.impl.sms.ChannelSmsImpl;
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
	public void sendMessage(Venue venue)
	{
		// TODO: Send using channel
	}
	
	/**
	 * Gets a message matching passed venue
	 * 
	 * @param venu
	 * @return
	 */
	private Message getMessage(Venue venue)
	{
		// TODO: Return matching message
		return null;
	}
}
