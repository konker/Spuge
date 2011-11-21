package com.morningwoodsoftware.android.spuge.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            String body = getBody(intent.getExtras());
            Log.d("onReceive", "Received SMS: " + body);

            // TODO: Show Toast Widget. Test
            Toast.makeText(context, body, Toast.LENGTH_SHORT).show();

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
