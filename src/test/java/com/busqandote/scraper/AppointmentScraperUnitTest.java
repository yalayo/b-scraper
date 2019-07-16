package com.busqandote.scraper;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppointmentScraperUnitTest {
    private AppointmentScraper appointmentScraper = new AppointmentScraper();

    @Test
    @DisplayName("Given a specific html the scraper should return false")
    public void testSystemNotAvailable() {
        assertFalse(appointmentScraper.check());
    }

    @Test
    @DisplayName("Given a specific html the scraper should return true")
    public void testSystemAvailable() {
        //assertTrue(appointmentScraper.check());
    }

    @Test
    @DisplayName("If the message to be send is not empty then return true")
    public void testSendMessageWithTelegramApi() {
        String user = "test";
        String message = "Test message";

        HttpClient httpClient = mock(HttpClient.class);
        HttpPost httpPost = mock(HttpPost.class);
        HttpResponse httpResponse = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        try {
            when(httpClient.execute(any())).thenReturn(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);

        appointmentScraper.setHttpClient(httpClient);

        //assertTrue(appointmentScraper.send(message, user));
    }
}

