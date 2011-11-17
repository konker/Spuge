package com.morningwoodsoftware.android.spuge.channel;

import com.morningwoodsoftware.android.spuge.dto.Message;

public abstract class ReceiveMessageListener
{
    public abstract void onReceive(Message message);
}
