package com.morningwoodsoftware.android.spuge.channel.impl.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.channel.SendMessageListener;
import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;
import com.morningwoodsoftware.android.spuge.exception.ApplicationException;

public class ChannelSmsImpl extends BroadcastReceiver implements Channel
{
    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";

    public ChannelSmsImpl()
    {

    }

    @Override
    public void send(Message message, Contact receiver,
            final SendMessageListener listener) throws ApplicationException
    {
        try
        {
            PendingIntent sentPI = PendingIntent.getBroadcast(
                    listener.getContext(), 0, new Intent(SENT), 0);

            PendingIntent deliveredPI = PendingIntent.getBroadcast(
                    listener.getContext(), 0, new Intent(DELIVERED), 0);

            // ---when the SMS has been sent---
            listener.getContext().registerReceiver(new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context arg0, Intent arg1)
                {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Log.d("send", "RESULT_OK");
                            listener.onMessageSent();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Log.d("send", "RESULT_ERROR_GENERIC_FAILURE");
                            listener.onSendFailed();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Log.d("send", "RESULT_ERROR_NO_SERVICE");
                            listener.onSendFailed();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Log.d("send", "RESULT_ERROR_NULL_PDU");
                            listener.onSendFailed();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Log.d("send", "RESULT_ERROR_RADIO_OFF");
                            listener.onSendFailed();
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            // ---when the SMS has been delivered---
            listener.getContext().registerReceiver(new BroadcastReceiver()
            {
                @Override
                public void onReceive(Context arg0, Intent arg1)
                {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Log.d("send", "RESULT_OK");
                            listener.onSentMessageReceived();
                            break;
                        case Activity.RESULT_CANCELED:
                            Log.d("send", "RESULT_CANCELED");
                            listener.onSendFailed();
                            break;
                    }
                }
            }, new IntentFilter(DELIVERED));

            sendSms(message, receiver, sentPI, deliveredPI);
        }
        catch (Exception e)
        {
            throw new ApplicationException("Sending message failed!", e);
        }
    }

    private void sendSms(Message message, Contact receiver,
            PendingIntent sentPI, PendingIntent deliveredPI)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(receiver.getMobileNr(), null, message.getBody(),
                sentPI, deliveredPI);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            String body = getBody(intent.getExtras());
            Log.d("onReceive", "Received SMS: " + body);

            // TODO: Show Toast Widget

        }
        catch (Exception e)
        {
            Log.e("onReceive", "Exception", e);
        }
    }

    private String getBody(Bundle bundle)
    {
        // ---retrieve the SMS message received---
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] msgs = new SmsMessage[pdus.length];
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < msgs.length; i++)
        {
            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            // sb.append( msgs[i].getOriginatingAddress() ).append(": ");
            sb.append(msgs[i].getMessageBody().toString());
        }
        return sb.toString();
    }
}
