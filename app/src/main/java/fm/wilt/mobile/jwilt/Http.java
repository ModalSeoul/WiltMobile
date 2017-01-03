package fm.wilt.mobile.jwilt;

import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


public class Http {

    public JSONParser parser = new JSONParser();
    protected HttpURLConnection connection;
    protected String APIUrl;

    public Http(String APIUrl) {
        this.APIUrl = APIUrl;
    }

    /**
     * Returns JSONObject from Wilt backend
     * @param endpoint
     * @return response object from get request
     */
    public JSONObject apiGet(String endpoint) {
        try {
            String response = get(endpoint);
            JSONObject responseObj = new JSONObject();
            responseObj = (JSONObject) parser.parse(response);
            return responseObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Simple HTTP get request
     * @param endPoint
     * @param format
     * @return String containing response body
     * @throws Exception
     */
    public String get(String endpoint) throws Exception {
        URL url = new URL(APIUrl + endpoint);
        URLConnection wilt = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(wilt.getInputStream()));
        String inputLine = in.readLine();
        in.close();
        return inputLine;
    }

    /**
     * Simple HTTP post request
     * @param endpoint
     * @param payload
     * @param token
     * @return JSONObject of response body
     * @throws IOException
     */
    public JSONObject post(String endpoint, String payload, String token) throws IOException {
        String respBody;
        URLConnection wilt = getOpenedConnection(endpoint, token);
        wilt.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(wilt.getOutputStream());

        writer.write(payload);
        writer.flush();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(wilt.getInputStream()));
        respBody = in.readLine();
        in.close();

        if (respBody.length() > 0) {
            try {
                return (JSONObject) parser.parse(respBody);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private URLConnection getOpenedConnection(String endpoint, String token) {
        try {
            URL url = new URL(APIUrl + endpoint);
            URLConnection wilt = url.openConnection();

            if (token != "")
                wilt.setRequestProperty("Authorization", token);

            return wilt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}