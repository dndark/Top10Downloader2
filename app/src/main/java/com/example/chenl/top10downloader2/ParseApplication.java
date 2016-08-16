package com.example.chenl.top10downloader2;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by chenl on 8/16/2016.
 */
public class ParseApplication {
    private String xmlData;
    private ArrayList<Application> applications;

    public ParseApplication(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<Application>();
    }

    public ArrayList<Application> getApplications() {
        Log.d("aaa",applications.toString());
        return applications;
    }

    public boolean process() {
        boolean status = true;
        Application currentRecord = null;
        boolean inEnTRY = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d("ParseApplication", "Stating tag for " + tagName);
                        if (tagName.equalsIgnoreCase("entry")) {
                            inEnTRY = true;
                            currentRecord = new Application();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d("ParseApplication", "Stating tag for " + tagName);
                        if (inEnTRY){
                            if (tagName.equalsIgnoreCase("entry")){
                                applications.add(currentRecord);
                                inEnTRY = false;
                            } else if(tagName.equalsIgnoreCase("name")) {
                                currentRecord.setName(textValue);
                            } else if(tagName.equalsIgnoreCase("artist")) {
                                currentRecord.setArtist(textValue);
                            } else if(tagName.equalsIgnoreCase("releaseDate")) {
                                currentRecord.setReleaseData(textValue);
                            }
                        }
                        break;
                    default:
                        //nothing
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return true;
    }
}
