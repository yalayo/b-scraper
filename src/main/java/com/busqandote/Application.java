package com.busqandote;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.busqandote.scraper.AppointmentScraper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.io.OutputStream;

public class Application implements RequestStreamHandler {
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private AppointmentScraper appointmentScraper = new AppointmentScraper(httpClient);

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
        boolean available = appointmentScraper.check();
        context.getLogger().log(available ? "Website AVAILABLE" : "Website NOT-AVAILABLE");

        if(available) {
            String user = "-1001463900608";
            String message = "ATENCION -> Sitio disponible para solicitar citas.";

            appointmentScraper.send(message, user);
        }
    }
}