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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
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
        HttpPost post = new HttpPost("https://api.instagram.com/oauth/access_token");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        // urlParameters.add(new BasicNameValuePair("username", "abc"));
        // urlParameters.add(new BasicNameValuePair("password", "123"));
        // urlParameters.add(new BasicNameValuePair("custom", "secret"));
        urlParameters.add(new BasicNameValuePair("client_id", "1087161935189461"));
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("client_secret", "ea04816ebaa1690167fdb3d9c39580c5"));
        urlParameters.add(new BasicNameValuePair("redirect_uri", "https://pickwinr-webapp.herokuapp.com/welcome"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        // HttpPost httpPost = new HttpPost(uriBuilder.build());
        // httpPost.setHeader("Content-Type", "application/json");

        // HttpResponse response = httpClient.execute(httpPost);
        // HttpEntity entity = response.getEntity();
        // if (null != entity) {
        //     userResponse = EntityUtils.toString(entity, "UTF-8");
        // }
        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        }
    }
}
