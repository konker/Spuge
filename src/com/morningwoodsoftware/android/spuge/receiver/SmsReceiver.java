package com.morningwoodsoftware.android.spuge.receiver;

import com.morningwoodsoftware.android.spuge.activity.MessageReceiverActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

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
            // Toast.makeText(context, body, Toast.LENGTH_SHORT).show();
            
            Intent i = new Intent(context, MessageReceiverActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            /*
            Intent i = new Intent();
            i.setClassName("com.morningwoodsoftware.android.spuge.activity", 
                    "MessageReceiverActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            */
            context.startActivity(i);
            

        }
        catch (Exception e)
        {
            Log.e("onReceive", "Exception", e);
        }
    }

    // ---retrieve the SMS message received---
    private String getBody(Bundle bundle)
    {
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
