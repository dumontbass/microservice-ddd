package br.org.base.log;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: guilherme
 * Date: 11/7/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class SFLogger{


    public static void log(String m, Class clazz){

        final  Logger logger = LoggerFactory.getLogger(clazz);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);


        HttpClient client = new DefaultHttpClient();
        HttpPost post = null;
        try {
            try {
                post = new HttpPost("http://api.hipchat.com/v1/rooms/message?" +
                        "format=json&" +
                        "auth_token=2c08c31fce55dd3cbbe14755273dd1&" +
                        "room_id=293398&from=suporte&" +
                        "message="+ URLEncoder.encode(m+"\n"+"[T]"+reportDate+"\n[C]"+clazz.getName()+"\n-----","UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (HttpException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }





}
