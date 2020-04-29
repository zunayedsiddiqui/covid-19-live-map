package zunayed.siddiqui.utdallas.edu.mapapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class DataFetcher extends AsyncTask<String, Void, JSONObject> {
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final String BASE_URL = "http://149.165.157.107:1971/api/data";

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject resp;
        String strUrl = BASE_URL+"?"+strings[0];

        try {
            // Create a URL object holding the url
            URL url = new URL(strUrl);

            // Create a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            // Connect ot the url
            connection.connect();

            // Check if Http request was successful
            if(connection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException("HTTP error code: " + connection.getResponseCode());
            }

            // Create a new buffered reader and String Builder
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String jsonTxt = readAll(bf);
            resp = new JSONObject(jsonTxt);

            //Close the Buffered reader and disconnect http connection
            bf.close();
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

        return resp;
    }

    private String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
