package com.example.drss.asynctask;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MyAsyncTask extends AsyncTask<Void,Void,String> {
    String url;
    List<NameValuePair> params;
    AsyncInterface activityObj;
    public MyAsyncTask(AsyncInterface activityObj,String url,List<NameValuePair> params){
        this.activityObj=activityObj;
        this.url=url;
        this.params=params;
    }
    @Override
    protected String doInBackground(Void... params) {
        String result=null;
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(this.params, HTTP.UTF_8));
            HttpResponse response=httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode()==200){
                HttpEntity entity=response.getEntity();
                result= EntityUtils.toString(entity);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        parseResult(result);
        if (activityObj instanceof AsyncInterface){
            activityObj.parseResult(result);
        }else {
            System.out.print("===>>"+"activity尚未实现接口，请检查程序");
        }
    }
}