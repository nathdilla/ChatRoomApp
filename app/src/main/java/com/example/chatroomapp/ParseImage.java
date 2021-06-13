package com.example.chatroomapp;

import android.util.Log;

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

    private boolean hasImageLink(String url)
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
