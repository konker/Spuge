package com.morningwoodsoftware.android.spuge.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MessageReceiverActivity extends Activity
{
    private final static String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d("onCreate", "We're here");
        
        super.onCreate(savedInstanceState);
        displayAlert( this.getApplicationContext(), "Jee");
        //IntentFilter filter = new IntentFilter(ACTION);
        //this.registerReceiver(mReceivedSMSReceiver, filter);
    }

    private void displayAlert(Context context, String body)
    {
        Log.d("displayAlert", "We're here");
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(body)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        //alert.show();
    }

    private final BroadcastReceiver mReceivedSMSReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if (ACTION.equals(action))
            {
                // your SMS processing code
                displayAlert(context, getBody(intent.getExtras()));
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

    };

}
