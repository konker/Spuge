package com.morningwoodsoftware.android.spuge.channel.impl.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.channel.ChannelListener;
import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;

public class ChannelSmsImpl implements Channel
{

	@Override
	public void send(Message message, Contact receiver) 
	{
		sendSms(message, receiver, null, null);
	}

	@Override
	public void send(Message message, Contact receiver, 
			final ChannelListener listener) 
	{
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        
        PendingIntent sentPI = PendingIntent.getBroadcast(listener.getContext(), 0,
        		new Intent(SENT), 0);
	 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(listener.getContext(), 0,
        		new Intent(DELIVERED), 0);
	 
	        //---when the SMS has been sent---
	        listener.getContext().registerReceiver( new BroadcastReceiver(){
	            @Override
	            public void onReceive(Context arg0, Intent arg1) {
	                switch (getResultCode())
	                {
	                    case Activity.RESULT_OK:
	                    	listener.onMessageSent();
	                        break;
	                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
	                    	listener.onMessageFailed();
	                        break;
	                    case SmsManager.RESULT_ERROR_NO_SERVICE:
	                    	listener.onMessageFailed();
	                        break;
	                    case SmsManager.RESULT_ERROR_NULL_PDU:
	                    	listener.onMessageFailed();
	                        break;
	                    case SmsManager.RESULT_ERROR_RADIO_OFF:
	                    	listener.onMessageFailed();
	                        break;
	                }
	            }
	        }, new IntentFilter(SENT) );
	 
	        //---when the SMS has been delivered---
	        listener.getContext().registerReceiver(new BroadcastReceiver(){
	            @Override
	            public void onReceive(Context arg0, Intent arg1) {
	                switch (getResultCode())
	                {
	                    case Activity.RESULT_OK:
	                    	listener.onSentMessageReceived();
	                        break;
	                    case Activity.RESULT_CANCELED:
	                    	listener.onMessageFailed();
	                        break;                        
	                }
	            }
	        }, new IntentFilter(DELIVERED) ); 

	        sendSms(message, receiver, sentPI, deliveredPI);
	}
	
	private void sendSms(Message message, Contact receiver,
			PendingIntent sentPI, PendingIntent deliveredPI)
	{
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(receiver.getMobileNr(), null, message.getBody(), sentPI, deliveredPI);	
	}

}
