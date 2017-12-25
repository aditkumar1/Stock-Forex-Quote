package com.stockquote.adit.stockquote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class POSTHttpFetcher extends AsyncTask<Void,Void,String>
{
    Context context;
    String name;
    String id;
	public POSTHttpFetcher(Context context,String name,String id)
	{
		super();
		this.context=context;
		this.name=name;
		this.id=id;
	}
	@Override
	protected String doInBackground(Void... voids)
	{
        try{
            URL url = new URL(CommonUtils.paasEndPointURL);
            Log.e("POST called","POst  called");
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("id", id);
            postDataParams.put("name", name);
            Log.e("params",postDataParams.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams.toString());
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }
            else {
                return "{'Exception': '"+responseCode+"'}";
            }
        }
        catch(Exception e){
            return "Exception";
        }
	}
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
        if(result!=null&&result.contains("True")){
            Toast.makeText(context, "Server Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
