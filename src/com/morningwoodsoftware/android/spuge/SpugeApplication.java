package com.morningwoodsoftware.android.spuge;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.morningwoodsoftware.android.spuge.channel.Channel;
import com.morningwoodsoftware.android.spuge.channel.SendMessageListener;
import com.morningwoodsoftware.android.spuge.channel.impl.sms.ChannelSmsImpl;
import com.morningwoodsoftware.android.spuge.dto.Contact;
import com.morningwoodsoftware.android.spuge.dto.Message;
import com.morningwoodsoftware.android.spuge.dto.Venue;
import com.morningwoodsoftware.android.spuge.exception.ApplicationException;
import com.morningwoodsoftware.android.spuge.exception.NotReadyException;
import com.morningwoodsoftware.android.spuge.util.VenueParser;

public class SpugeApplication extends Application implements
        OnSharedPreferenceChangeListener
{
    private static final String TAG = "SPUGE";
    private static final String KEY_NUMBER = "number";

    private List<Venue> venues = new ArrayList<Venue>();
    private Channel channel;
    private boolean sending = false;
    private String number;

    @Override
    public void onCreate()
    {
        this.channel = new ChannelSmsImpl();

        // TODO: Init venues
        // TESTS
        readVenues();

        // Get number from preferences and listen for changes to this number
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        this.number = prefs.getString(KEY_NUMBER, null);

        super.onCreate();
    }

    /**
     * Returns number to send messages to
     * 
     * @return
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * Returns list of venues
     * 
     * @return
     */
    public List<Venue> getVenues()
    {
        return venues;
    }

    public Venue getVenueByIndex(int i)
    {
        return venues.get(i);
    }

    /**
     * Sends a venue using a Channel
     * 
     * @param venue
     */
    public void sendMessage(Venue venue) throws NotReadyException,
            ApplicationException
    {
        if (sending)
        {
            throw new NotReadyException("Still sending previous message!");
        }

        // TODO: Get message from venue
        Message message = getMessage(venue);
        Contact receiver = new Contact(number);

        // TODO: Get receiver

        // TODO: Remove below when fixed
        /*
         * Message message = new Message("Some message body"); Contact receiver
         * = new Contact("5556");
         */

        SpugeApplication.this.sending = true;

        // TODO: Implement
        channel.send(message, receiver,
                new SendMessageListener(this.getApplicationContext())
                {
                    @Override
                    public void onMessageSent()
                    {
                        Log.d("sendMessage()", "Message sent successfully!");
                        SpugeApplication.this.sending = false;
                        Toast.makeText(this.getContext(), "Message sent successfully!", Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onSendFailed()
                    {
                        Log.d("sendMessage()", "Message failed!");
                        SpugeApplication.this.sending = false;
                        Toast.makeText(this.getContext(), "Message sending failed!", Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onSentMessageReceived()
                    {
                        Log.d("sendMessage()", "Sent Message received!");
                        Toast.makeText(this.getContext(), "Message received successfully!", Toast.LENGTH_SHORT).show();
                        SpugeApplication.this.sending = false;
                        // TODO Auto-generated method stub
                    }
                });
    }

    /**
     * Gets a message matching passed venue
     * 
     * @param venue
     * @return
     */
    private Message getMessage(Venue venue)
    {
        // TODO: id?
        return new Message("FIXME:ID", venue.getQuestion());
    }

    /**
     * Reads and constructs a list of venues
     * 
     * @return
     */
    private void readVenues()
    {
        VenueParser parser = new VenueParser(this);
        // FIXME: use setVenues(..) ?
        // FIXME: exceptions
        try
        {
            venues = parser.readVenues();
        }
        catch (Exception ex)
        {
            // FIXME: now what?
        }
    }

    /* method required by OnSharedPreferenceChangeListener */
    @Override
    public synchronized void onSharedPreferenceChanged(SharedPreferences prefs,
            String key)
    {
        if (key.equals(KEY_NUMBER))
        {
            number = prefs.getString(KEY_NUMBER, null);
        }
        return;
    }
}
