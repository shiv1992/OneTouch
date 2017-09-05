package Remote_API;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Object_Structure.NYTimes_Object;

/**
 * Created by SHIVVVV on 4/27/2017.
 */
public class NYTimes_API {

    ArrayList<NYTimes_Object> aList=null;

    public ArrayList<NYTimes_Object> searchAPI(String URL) throws JSONException, IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget= new HttpGet(URL);
        JSONObject searchJSON=null;
        JSONArray jsonArray=null;
        int flag=0;

        //Get JSON Object for URL
        try {
            HttpResponse response = httpclient.execute(httpget);

            if (response.getStatusLine().getStatusCode() == 200) {
                String server_response = EntityUtils.toString(response.getEntity());
                searchJSON=new JSONObject(server_response);
                jsonArray=searchJSON.getJSONArray("items");
                flag=1;
                Log.v("Server response", jsonArray.toString());
            } else {
                Log.v("Server response", "Failed to get server response");
            }
        }
        catch (IOException e){
            Log.v("Server response", "EE");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(flag==1) {
            //Process jsonArray into Object
            int jsonArrayLength = jsonArray.length();
            Log.v("Server PTR SSZZ: ", String.valueOf(jsonArrayLength));
            aList = new ArrayList<>();

            for (int i = 0; i < 1; i++)
            {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                //if(obj.isNull("title")) continue;
               // Log.v("LL OBJ : ",obj.get("title").toString());
                NYTimes_Object tempObj = new NYTimes_Object();

                tempObj.setTitle(obj.get("title").toString());
                tempObj.setContent(obj.get("content").toString());
                tempObj.setLink(obj.get("guid").toString());
                tempObj.setAuthor(obj.get("author").toString());

                //Image
                Object tmp = obj.get("enclosure");

                //check if Object or Array
                if (tmp instanceof JSONArray) {
                    JSONArray tImage = obj.getJSONArray("enclosure");
                    if (tImage.length() == 0) return null;//continue;
                   // Log.v("LENGTH: ", String.valueOf(tImage.length()));
                    JSONObject jObj = tImage.getJSONObject(0);
                    tempObj.setThumbnail_URL(jObj.get("link").toString().replace("moth","facebookJumbo"));
                } else if (tmp instanceof JSONObject) {
                    JSONObject tImage = obj.getJSONObject("enclosure");
                    tempObj.setThumbnail_URL(tImage.get("link").toString().replace("moth","facebookJumbo"));
                }

                java.net.URL url = new URL(tempObj.getThumbnail_URL());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                tempObj.setBitmap_Image(myBitmap);

               // Log.v("Server PTR : ",tempObj.toString());
                aList.add(tempObj); // Add to list
            }
        }else {
            aList = new ArrayList<>();
        }

        Log.v("Server PTR SSZZ: ", String.valueOf(aList.size()));
        return aList;

    }



}
