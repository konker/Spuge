package com.morningwoodsoftware.android.spuge.channel;

import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;
import com.morningwoodsoftware.android.spuge.exception.ApplicationException;

public interface Channel 
{
	/**
	 * Sends a message to a receiver with a listener
	 * 
	 * @param message
	 * @param receiver
	 */
	void send(Message message, Contact receiver, ChannelListener listener)
	throws ApplicationException;
	
	/**
	 * Being called when message is received.
	 * This method should be overriden upon Construct
	 * 
	 * @param message
	 */
	public void onReceive(Message message);
}
