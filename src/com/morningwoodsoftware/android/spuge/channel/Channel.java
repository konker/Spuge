package com.morningwoodsoftware.android.spuge.channel;

import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;

public interface Channel 
{
	/**
	 * Sends a message to a receiver
	 * 
	 * @param message
	 * @param receiver
	 */
	void send(Message message, Contact receiver);
}
