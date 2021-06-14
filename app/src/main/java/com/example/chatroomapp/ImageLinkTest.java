package com.example.chatroomapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageLinkTest {
    @Test
    public void correctURLString()
    {
        ParseImage parse = new ParseImage("%https://www.noracooks.com/wp-content/uploads/2020/05/square.jpg");
        boolean expected = true;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void incorrectURLStringStart()
    {
        ParseImage parse = new ParseImage("97https://www.noracooks.com/wp-content/uploads/2020/05/square.jpg");
        boolean expected = false;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void nopercentURLStringStart()
    {
        ParseImage parse = new ParseImage("https://www.noracooks.com/wp-content/uploads/2020/05/square.jpg");
        boolean expected = false;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void noProtocolURLStringStart()
    {
        ParseImage parse = new ParseImage("www.noracooks.com/wp-content/uploads/2020/05/square.jpg");
        boolean expected = false;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void notEvenAURL()
    {
        ParseImage parse = new ParseImage("cheese");
        boolean expected = false;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void differentURLCorrect()
    {
        ParseImage parse = new ParseImage("%https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FFire&psig=AOvVaw1dqDwZB-sDZrbOTdlTB85l&ust=1623710957110000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjC8ejYlfECFQAAAAAdAAAAABAD");
        boolean expected = true;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void differentURLCorrect2()
    {
        ParseImage parse = new ParseImage("%https://static-assets-prod.epicgames.com/fortnite/static/webpack/8f9484f10eb14f85a189fb6117a57026.jpg");
        boolean expected = true;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void differentURLCorrect3()
    {
        ParseImage parse = new ParseImage("%https://www.cnet.com/a/img/b2KipUnQbxLgGby3LeK6xK2GXwk=/940x0/2021/03/15/a2c34474-31ae-48d9-adda-1a1531e23c19/fortnite.jpg");
        boolean expected = true;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void differentURLCorrect4()
    {
        ParseImage parse = new ParseImage("%https://cdn2.unrealengine.com/14br-consoles-1920x1080-wlogo-1920x1080-432974386.jpg");
        boolean expected = true;
        assertEquals(expected,parse.isHasImage());
    }
    @Test
    public void differentURLCorrect5()
    {
        ParseImage parse = new ParseImage("%https://www.google./st=1623710957110000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjC8ejYlfECFQAAAAAdAAAAABAD");
        boolean expected = true;
        assertEquals(expected,parse.isHasImage());
    }
}
