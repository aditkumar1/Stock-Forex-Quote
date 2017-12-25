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

import javax.net.ssl.HttpsURLConnection;

public class GETHttpFetcher extends AsyncTask<Void,Void,String>
{
	String id="";
	Context context;
    public GETHttpFetcher(Context context,String id)
	{
		super();
		this.context=context;
		this.id=id;
	}
	@Override
	protected String doInBackground(Void... voids)
	{
        try{
            URL url = new URL(CommonUtils.paasEndPointURL+"?id="+id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
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
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

}
