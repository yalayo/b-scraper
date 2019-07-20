package com.busqandote;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.busqandote.scraper.AppointmentScraper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.io.OutputStream;

public class Application implements RequestStreamHandler {
    private HttpClient httpClient;
    private AppointmentScraper appointmentScraper = new AppointmentScraper();

    public Application() {
        init();
    }

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
        boolean available = appointmentScraper.check();
        context.getLogger().log(available ? "Website AVAILABLE" : "Website NOT-AVAILABLE");
        context.getLogger().log("USING SCRAP_URL: " + System.getenv("SCRAP_URL"));
        context.getLogger().log("USING MESSAGE: " + System.getenv("MESSAGE"));

        if(available) {
            String user = "-1001463900608";
            String message = System.getenv("MESSAGE");

            appointmentScraper.send(message, user);
        }
    }

    private void init() {
        int timeout = 5;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        appointmentScraper.setHttpClient(httpClient);

        System.out.println("APP INITIALIZED");
    }
}