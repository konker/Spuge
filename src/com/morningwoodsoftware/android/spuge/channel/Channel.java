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
    void send(Message message, Contact receiver, SendMessageListener listener)
            throws ApplicationException;
}
