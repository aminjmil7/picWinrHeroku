package com.pickwinr.web.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/*
 * Sample code to demonstrate the use of the v2 Tweets endpoint
 * */
@RestController
@RequestMapping("/api")
public class TwitterVersionTwo {

  /*
   * This method calls the v2 Tweets endpoint with ids as query parameter
   */
  @GetMapping("twitter/v2/getTweets")
  private String getTweets() throws IOException, URISyntaxException {
    String tweetResponse = null;
    String bearerToken = "AAAAAAAAAAAAAAAAAAAAAN%2FoVwEAAAAAsiF8lFdSyBWNW%2B2cPH%2F3j%2BWfjbo%3DsDt8TKCUa623RSf02wIBMEaSgN2TtwGz5FRvsDpkW3KWf3abZB";

    String ids = "1138505981460193280,1261326399320715264";
    HttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD).build())
        .build();

    URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
    ArrayList<NameValuePair> queryParameters;
    queryParameters = new ArrayList<>();
    queryParameters.add(new BasicNameValuePair("ids", ids));
    queryParameters
        .add(new BasicNameValuePair("tweet.fields", "created_at,author_id,entities,attachments,conversation_id"));
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
    String bearerToken = "AAAAAAAAAAAAAAAAAAAAAN%2FoVwEAAAAAsiF8lFdSyBWNW%2B2cPH%2F3j%2BWfjbo%3DsDt8TKCUa623RSf02wIBMEaSgN2TtwGz5FRvsDpkW3KWf3abZB";

    // String userId = "1374640376250765313";
    HttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD).build())
        .build();

    URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
    ArrayList<NameValuePair> queryParameters;
    queryParameters = new ArrayList<>();
    queryParameters.add(new BasicNameValuePair("tweet.fields",
        "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld"));
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
    String bearerToken = "AAAAAAAAAAAAAAAAAAAAAN%2FoVwEAAAAAsiF8lFdSyBWNW%2B2cPH%2F3j%2BWfjbo%3DsDt8TKCUa623RSf02wIBMEaSgN2TtwGz5FRvsDpkW3KWf3abZB";

    HttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD).build())
        .build();

    URIBuilder uriBuilder = new URIBuilder(
        String.format(
            "https://api.twitter.com/2/tweets?ids=" + postId));
    ArrayList<NameValuePair> queryParameters;
    queryParameters = new ArrayList<>();
    queryParameters.add(new BasicNameValuePair("tweet.fields",
        "attachments,author_id,context_annotations,conversation_id,created_at,entities,geo,id,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,reply_settings,source,text,withheld"));
    queryParameters.add(new BasicNameValuePair("expansions",
        "author_id,in_reply_to_user_id,referenced_tweets.id"));
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
    String bearerToken = "AAAAAAAAAAAAAAAAAAAAAN%2FoVwEAAAAAsiF8lFdSyBWNW%2B2cPH%2F3j%2BWfjbo%3DsDt8TKCUa623RSf02wIBMEaSgN2TtwGz5FRvsDpkW3KWf3abZB";

    HttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD).build())
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
    System.out.println(userResponse);
    return userResponse;
  }

}