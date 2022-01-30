package com.pickwinr.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Sample code to demonstrate the use of the v2 Tweets endpoint
 * */
@RestController
@RequestMapping("/api")
@PropertySource("classpath:config/application.yml")
public class InstagramSocialResource {

    @GetMapping("instagram/accessToken/{code}")
    private String getAccessToken(@PathVariable String code) throws IOException, URISyntaxException {
        String userResponse = null;

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://api.instagram.com/oauth/access_token");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("client_id", "1087161935189461"));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("client_secret", "ea04816ebaa1690167fdb3d9c39580c5"));
        params.add(new BasicNameValuePair("redirect_uri", "https://pickwinr-webapp.herokuapp.com/welcome"));

        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }
        System.out.println("_____________");
        System.out.println(userResponse);

        return userResponse;
    }

    @GetMapping("instagram/getUserMedia/{userId}/{accessToken}")
    private String getUserMedia(@PathVariable String userId, @PathVariable String accessToken) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://graph.instagram.com/v11.0/me"));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("fields", "id,username"));
        queryParameters.add(new BasicNameValuePair("access_token", accessToken));

        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

    @GetMapping("instagram/getMedia/{userId}/{accessToken}")
    private String getMedia(@PathVariable String userId, @PathVariable String accessToken) throws IOException, URISyntaxException {
        String userResponse = null;

        HttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(String.format("https://graph.instagram.com/v11.0/me/media"));

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("fields", "id,username"));
        params.add(new BasicNameValuePair("access_token", accessToken));
        uriBuilder.addParameters(params);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Content-Type", "application/json");

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }
        System.out.println("_____________");
        System.out.println(userResponse);

        return userResponse;
    }
}
