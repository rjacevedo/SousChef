package com.anra.souschef;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class APICall extends AsyncTask<String, String, String> {

	private final static String yummilyInfo = "_app_id=c91497fc&_app_key=27909000a25956510ee72becfb8cbc21";
	@Override
	protected String doInBackground(String... uri) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        DisplayResults.instance.displayResults(result);
        //Do anything with response..
    }
    
    public static void callAPI(List<String> ingredients){
    	StringBuilder sb = new StringBuilder();
    	sb.append("http://api.yummly.com/v1/api/recipes?");
    	sb.append(yummilyInfo);
    	for(String s : ingredients){
    		sb.append("&allowedIngredient[]=").append(s.toLowerCase());
    	}
    	String s = sb.toString().replace(" ", "+");
    	new APICall().execute(s);
    }

}
