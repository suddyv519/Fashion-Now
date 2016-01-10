package com.example.whhsfbla.fashionnow;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Sudharshan on 1/9/2016.
 */
@ParseClassName("Post")
public class PostInfo extends ParseObject {

    public PostInfo(){

    }

    public String getUsername() {
        return getString("user");
    }

    public String getPic() {
        return getString("picURL");
    }
}
