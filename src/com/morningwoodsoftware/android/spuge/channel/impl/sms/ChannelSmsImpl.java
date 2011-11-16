package com.morningwoodsoftware.android.spuge.channel.impl.sms;

import android.telephony.SmsManager;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;

public class ChannelSmsImpl implements Channel
{

	@Override
	public void send(Message message, Contact receiver) 
	{
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(receiver.getMobileNr(), null, message.getBody(), null, null);	
	}

}
