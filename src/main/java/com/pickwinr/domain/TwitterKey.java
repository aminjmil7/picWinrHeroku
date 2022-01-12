package com.pickwinr.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TwitterKey {

    @Id
    private String twitterAuthKey;

    public String getTwitterAuthKey() {
        return twitterAuthKey;
    }

    public void setTwitterAuthKey(String twitterAuthKey) {
        this.twitterAuthKey = twitterAuthKey;
    }

    public TwitterKey(String twitterAuthKey) {
        this.twitterAuthKey = twitterAuthKey;
    }

    @Override
    public String toString() {
        return "TwitterKey [twitterAuthKey=" + twitterAuthKey + "]";
    }
}
