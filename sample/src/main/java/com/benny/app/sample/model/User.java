package com.benny.app.sample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 9/5/15.
 */

public class User {
    public String id;
    public String name;
    public String photo;
    public String email;
    public String mobile;
    public String gender;
    public String province;
    public String city;

    @SerializedName("access_token")
    public String accessToken;
}
