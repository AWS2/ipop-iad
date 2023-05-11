package com.mygdx.ipop_game.utils;

import static jdk.internal.net.http.HttpRequestImpl.USER_AGENT;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;




        import java.io.BufferedReader;
        import java.io.DataOutputStream;
import java.io.InputStreamReader;
        import java.net.HttpURLConnection;

interface WebServicesInt {
    StringBuffer sendPost(String url, JSONObject json) throws IOException;
}

public class WebServices implements WebServicesInt{
    public static String ADDRESS = "localhost";
    public static final int PORT = 8888;

    @Override
    public StringBuffer sendPost(String url, JSONObject json) throws IOException {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(json.toString());
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

}
