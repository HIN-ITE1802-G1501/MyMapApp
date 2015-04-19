// MyMapApp

package no.hin.student.mymapapp;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

class PostServlet extends AsyncTask<String, Void, String> {
    String TAG = "Project";

    @Override
    protected String doInBackground(String... params) {
        try{
            Log.d("Prosjekt", "Before writing");
            String urlParams =
                    URLEncoder.encode("device", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                    URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                    URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");

            Log.d("Prosjekt", urlParams);
            String serverURL = "http://kark.hin.no:8088/MyMapApp/Location" + "?" + urlParams;
            Log.d("Prosjekt", serverURL);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(serverURL);

            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }
            return "Error: " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase();
        }catch(Exception e){
            Log.d(TAG, "Error");
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null)
            Log.d(TAG, "Ok");
    }
}