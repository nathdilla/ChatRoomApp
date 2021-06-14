package com.example.chatroomapp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseImage
{
    private String url;
    private boolean hasImage;

    public ParseImage(String url)
    {
        this.url = url;
        this.hasImage = hasImageLink(url);
    }

    public String getImage(String searchParameters) {
        String result = "";
        //String ua = "Mozilla/5.0";
        try {
            String googleUrl = "https://www.google.com/search?tbm=isch&q=" + searchParameters.replace(",", "");
            //Document doc1 = Jsoup.connect(googleUrl).userAgent(ua).timeout(10 * 1000).get();
            Document doc1 = Jsoup.connect(googleUrl).get();
            Element media = doc1.select("[data-src]").first();
            String sourceUrl = media.attr("abs:data-src");

            result = "http://images.google.com/search?tbm=isch&q=" + searchParameters + " image source= " + sourceUrl.replace("&quot", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static boolean hasImageLink(String url)
    {
        boolean output=false;
        Pattern p = Pattern.compile("^%https?");
        Matcher m = p.matcher(url);
        if (m.find()) {
            output= true;
        }
        else
        {
            output = false;
        }
    return output;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHasImage() {
        return hasImage;
    }
}
