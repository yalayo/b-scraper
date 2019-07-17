package com.busqandote.scraper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentScraper {
    private HttpClient httpClient;

    public AppointmentScraper() {}

    public AppointmentScraper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean check() {
        String url = "http://visas.migracion.gob.pa/SIVA/verif_citas";

        Document document = null;
        try {
            document = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Elements images = document.select("img");
        Elements links = document.select("a");

        if(images.size() > 2) {
            return links.size() > 1;
        }

        return false;
    }

    public boolean send(String message, String user) {
        String url = "https://wlwba0hr6h.execute-api.us-east-1.amazonaws.com/Prod/message";
        HttpPost httpPost = new HttpPost(url);

        try {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("message", message));
            params.add(new BasicNameValuePair("user", user));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();

            if(statusLine.getStatusCode() != 200)
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}