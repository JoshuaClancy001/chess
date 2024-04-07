package ui;

import com.google.gson.Gson;
import ui.Exception.ResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class ClientCommunicator {

    public  <T> T doPost(String serverUrl,String path, Object request, Class<T> response, String[] auth) throws ResponseException {
        T deserializedResponse = null;
        try{
            URI uri = new URI(serverUrl + path);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setReadTimeout(5000);
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("authorization",auth[0]);

            http.connect();

            try(OutputStream requestBody = http.getOutputStream();){
                String reqData = new Gson().toJson(request);
                requestBody.write(reqData.getBytes());
            }

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream responseBody = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(responseBody);
                deserializedResponse = new Gson().fromJson(reader, response);

            }
            else if (http.getResponseCode() == 403){
                throw new ResponseException(403,"User already exists");
            }
            else if (http.getResponseCode() == 400){
                throw new ResponseException(400, "bad request");
            }
            else {
                System.out.println(http.getResponseCode());
                System.out.println(http.getResponseMessage());
                throw new ResponseException(500,"Description Error");
            }
        }
        catch(Exception ex){
            throw new ResponseException(500, ex.getMessage());
        }
        return deserializedResponse;
    }
    public  <T> T doPut(String serverUrl,String path, Object request, Class<T> response, String[] auth) throws ResponseException {
        T deserializedResponse = null;
        try{
            URI uri = new URI(serverUrl + path);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setReadTimeout(5000);
            http.setRequestMethod("PUT");
            http.setDoOutput(true);

            http.addRequestProperty("authorization",auth[0]);

            http.connect();

            try(OutputStream requestBody = http.getOutputStream();){
                String reqData = new Gson().toJson(request);
                requestBody.write(reqData.getBytes());
            }

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream responseBody = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(responseBody);
                deserializedResponse = new Gson().fromJson(reader, response);

            }
            else if (http.getResponseCode() == 403){
                throw new ResponseException(403,"User already exists");
            }
            else if (http.getResponseCode() == 400){
                throw new ResponseException(400, "bad request");
            }
            else {
                System.out.println(http.getResponseCode());
                System.out.println(http.getResponseMessage());
                throw new ResponseException(500,"Description Error");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new ResponseException(500, ex.getMessage());
        }
        return deserializedResponse;
    }

    public static <T> T doGet(String serverUrl, String path, Object request, Class<T> response, String[] auth) throws ResponseException {
        T deserializedResponse = null;
        try{
            URI uri = new URI(serverUrl + path);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setReadTimeout(5000);
            http.setRequestMethod("GET");
            http.addRequestProperty("authorization",auth[0]);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream responseBody = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(responseBody);
                deserializedResponse = new Gson().fromJson(reader, response);

            }
            else if (http.getResponseCode() == 401){
                throw new ResponseException(401,"Unauthorized");
            }
            else if (http.getResponseCode() == 500){
                throw new ResponseException(500, "Description Error");
            }
            else {
                System.out.println(http.getResponseCode());
                System.out.println(http.getResponseMessage());
                throw new ResponseException(600,"Description Error");
            }
        }
        catch(Exception ex){
            throw new ResponseException(500, ex.getMessage());
        }
        return deserializedResponse;

    }
    void doDelete(String serverUrl,String path,String[] auth) throws ResponseException {
        try{
            URI uri = new URI(serverUrl + path);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setReadTimeout(5000);
            http.setRequestMethod("DELETE");
            if (auth[0].equals("") & path.equals("/session")){
                throw new ResponseException(401, "Unauthorized");
                }
            http.addRequestProperty("authorization",auth[0]);

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                auth[0] = "";
            }
            else if (http.getResponseCode() == 500){
                throw new ResponseException(500, "Description Error");
            }
        }
        catch(Exception ex){
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private  void writeBody(Object request, HttpURLConnection http) throws  IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }
    private  <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
