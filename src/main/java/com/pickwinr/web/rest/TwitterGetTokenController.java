package com.pickwinr.web.rest;

import com.pickwinr.domain.TwitterKey;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@RestController
@RequestMapping("/api")
@PropertySource("classpath:config/application.yml")
public class TwitterGetTokenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterGetTokenController.class);
    Twitter twitter;

    @Value("${twitterConsumerKey}")
    private String consumerKey;
    @Value("${twitterConsumerSecret}")
    private String consumerSecret;
    @Value("${twitterRedirectUrl}")
    private String redirectURL;

	@RequestMapping("twitter/getToken")
    public ResponseEntity<TwitterKey> getToken(HttpServletRequest request) {
        // this will be the URL that we take the user to
        String twitterUrl = "";

        try {
            // get the Twitter object
            twitter = getTwitter();

            // go get the request token from Twitter
            RequestToken requestToken = twitter.getOAuthRequestToken(redirectURL);
            // put the token in the session because we'll need it later
            request.getSession().setAttribute("requestToken", requestToken);

            // let's put Twitter in the session as well
            request.getSession().setAttribute("twitter", twitter);

            // now get the authorization URL from the token
            twitterUrl = requestToken.getAuthorizationURL();

            LOGGER.info("Authorization url is " + twitterUrl);
        } catch (Exception e) {
            LOGGER.error("Problem logging in with Twitter!", e);
        }

        TwitterKey resultKey = new TwitterKey(twitterUrl);
        return ResponseUtil.wrapOrNotFound(Optional.of(resultKey));
    }

    /*
     * Instantiates the Twitter object
     */
    public Twitter getTwitter() throws IllegalStateException, TwitterException {
        Twitter twitter = null;

		System.out.println("*********************");
		System.out.println(consumerKey);
		System.out.println(consumerSecret);
        // build the configuration
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        Configuration configuration = builder.build();

        // instantiate the Twitter object with the configuration
        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();

        return twitter;
    }

    // This is where we land when we get back from Twitter
    @RequestMapping("twitter/getUserTimeline/{oauthVerifier}")
    public ResponseEntity<ResponseList<Status>> twitterTimeline(HttpServletRequest request, @PathVariable String oauthVerifier) {
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");

        try {
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            ResponseList<Status> mytweets = twitter.getUserTimeline();

            return ResponseUtil.wrapOrNotFound(Optional.of(mytweets));
        } catch (Exception e) {
            LOGGER.error("Problem getting token!", e);
            return ResponseUtil.wrapOrNotFound(Optional.of(null));
        }
    }
}
