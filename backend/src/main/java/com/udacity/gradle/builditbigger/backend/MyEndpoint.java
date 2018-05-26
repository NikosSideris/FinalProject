package com.udacity.gradle.builditbigger.backend;

import com.example.android.javajokeproviderlib.Joke;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.builditbigger.gradle.udacity.com", ownerName = "backend.builditbigger.gradle.udacity.com", packagePath = ""))
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        Joke joke = new Joke();
        String message = joke.getJoke() + " pulled by backend";
        MyBean response = new MyBean();
//        response.setData("Hi, " + name);
        response.setData(message);

        return response;
    }

}
