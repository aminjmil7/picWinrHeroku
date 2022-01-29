package com.pickwinr.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
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
public class TwitterVersionTwo {

  @Value("${twitterBearerToken}")
  private String bearerToken;

    /*
     * This method calls the v2 Tweets endpoint with ids as query parameter
     */
    @GetMapping("twitter/v2/getTweets")
    private String getTweets() throws IOException, URISyntaxException {
        String tweetResponse = null;

        String ids = "1138505981460193280,1261326399320715264";
        HttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("ids", ids));
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at,author_id,entities,attachments,conversation_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

    @GetMapping("twitter/v2/getTimelineTweets/{userId}")
    private String getTimelineTweets(@PathVariable String userId) throws IOException, URISyntaxException {
        String tweetResponse = null;

        // String userId = "1374640376250765313";
        HttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(
            new BasicNameValuePair(
                "tweet.fields",
                "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld"
            )
        );
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

    @GetMapping("twitter/v2/getTweetReplies/{postId}")
    private String getTweetReplies(@PathVariable String postId) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/tweets?ids=" + postId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(
            new BasicNameValuePair(
                "tweet.fields",
                "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld"
            )
        );
        queryParameters.add(new BasicNameValuePair("expansions", "author_id,in_reply_to_user_id,referenced_tweets.id"));
        queryParameters.add(new BasicNameValuePair("user.fields", "name,username"));

        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

    @GetMapping("twitter/v2/getProfileInfo/{usernames}")
    private String getUsers(@PathVariable String usernames) throws IOException, URISyntaxException {
        String userResponse = null;

        HttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", usernames));
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }

        return userResponse;
    }

    @GetMapping("twitter/v2/accessToken/{code}")
    private String getAccessToken(@PathVariable String code) throws IOException, URISyntaxException {
        String userResponse = null;

        HttpClient httpClient = HttpClients
            .custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.instagram.com/oauth/access_token");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("client_id", "1087161935189461"));
        queryParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        queryParameters.add(new BasicNameValuePair("code", code));
        queryParameters.add(new BasicNameValuePair("client_secret", "ea04816ebaa1690167fdb3d9c39580c5"));
        queryParameters.add(new BasicNameValuePair("redirect_uri", "https://pickwinr-webapp.herokuapp.com/welcome"));

        uriBuilder.addParameters(queryParameters);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }

        return userResponse;
    }

}
