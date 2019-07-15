package com.busqandote.scraper;

import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AppointmentScraper {
    private HttpClient httpClient;

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
            if(links.size() > 1)
                return true;
        }

        return false;
    }

    public boolean send(String message, String user) {
        return false;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}