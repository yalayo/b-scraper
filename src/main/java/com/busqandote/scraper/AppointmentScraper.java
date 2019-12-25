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

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class AppointmentScraper {
    private HttpClient httpClient;

    public AppointmentScraper() {}

    public AppointmentScraper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public boolean check() {
        String url = System.getenv("SCRAP_URL");

        try {
            Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).sslSocketFactory(socketFactory()).get();

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
        }

        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }
}