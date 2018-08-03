package com.odysseus.fibapp.Constants;

import java.util.Random;

public class OAuthParams {

    public static final String clientID = "p9YiuO1yk4suxz9t1ZUUNHXLlBhJiffIBmd49Orc";
    public static final String clientSecret = "09rrfKdHsBBjJzGA0ljwCIsHPwStdVfVBKPMrot1EH5TvPGrZNdNqxRf2bSlhpyHVEpbIgruTPbnzbMxSBV2fY7D21JytpccVxi8yqT14xoiUg6nssDhsVNJYWVnhnrU";
    public static final String redirectUri = "apifib://raco";
    public static String responseType = "code";
    public static String state;

    public static String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        state = saltStr;
        return saltStr;
    }
}
