package com.pickwinr.web.rest;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Comment;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pickwinr.domain.Post}.
 */
@RestController
@RequestMapping("/api")
public class FaceboolSocialResource {

    private final Logger log = LoggerFactory.getLogger(FaceboolSocialResource.class);

    private static final String ENTITY_NAME = "cycle";

    private Facebook facebook;

    private ConnectionRepository connectionRepository;

    public FaceboolSocialResource(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    public FaceboolSocialResource() {
    }

    @GetMapping("/fb/{token}/profile")
    public ResponseEntity<User> profile(@PathVariable String token) {
        FacebookClient fbClient = new DefaultFacebookClient(token, com.restfb.Version.VERSION_9_0);
        User userProfile = fbClient.fetchObject("me", User.class,
                Parameter.with("fields", "id,name,email,gender,picture,birthday,location"));
        return ResponseUtil.wrapOrNotFound(Optional.of(userProfile));
    }

    @GetMapping("/fb/{token}/pages")
    public ResponseEntity<Connection<Page>> pages(@PathVariable String token) {
        FacebookClient fbClient = new DefaultFacebookClient(token, com.restfb.Version.VERSION_9_0);

        Connection<Page> userPages = fbClient.fetchConnection("me/accounts", Page.class,
                Parameter.with("fields", "id,name,picture,fan_count"));
        return ResponseUtil.wrapOrNotFound(Optional.of(userPages));
    }

    @GetMapping("/fb/{token}/pagePosts/{id}")
    public ResponseEntity<Connection<Post>> pagePosts(@PathVariable String token, @PathVariable String id) {
        FacebookClient fbClient = new DefaultFacebookClient(token, com.restfb.Version.VERSION_9_0);

        Connection<Post> pagePosts = fbClient.fetchConnection(
                id + "/posts",
                Post.class,
                Parameter.with(
                        "fields",
                        "created_time,attachments{description,title,type,description_tags,media},full_picture,comments{application,can_like,from,id,like_count},from,id,message,likes.summary(true)"));

        return ResponseUtil.wrapOrNotFound(Optional.of(pagePosts));
    }

    @GetMapping("/fb/{token}/getPostById/{postId}")
    public ResponseEntity<Connection<Comment>> getPostById(@PathVariable String token, @PathVariable String postId) {
        FacebookClient fbClient = new DefaultFacebookClient(token, com.restfb.Version.VERSION_9_0);

        Connection<Comment> commentConnection = fbClient.fetchConnection(postId + "/comments", Comment.class);
        return ResponseUtil.wrapOrNotFound(Optional.of(commentConnection));
    }

    @GetMapping("/fb/{token}/myPosts")
    public ResponseEntity<Connection<Post>> myPosts(@PathVariable String token) {
        FacebookClient fbClient = new DefaultFacebookClient(token, com.restfb.Version.VERSION_9_0);

        Connection<Post> userPosts = fbClient.fetchConnection(
                "me/posts",
                Post.class,
                Parameter.with(
                        "fields",
                        "name,created_time,description,full_picture,from,id,message,reactions.summary(total_count),comments.summary(total_count)"));

        return ResponseUtil.wrapOrNotFound(Optional.of(userPosts));
    }
}
