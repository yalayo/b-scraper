package com.busqandote.scraper;

import org.apache.http.client.HttpClient;

public class AppointmentScraper {
    private HttpClient httpClient;

    public boolean check() {
        return false;
    }

    public boolean send(String message, String user) {
        return false;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}