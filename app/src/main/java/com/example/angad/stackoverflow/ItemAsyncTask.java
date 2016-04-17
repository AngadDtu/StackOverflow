package com.example.angad.stackoverflow;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Angad on 17/04/16.
 */
public class ItemAsyncTask extends AsyncTask<String,Void,item[]>{
    ItemAsyncTaskInterface listener;
    @Override
    protected item[] doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream data = urlConnection.getInputStream();
            Scanner s = new Scanner(data);
            StringBuffer output = new StringBuffer();
            while (s.hasNext()) {
                output.append(s.nextLine());
            }
            Log.i("output", output.toString());
            s.close();
            urlConnection.disconnect();
            return parseJson(output.toString());
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    @Override
    protected void onPostExecute(item[] items) {
        if(listener!=null)
            listener.fetchItem(items);
    }

    private item[] parseJson(String jsonString) {
        try {
            JSONObject object=new JSONObject(jsonString);
            JSONArray itemArray=object.getJSONArray("items");
            item[] output=new item[itemArray.length()];
            String image;
            String name;
            int score;
            String link;
            String title;
            long last_date;
            for(int i=0;i<itemArray.length();i++) {
                JSONObject root = itemArray.getJSONObject(i);
                score=root.getInt("score");
                link=root.getString("link");
                title=root.getString("title");
                last_date=root.getLong("last_activity_date");
                JSONObject owner=root.getJSONObject("owner");
                image=owner.getString("profile_image");
                name=owner.getString("display_name");
                JSONArray tag=root.getJSONArray("tags");
                //String image, String name, int score, String link, String title, long time, String[] tags)
                item f = new item(image,name,score,link,title,last_date,tag);
                output[i]=f;
            }
            return output;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
