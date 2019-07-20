package com.busqandote.scraper;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;

public class AppointmentScraper {
    private HttpClient httpClient;

    public AppointmentScraper() {}

    public AppointmentScraper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean check() {
        String url = System.getenv("SCRAP_URL");

        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();

            Elements images = document.select("img");
            Elements links = document.select("a");

            if(images.size() > 2) {
                return links.size() > 1;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean send(String message, String user) {
        String url = "https://wlwba0hr6h.execute-api.us-east-1.amazonaws.com/Prod/message";

        try {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameter("message", message).setParameter("user", user);

            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();

            if(statusLine.getStatusCode() != 200) {
                String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("The one: " + json);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}