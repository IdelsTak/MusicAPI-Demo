package com.github.idelstak.apis;

import okhttp3.*;
import org.json.*;

import java.io.*;

public class Examples {

    private static final String LASTFM_API_URL = "http://ws.audioscrobbler.com/2.0/";
    private static final String LASTFM_API_KEY = "keyekeykeyeyey";
    private static final String GENIUS_API_URL = "https://api.genius.com/search";
    private static final String GENIUS_ACCESS_TOKEN = "tokentokentokenetoken";
    private static final String DEEZER_API_URL = "https://api.deezer.com";

    public static void main(String[] args) throws IOException {
        String artist = "";
        String track = "this is the world";

//        searchLastFm(artist, track);
//        searchGeniusLyrics(artist, track);
        searchDeezer(artist, track);
    }

    private static void searchDeezer(String artist, String track) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(DEEZER_API_URL).newBuilder();
        builder.addPathSegment("search");
        builder.addQueryParameter("q", artist + " " + track);
//        builder.addPathSegment("track");
//        builder.addPathSegment("2768259001");
        builder.addQueryParameter("limit", "10");

        Request request = new Request.Builder().url(builder.build().toString()).build();
        System.out.println("request.toString() = " + request);

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("response = " + response);
        }
    }

    private static void searchGeniusLyrics(String artist, String track) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(GENIUS_API_URL).newBuilder();
        builder.addQueryParameter("q", artist + " " + track);
        builder.addQueryParameter("access_token", GENIUS_ACCESS_TOKEN);

        Request request = new Request.Builder().url(builder.build().toString()).build();
        System.out.println("request.toString() = " + request);

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("response = " + response);
        }
    }

    private static void searchLastFm(String artist, String track) throws IOException {

        HttpUrl.Builder builder = HttpUrl.parse(LASTFM_API_URL).newBuilder();
        builder.addQueryParameter("method", "track.search");
        builder.addQueryParameter("artist", artist);
        builder.addQueryParameter("track", track);
        builder.addQueryParameter("api_key", LASTFM_API_KEY);
        builder.addQueryParameter("limit", "20");
        builder.addQueryParameter("format", "json");

        Request request = new Request.Builder().url(builder.build().toString()).build();
        System.out.println("request.toString() = " + request);

        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }

            String jsonBody = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonBody);
            System.out.println("jsonObject = " + jsonObject);
        }
    }
}