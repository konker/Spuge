package com.morningwoodsoftware.android.spuge.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.util.Log;

import com.morningwoodsoftware.android.R;
import com.morningwoodsoftware.android.spuge.dto.Venue;

/* Venue XML parser */
public class VenueParser extends DefaultHandler
{
    private static final String TAG = "SPUGE";

    static final String NAME_VENUE = "venue";
    static final String NAME_NAME = "name";
    static final String NAME_MESSAGE = "message";

    private boolean inName = false;
    private boolean inMessage = false;

    private Context context;
    private List<Venue> venues;
    private String curName = null;
    private String curMessage = null;
    private boolean inVenue;

    public VenueParser(Context context)
    {
        this.context = context;
        this.venues = new ArrayList<Venue>();
        // Log.d(TAG, "p:parsed: " + rep);
    }

    public List<Venue> readVenues() throws Exception
    {
        // read in and parse the xml
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();

        xr.setContentHandler(this);
        xr.parse(new InputSource(context.getResources().openRawResource(
                R.raw.venues)));

        return venues;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException
    {
        // Log.d(TAG, "p:startElement: " + (localName.equals(NAME_QUERY)));
        if (localName.equalsIgnoreCase(NAME_VENUE))
        {
            inVenue = true;
            inName = false;
            inMessage = false;
        }
        else if (localName.equalsIgnoreCase(NAME_NAME))
        {
            inName = true;
            inMessage = false;
        }
        else if (localName.equalsIgnoreCase(NAME_MESSAGE))
        {
            inMessage = true;
            inName = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        Log.d(TAG, "p:endElement: " + curName + "," + curMessage);
        if (localName.equalsIgnoreCase(NAME_VENUE))
        {
            venues.add(new Venue(curName, curMessage));
            curName = null;
            curMessage = null;
            inVenue = false;
            inName = false;
            inMessage = false;
        }
        else if (localName.equalsIgnoreCase(NAME_NAME))
        {
            inName = false;
        }
        else if (localName.equalsIgnoreCase(NAME_MESSAGE))
        {
            inMessage = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        // Log.d(TAG, "p:characters: " + length + ": " + curName);
        if (inName)
        {
            curName = new String(ch, start, length);
        }
        else if (inMessage)
        {
            curMessage = new String(ch, start, length);
        }
    }
}
