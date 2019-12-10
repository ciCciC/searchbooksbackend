package com.koray.searchbackend.server.book.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class ScraperService {

    private StringBuilder isbnQuery;
    private StringBuilder imgSize;
    private Document doc;

    public ScraperService() {
//        https://isbnsearch.org/isbn/ <- for getting isbn data
        this.isbnQuery = new StringBuilder("http://isbnsearch.org/isbn/");
        this.imgSize = new StringBuilder("&tbs=isz:m");
        try {
            enableSSLSocket();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            System.out.println("Cant enable SSLSocket");
            e.printStackTrace();
        }
    }

    public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }

    public void connect(StringBuilder isbn){
        try{
            System.out.println("Calling: " + this.isbnQuery.toString() + isbn.toString());
            doc = Jsoup.connect(this.isbnQuery.toString() + isbn.toString())
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("No connection.");
            e.printStackTrace();
        }
    }

    private Element getBody(){
        return doc.body();
    }

    public StringBuilder getImgUrl() {
        Element book = this.getBody().getElementById("book");
//        Elements image = book.getElementsByClass("image");
//        Element data = image.select("img").first();
        return new StringBuilder(book.html());
    }
}
