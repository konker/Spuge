package com.morningwoodsoftware.android.spuge.channel;

import android.content.Context;

public abstract class SendMessageListener
{
    private Context context;

    public SendMessageListener(Context context)
    {
        this.context = context;
    }

    public Context getContext()
    {
        return this.context;
    }

    /**
     * Triggered when message has successfully been sent
     */
    public abstract void onMessageSent();

    /**
     * Triggered when a sent message has been received successfully
     */
    public abstract void onSentMessageReceived();

    /**
     * Triggers when message failed
     */
    public abstract void onSendFailed();
}
