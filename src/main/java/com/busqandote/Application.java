package com.busqandote;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.busqandote.scraper.AppointmentScraper;

import java.io.InputStream;
import java.io.OutputStream;

public class Application implements RequestStreamHandler {
    private AppointmentScraper appointmentScraper = new AppointmentScraper();

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
        context.getLogger().log("TESTING ");
    }
}