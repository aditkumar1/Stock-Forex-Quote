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

public class PUTHttpFetcher extends AsyncTask<Void,Void,String>
{
	Context context;
	String id,name;
    public PUTHttpFetcher(Context context,String id, String name)
	{
        super();
	    this.context=context;
        this.id=id;
        this.name=name;
	}
	@Override
	protected String doInBackground(Void... voids)
	{
        try{
            URL url = new URL(CommonUtils.paasEndPointURL+"?id="+id+"name="+name);
            JSONObject postDataParams = new JSONObject();
            Log.e("params",postDataParams.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("PUT");
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
                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
	}
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
        if(result!=null&&result.contains("True")){
            Toast.makeText(context, "Server Updated", Toast.LENGTH_LONG).show();
        }
    }

}
